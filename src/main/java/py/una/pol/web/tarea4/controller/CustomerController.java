package py.una.pol.web.tarea4.controller;

import org.apache.ibatis.session.SqlSession;
import py.una.pol.web.tarea4.exceptions.OutOfStockException;
import py.una.pol.web.tarea4.initialization.MyBatisSingleton;
import py.una.pol.web.tarea4.mapper.CustomerMapper;
import py.una.pol.web.tarea4.mapper.ItemMapper;
import py.una.pol.web.tarea4.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class CustomerController {
    @PersistenceContext(name = "Tarea3DS")
    private EntityManager em;

    @Inject
    ItemController itemController;

    @Inject
    private MyBatisSingleton myBatis;

    public List<Customer> getCustomers() {
        List<Customer> customers;
        SqlSession session = myBatis.getFactory().openSession();
        try {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            customers = mapper.getCustomers();
        } finally {
            session.close();
        }
        return customers;
    }

    public void addCustomer(Customer c) {
        SqlSession session = myBatis.getFactory().openSession();
        try {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.insertCustomer(c);
        }
        finally {
            session.close();
        }
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
        Customer customer;
        SqlSession session = myBatis.getFactory().openSession();
        try {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            customer = mapper.getCustomer(id);
        } finally {
            session.close();
        }
        return customer;
    }

    public Customer updateCustomer(Integer id, Customer customerWithChanges) {
        SqlSession session = myBatis.getFactory().openSession();
        Customer c = getCustomer(id);
        if(customerWithChanges.getName() != null && customerWithChanges.getName().compareTo(c.getName()) != 0) {
            c.setName(customerWithChanges.getName());
        }
        if(customerWithChanges.getAmountToPay() != null && customerWithChanges.getAmountToPay().compareTo(c.getAmountToPay()) != 0) {
            c.setAmountToPay(customerWithChanges.getAmountToPay());
        }
        try {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.updateCustomer(c);
        }
        finally {
            session.close();
        }
        return c;
    }

    public void removeCustomer(final Integer id) {
        SqlSession session = myBatis.getFactory().openSession();
        try {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.deleteCustomer(id);
        }
        finally {
            session.close();
        }
    }
}
