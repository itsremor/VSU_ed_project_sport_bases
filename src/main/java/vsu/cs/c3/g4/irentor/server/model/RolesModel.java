package vsu.cs.c3.g4.irentor.server.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RolesModel implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<UsersModel> users;

    public RolesModel() {
    }

    public RolesModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RolesModel(String name, Set<UsersModel> users) {
        this.name = name;
        this.users = users;
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

    public Set<UsersModel> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersModel> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
