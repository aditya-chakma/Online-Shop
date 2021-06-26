package net.therap.therapshop.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Objects;

/**
 * @author al.imran
 * @since 09/06/2021
 */
@Entity
@Table(name = "rating")
@NamedQueries(value = {
        @NamedQuery(name = "rating.findByUserAndProductId",
                query = "SELECT r FROM Rating r WHERE r.user.id = :userId AND r.product.id = :productId"),
        @NamedQuery(name = "rating.findByProductId",
                query = "SELECT COUNT(r), SUM(r.ratingValue) FROM Rating r WHERE r.product.id = :productId")
})
public class Rating extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(0)
    @Max(10)
    @Column(name = "rating_value")
    private int ratingValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean isNew() {
        return id == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Rating)) {
            return false;
        }

        return getUser().equals(((Rating) o).getUser()) &&
                getProduct().equals(((Rating) o).getProduct());
    }
}
