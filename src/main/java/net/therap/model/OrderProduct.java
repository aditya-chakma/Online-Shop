package net.therap.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author aditya.chakma
 * @since 6/3/21
 */
@Entity
@Table(name = "order_product")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Min(value = 0, message = "Quantity can not be negative")
    private int quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

        if (!(o instanceof OrderProduct)) {
            return false;
        }

        OrderProduct op = (OrderProduct) o;
        return Objects.equals(getId(), op.getId()) &&
                Objects.equals(getOrder().getId(), op.getOrder().getId()) &&
                Objects.equals(getProduct().getId(), op.getProduct().getId());
    }
}
