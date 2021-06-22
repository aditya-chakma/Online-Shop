package net.therap.therapshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author al.imran
 * @since 02/06/2021
 */
@Entity
@Table(name = "category")
@NamedQueries(value = {
        @NamedQuery(name = "category.findAll",
                query = "SELECT c FROM Category c"),
        @NamedQuery(name = "category.findByName",
                query = "SELECT c FROM Category c WHERE c.name = :name")
})
public class Category extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    public Category() {
        this.products = new HashSet<>();
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean isNew() {
        return id == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Category)) {
            return false;
        }

        return Objects.equals(getId(), ((Category) o).getId()) &&
                Objects.equals(getName(), ((Category) o).getName());
    }
}
