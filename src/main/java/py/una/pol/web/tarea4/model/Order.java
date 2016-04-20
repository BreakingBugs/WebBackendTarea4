package py.una.pol.web.tarea4.model;

import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer item;

    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }
}
