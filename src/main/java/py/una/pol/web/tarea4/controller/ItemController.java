package py.una.pol.web.tarea4.controller;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
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
import java.util.List;

@Stateless
public class ItemController {
    @Inject
    ProviderController providerController;

    @Inject
    DuplicateItemController duplicateItemController;

    @Inject
    private ItemController self;

    @EJB
    private MyBatisSingleton myBatis;

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
        int duplicates = 0;
        for (Item item : items) {
            addItem(item);
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
