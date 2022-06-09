package vsu.cs.c3.g4.irentor.server.model;


import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class CategoriesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    public CategoriesModel() {
    }

    public CategoriesModel(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
