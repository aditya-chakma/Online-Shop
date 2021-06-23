package net.therap.therapshop.model;

import net.therap.therapshop.util.ProductStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * @author al.imran
 * @since 03/06/2021
 */
@Entity
@Table(name = "product")
@NamedQueries(value = {
        @NamedQuery(name = "product.findAll",
                query = "SELECT p FROM Product p"),
        @NamedQuery(name = "product.findAllByName",
                query = "SELECT p FROM Product p WHERE p.name LIKE :name"),
        @NamedQuery(name = "product.findByName",
                query = "SELECT p FROM Product p WHERE p.name = :name"),
        @NamedQuery(name = "product.findByCategoryId",
                query = "SELECT p FROM Product p WHERE p.category.id = :categoryId")
})
public class Product extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    private Set<CartItem> cartItems;

    @Min(value = 0)
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = true)
    private double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Size(max = 500)
    private String details;

    @Column(name = "created_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Transient
    private List<MultipartFile> images;

    public Product() {
        this.productImages = new HashSet<>();
        this.images = new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(Set<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    @Override
    public boolean isNew() {
        return id == 0;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Product)) {
            return false;
        }

        return Objects.equals(getId(), ((Product) o).getId()) &&
                Objects.equals(getName(), ((Product) o).getName());
    }
}
