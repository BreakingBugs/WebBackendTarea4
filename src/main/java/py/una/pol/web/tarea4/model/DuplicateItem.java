package py.una.pol.web.tarea4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class DuplicateItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @JsonIgnore
    private Item item;

    private Integer cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
