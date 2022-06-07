package vsu.cs.c3.g4.irentor.server.model;


import javax.persistence.*;

@Entity
@Table(name = "items")
public class ItemsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "category")
    private Long category;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "url")
    private String url;

    @Column(nullable = false, name = "price")
    private Float price;

    @Column(nullable = false, name = "status")
    private Long status;

    public ItemsModel() {
    }

    public ItemsModel(Long category, String name, String description, Float price) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.url = "nopick";
        this.price = price;
        this.status = 1L;
    }

    public ItemsModel(Long category, String name, String description, String url, Float price) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.url = url;
        this.price = price;
        this.status = 1L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
