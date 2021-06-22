package net.therap.therapshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author al.imran
 * @since 03/06/2021
 */
@Entity
@Table(name = "product_image")
public class ProductImage extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "image_link", unique = true)
    @NotNull
    @Size(max = 100)
    private String imageLink;

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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
        return Objects.hash(imageLink);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (Objects.nonNull(o) && (o instanceof ProductImage)) {
            return imageLink.equals(((ProductImage) o).getImageLink());
        }

        return false;
    }
}
