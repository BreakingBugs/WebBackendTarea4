package py.una.pol.web.tarea4.controller;

import org.apache.ibatis.session.SqlSession;
import py.una.pol.web.tarea4.initialization.MyBatisSingleton;
import py.una.pol.web.tarea4.mapper.DuplicateItemMapper;
import py.una.pol.web.tarea4.mapper.ItemMapper;
import py.una.pol.web.tarea4.model.DuplicateItem;
import py.una.pol.web.tarea4.model.Item;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class DuplicateItemController {
    @PersistenceContext(name = "Tarea3DS")
    private EntityManager em;

    @EJB
    private ItemController itemController;

    @EJB
    private MyBatisSingleton myBatis;

    public List<DuplicateItem> getDuplicates() {
        List<DuplicateItem> duplicates;
        SqlSession session = myBatis.getFactory().openSession();
        try {
            DuplicateItemMapper mapper = session.getMapper(DuplicateItemMapper.class);
            duplicates = mapper.getDuplicateItems();
        } finally {
            session.close();
        }
        return duplicates;
    }

    public void addDuplicate(Item p) {
        Item existing = itemController.getItemByName(p.getName());
        DuplicateItem dup = existing.getDuplicate();
        SqlSession session = myBatis.getFactory().openSession();
        try {
            DuplicateItemMapper mapper = session.getMapper(DuplicateItemMapper.class);
            if (dup != null) {
                dup.setCantidad(dup.getCantidad() + 1);
                mapper.updateDuplicateItem(dup);
            } else {
                dup = new DuplicateItem();
                dup.setCantidad(1);
                dup.setItem(existing);
                existing.setDuplicate(dup);
                mapper.insertDuplicateItem(dup);
            }
        } finally {
            session.close();
        }
        p.setId(existing.getId());
        p.setName(existing.getName());
        p.setPrice(existing.getPrice());
        p.setStock(existing.getStock());
        p.setDuplicate(existing.getDuplicate());
        p.setProvider(existing.getProvider());
    }

    public DuplicateItem getDuplicate(Integer id) {
        DuplicateItem duplicate;
        SqlSession session = myBatis.getFactory().openSession();
        try {
            DuplicateItemMapper mapper = session.getMapper(DuplicateItemMapper.class);
            duplicate = mapper.getDuplicateItem(id);
        } finally {
            session.close();
        }
        return duplicate;
    }
}
