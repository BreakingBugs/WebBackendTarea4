package py.una.pol.web.tarea4.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEMS_SEQ")
//    @SequenceGenerator(name = "ITEMS_SEQ", sequenceName = "SEQUENCE_ITEMS")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    private Double price;

    private Integer stock = 0;

    @OneToOne(mappedBy = "item")
    private DuplicateItem duplicate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_id")
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
