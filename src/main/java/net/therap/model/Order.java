package net.therap.model;

import net.therap.util.OrderStatus;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author aditya.chakma
 * @since 6/3/21
 */
@Entity
@Table(name = "`order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sub_total")
    @NotNull
    @DecimalMin("0.0")
    private double subTotal;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<OrderProduct> orderProducts;

    public Order() {
        this.orderProducts = new HashSet<>();
        this.status = OrderStatus.PENDING;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public boolean isDelivered() {
        return this.status == OrderStatus.DELIVERED;
    }

    public boolean isNew() {
        return id == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Order)) {
            return false;
        }

        return Objects.equals(getId(), ((Order) o).getId());
    }
}
