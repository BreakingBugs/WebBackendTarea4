package py.una.pol.web.tarea3.controller;

import py.una.pol.web.tarea3.model.DuplicateItem;
import py.una.pol.web.tarea3.model.Item;

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

    public List<DuplicateItem> getDuplicates() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DuplicateItem> cq = cb.createQuery(DuplicateItem.class);
        Root<DuplicateItem> root = cq.from(DuplicateItem.class);
        cq.select(root);
        TypedQuery<DuplicateItem> query = em.createQuery(cq);

        return query.getResultList();
    }

    public void addDuplicate(Item p) {
        Item existing = itemController.getItemByName(p.getName());
        DuplicateItem dup = existing.getDuplicate();
        if (dup != null) {
            dup.setCantidad(dup.getCantidad() + 1);
            em.merge(dup);
        } else {
            dup = new DuplicateItem();
            dup.setCantidad(1);
            dup.setItem(existing);
            existing.setDuplicate(dup);
            em.persist(dup);
        }
        p.setId(existing.getId());
        p.setName(existing.getName());
        p.setPrice(existing.getPrice());
        p.setStock(existing.getStock());
        p.setDuplicate(existing.getDuplicate());
        p.setProvider(existing.getProvider());
    }

    public DuplicateItem getDuplicate(Integer id) {
        return em.find(DuplicateItem.class, id);
    }
}
