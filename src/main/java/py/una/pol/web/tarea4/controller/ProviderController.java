package py.una.pol.web.tarea4.controller;

import org.apache.ibatis.session.SqlSession;
import py.una.pol.web.tarea4.initialization.MyBatisSingleton;
import py.una.pol.web.tarea4.mapper.ProviderMapper;
import py.una.pol.web.tarea4.model.Item;
import py.una.pol.web.tarea4.model.Order;
import py.una.pol.web.tarea4.model.Provider;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProviderController {
    @PersistenceContext(name = "Tarea3DS")
    EntityManager em;

    @Inject
    ItemController itemController;

    @EJB
    MyBatisSingleton myBatis;

    public List<Provider> getProviders() {
        SqlSession session = myBatis.getFactory().openSession();
        List<Provider> providers;
        try {
            ProviderMapper mapper = session.getMapper(ProviderMapper.class);
            providers = mapper.getProviders();
        } finally {
            session.close();
        }
        return providers;
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
