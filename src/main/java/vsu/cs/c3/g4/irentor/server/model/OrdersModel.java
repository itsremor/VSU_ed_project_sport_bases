package vsu.cs.c3.g4.irentor.server.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class OrdersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "user")
    private Long user;

    @Column(nullable = false, name = "item")
    private Long item;

    @Column(nullable = false, name = "start")
    private Date start;

    @Column(nullable = false, name = "duration")
    private Integer duration;

    @Column(nullable = false, name = "price")
    private Float price;

    @Column(nullable = false, name = "status")
    private Long status;

    public OrdersModel() {
    }

    public OrdersModel(Long user, Long item, Date start, Integer duration, Float price) {
        this.user = user;
        this.item = item;
        this.start = start;
        this.duration = duration;
        this.price = price;
        this.status = 2L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
