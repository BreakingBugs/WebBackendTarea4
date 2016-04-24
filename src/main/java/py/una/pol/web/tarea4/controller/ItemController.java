package py.una.pol.web.tarea4.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.una.pol.web.tarea4.exceptions.DuplicateException;
import py.una.pol.web.tarea4.initialization.MyBatisSingleton;
import py.una.pol.web.tarea4.mapper.ItemMapper;
import py.una.pol.web.tarea4.model.Item;
import py.una.pol.web.tarea4.model.Provider;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.io.OutputStream;
import java.util.List;

@Stateless
public class ItemController {
    private static final int ITEMS_MAX = 100;
    private static final int BATCH_SIZE = 20;

    @PersistenceContext(name = "Tarea3DS")
    private EntityManager em;

    @PersistenceUnit(unitName = "Tarea3DS")
    private SessionFactory sessionFactory;

    @Inject
    ProviderController providerController;

    @Inject
    DuplicateItemController duplicateItemController;

    @Inject
    private ItemController self;

    @EJB
    private MyBatisSingleton myBatis;

    //TODO: no traer todo de una
    public List<Item> getItems() {
        List<Item> items;
        SqlSession session = myBatis.getFactory().openSession();
        try {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            items = mapper.getItems();
        } finally {
            session.close();
        }
        return items;
    }

    private void fetchItemsAndStream(OutputStream outputStream) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonGenerator jg = objectMapper.getFactory().createGenerator(outputStream);
        jg.writeStartArray();

        StatelessSession session = sessionFactory.openStatelessSession();
        try {
            ScrollableResults scrollableResults = session.createQuery("from Item order by id")
                    .setReadOnly(true).setCacheable(false).setFetchSize(ITEMS_MAX).scroll(ScrollMode.FORWARD_ONLY);
            while (scrollableResults.next()) {
                Item i = (Item) scrollableResults.get()[0];
                jg.writeObject(i);
            }
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(ItemController.class);
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        jg.writeEndArray();
        jg.close();
    }

    public Item getItemByName(String name) {
        Item item;
        SqlSession session = myBatis.getFactory().openSession();
        try {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            item = mapper.getItemByName(name);
        } finally {
            session.close();
        }
        return item;
    }

    public void addItem(Item p) {
        if (p.getProvider() != null) {
            Provider provider = providerController.getProvider(p.getProvider().getId());
            p.setProvider(provider);
        }
        try {
            self.tryAddItem(p);
        } catch (DuplicateException e) {
            duplicateItemController.addDuplicate(p);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void tryAddItem(Item p) throws DuplicateException {
        SqlSession session = myBatis.getFactory().openSession();
        try {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            mapper.insertItem(p);
        } catch (PersistenceException e) {
            throw new DuplicateException();
        } finally {
            session.close();
        }
    }

    public int batchAddItem(List<Item> items) {
        int i = 0;
        int duplicates = 0;
        for (Item item : items) {
            i++;
            addItem(item);
            if (i % BATCH_SIZE == 0) {
                try {
                    em.flush();
                } catch (ConstraintViolationException e) {
                    Logger logger = LoggerFactory.getLogger(ItemController.class);
                    logger.error(e.getMessage());
                    e.printStackTrace();
                } finally {
                    em.clear();
                }
            }
        }
        return duplicates;
    }

    public Item getItem(Integer id) {
        Item item;
        SqlSession session = myBatis.getFactory().openSession();
        try {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            item = mapper.getItem(id);
        } finally {
            session.close();
        }
        return item;
    }

    public Item updateItem(Integer id, Item itemWithChanges) {
        SqlSession session = myBatis.getFactory().openSession();
        Item item = getItem(id);
        if (item != null) {
            if (itemWithChanges.getName() != null && itemWithChanges.getName().compareTo(item.getName()) != 0) {
                item.setName(itemWithChanges.getName());
            }
            if (itemWithChanges.getPrice() != null && itemWithChanges.getPrice().compareTo(item.getPrice()) != 0) {
                item.setPrice(itemWithChanges.getPrice());
            }
            if (itemWithChanges.getStock() != null && itemWithChanges.getStock().compareTo(item.getStock()) != 0) {
                item.setStock(itemWithChanges.getStock());
            }
            if (item.getProvider() == null ||
                    (itemWithChanges.getProvider() != null &&
                            itemWithChanges.getProvider().getId().compareTo(item.getProvider().getId()) != 0)) {
                item.setProvider(providerController.getProvider(itemWithChanges.getProvider().getId()));
            }
            try {
                ItemMapper mapper = session.getMapper(ItemMapper.class);
                mapper.updateItem(item);
            } finally {
                session.close();
            }
        }
        return item;
    }

    public void removeItem(final Integer id) {
        SqlSession session = myBatis.getFactory().openSession();
        try {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            mapper.deleteItem(id);
        } finally {
            session.close();
        }
    }
}
