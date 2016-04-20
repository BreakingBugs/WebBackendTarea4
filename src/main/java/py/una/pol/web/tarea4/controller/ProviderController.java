package py.una.pol.web.tarea4.controller;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import py.una.pol.web.tarea4.mapper.ItemMapper;
import py.una.pol.web.tarea4.mapper.ProviderMapper;
import py.una.pol.web.tarea4.model.Item;
import py.una.pol.web.tarea4.model.Order;
import py.una.pol.web.tarea4.model.Provider;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Stateless
public class ProviderController {
    @PersistenceContext(name = "Tarea3DS")
    EntityManager em;

    @Inject
    ItemController itemController;

    public List<Provider> getProviders() {
        String resource = "mybatis/config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            try {
                ProviderMapper mapper = session.getMapper(ProviderMapper.class);
                return mapper.getProviders();
            } finally{
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Provider> cq = cb.createQuery(Provider.class);
//        Root<Provider> root = cq.from(Provider.class);
//        cq.select(root);
//        TypedQuery<Provider> query = em.createQuery(cq);
//
//        return query.getResultList();
    }

    public void addProvider(Provider p) {
        em.persist(p);
    }

    public boolean buyFromProvider(Integer providerId, List<Order> orders) {
        Provider p = this.getProvider(providerId);
        if (p == null) {
            return false;
        }

        for (Order o : orders) {
            Item i = itemController.getItem(o.getItem());
            if (i == null) {
                continue;
            }

            for (Item item : p.getItems()) {
                if (i.getId().equals(item.getId())) {
                    i.setStock(i.getStock() + o.getAmount());
                }
            }

        }

        return true;
    }

    public Provider getProvider(Integer id) {
        return em.find(Provider.class, id);
    }

    public Provider updateProvider(Integer id, Provider providerWithChanges) {
        Provider p = getProvider(id);
        if (p != null) {
            if (providerWithChanges.getName() != null && providerWithChanges.getName().compareTo(p.getName()) != 0) {
                p.setName(providerWithChanges.getName());
            }
        }
        return p;
    }

    public void removeProvider(final Integer id) {
        Provider p = getProvider(id);
        if (p != null) {
            em.remove(p);
        }
    }

}
