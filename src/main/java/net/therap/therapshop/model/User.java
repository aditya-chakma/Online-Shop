package net.therap.therapshop.model;

import net.therap.therapshop.util.UserRole;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static net.therap.therapshop.util.StringConst.DEFAULT_IMAGE;
import static net.therap.therapshop.util.StringConst.REGEX_MOBILE_NUMBER;

/**
 * @author aditya.chakma
 * @since 6/3/21
 */
@Entity
@Table(name = "user")
@NamedQueries(value = {
        @NamedQuery(name = "user.byEmail", query = "SELECT u FROM User u WHERE u.email=:email")
})
public class User extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Column(unique = true)
    @NotNull
    @Size(min = 1, max = 50)
    @Email
    private String email;

    @Column(name = "mobile_no")
    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = REGEX_MOBILE_NUMBER, message = "Enter a valid mobile number")
    private String mobileNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "password")
    @NotNull
    @Size(min = 1, max = 100)
    private String hashedPassword;

    @Size(min = 0, max = 200)
    private String address;

    @Column(name = "image_link")
    @NotNull
    @Size(min = 1, max = 100)
    private String imageLink;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Complaint> complaints;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Order> orders;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<CartItem> cartItems;

    @Transient
    private MultipartFile image;

    public User() {
        this.complaints = new HashSet<>();
        this.orders = new HashSet<>();
        this.cartItems = new HashSet<>();
        this.role = UserRole.CUSTOMER;
        this.imageLink = DEFAULT_IMAGE;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Set<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        this.complaints = complaints;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public boolean isNew() {
        return this.id == 0;
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

        if (!(o instanceof User)) {
            return false;
        }

        return Objects.equals(getEmail(), ((User) o).getEmail());
    }
}
