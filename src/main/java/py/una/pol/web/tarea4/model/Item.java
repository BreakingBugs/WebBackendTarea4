package py.una.pol.web.tarea4.model;
import java.io.Serializable;


public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Double price;

    private Integer stock = 0;

    private DuplicateItem duplicate;

    private Provider provider;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public DuplicateItem getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(DuplicateItem duplicate) {
        this.duplicate = duplicate;
    }
}
