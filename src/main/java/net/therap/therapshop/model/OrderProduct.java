package net.therap.therapshop.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author aditya.chakma
 * @since 6/3/21
 */
@Entity
@Table(name = "order_product")
@NamedQueries(value = {
        @NamedQuery(name = "orderProduct.findAllByOrderId",
                query = "SELECT op FROM OrderProduct op WHERE op.order.id=:orderId")
})
public class OrderProduct extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 0, message = "Quantity can not be negative")
    private int quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Override
    public int getId() {
        return id;
    }

    @Override
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
    public boolean isNew() {
        return this.id == 0;
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
                getOrder().equals(((OrderProduct) o).getOrder()) &&
                getProduct().equals(((OrderProduct) o).getProduct());
    }
}
