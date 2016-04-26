package py.una.pol.web.tarea4.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Double amountToPay;

    private List<Payment> payments = new ArrayList<Payment>();

    private List<Sale> sales = new ArrayList<Sale>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

}
