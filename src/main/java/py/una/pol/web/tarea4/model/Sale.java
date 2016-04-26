package py.una.pol.web.tarea4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by codiumsa on 12/3/16.
 */

public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date date = new Date();

    private Double amount;


    @JsonIgnore
    private Customer customer;

    private List<SaleOrder> orders = new ArrayList<SaleOrder>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<SaleOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<SaleOrder> orders) {
        this.orders = orders;
    }
}
