package py.una.pol.web.tarea4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public class SaleOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Item item;

    private Integer amount;


    @JsonIgnore
    private Sale sale;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
