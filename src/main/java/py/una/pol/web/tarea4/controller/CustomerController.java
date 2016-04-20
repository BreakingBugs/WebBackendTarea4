package py.una.pol.web.tarea4.controller;

import py.una.pol.web.tarea4.exceptions.OutOfStockException;
import py.una.pol.web.tarea4.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Stateless
public class CustomerController {
    @PersistenceContext(name = "Tarea3DS")
    private EntityManager em;

    @Inject
    ItemController itemController;

    public List<Customer> getCustomers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);
        cq.select(root);
        TypedQuery<Customer> query = em.createQuery(cq);

        return query.getResultList();
    }

    public void addCustomer(Customer c) {
        em.persist(c);
    }

    public void sellToClient(Integer clientId, List<Order> orders) throws OutOfStockException, RuntimeException {
        Customer c = this.getCustomer(clientId);
        if (c == null) {
            throw new RuntimeException();
        }

        Sale s = new Sale();

        Double totalAmount = 0.0;

        for (Order o : orders) {
            SaleOrder so = createSaleOrder(o);
            so.setSale(s);
            em.persist(so);
            s.getOrders().add(so);

            Item i = so.getItem();
            Double total = i.getPrice() * so.getAmount();
            totalAmount += total;

            i.setStock(i.getStock() - so.getAmount());
            em.merge(i);
        }
        s.setAmount(totalAmount);
        s.setCustomer(c);
        em.persist(s);
        c.getSales().add(s);
        c.setAmountToPay(c.getAmountToPay() + s.getAmount());
        em.merge(c);
    }

    public SaleOrder createSaleOrder(Order o) throws OutOfStockException {
        SaleOrder so = new SaleOrder();
        Item i = itemController.getItem(o.getItem());
        if (i == null) {
            return null;
        }

        Integer amount = o.getAmount();

        if(i.getStock() == 0 || i.getStock() < o.getAmount()) {
            throw new OutOfStockException();
        }

        so.setItem(i);
        so.setAmount(amount);
        return so;
    }

    public boolean addPayment(Integer clientId, Payment payment) {
        Customer c = this.getCustomer(clientId);
        if (c == null) {
            return false;
        }

        Double monto = payment.getAmount();
        c.setAmountToPay(c.getAmountToPay() - monto);
        c.getPayments().add(payment);
        payment.setCustomer(c);
        em.persist(payment);
        return true;
    }

    public Customer getCustomer(Integer id) {
        return em.find(Customer.class, id);
    }

    public Customer updateCustomer(Integer id, Customer customerWithChanges) {
        Customer c = getCustomer(id);
        if (c != null) {
            if (customerWithChanges.getName() != null && customerWithChanges.getName().compareTo(c.getName()) != 0) {
                c.setName(customerWithChanges.getName());
            }
        }
        return c;
    }

    public void removeCustomer(final Integer id) {
        Customer c = getCustomer(id);
        if (c != null) {
            em.remove(c);
        }
    }
}
