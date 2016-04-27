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
import java.util.List;

@Stateless
public class ProviderController {
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
    }

    public void addProvider(Provider p) {
        SqlSession session = myBatis.getFactory().openSession();
        try {
            ProviderMapper mapper = session.getMapper(ProviderMapper.class);
            mapper.insertProvider(p);
        } finally {
            session.close();
        }
    }

    //TODO: test this
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
            itemController.updateItem(i.getId(), i);
        }

        return true;
    }

    public Provider getProvider(Integer id) {
        Provider provider;
        SqlSession session = myBatis.getFactory().openSession();
        try {
            ProviderMapper mapper = session.getMapper(ProviderMapper.class);
            provider = mapper.getProvider(id);
        } finally {
            session.close();
        }
        return provider;
    }

    public Provider updateProvider(Integer id, Provider providerWithChanges) {
        Provider p = getProvider(id);
        if (p != null) {
            SqlSession session = myBatis.getFactory().openSession();
            if (providerWithChanges.getName() != null && providerWithChanges.getName().compareTo(p.getName()) != 0) {
                p.setName(providerWithChanges.getName());
            }
            try {
                ProviderMapper mapper = session.getMapper(ProviderMapper.class);
                mapper.updateProvider(p);
            } finally {
                session.close();
            }
        }
        return p;
    }

    public void removeProvider(final Integer id) {
        Provider p = getProvider(id);
        if (p != null) {
            SqlSession session = myBatis.getFactory().openSession();
            try {
                ProviderMapper mapper = session.getMapper(ProviderMapper.class);
                mapper.deleteProvider(id);
            } finally {
                session.close();
            }
        }
    }

}
