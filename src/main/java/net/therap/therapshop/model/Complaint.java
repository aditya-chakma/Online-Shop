package net.therap.therapshop.model;

import net.therap.therapshop.util.ComplaintStatus;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "complaint")
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private User user;

    @OneToMany(mappedBy = "complaint", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ComplaintReply> complaintReplies;

    @Valid
    @Transient
    private ComplaintReply reply;

    public Complaint() {
        this.status = ComplaintStatus.OPEN;
        this.complaintReplies = new HashSet<>();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ComplaintReply> getComplaintReplies() {
        return complaintReplies;
    }

    public void setComplaintReplies(Set<ComplaintReply> complaintReplies) {
        this.complaintReplies = complaintReplies;
    }

    public boolean isNew() {
        return this.id == 0;
    }

    public ComplaintReply getReply() {
        return reply;
    }

    public void setReply(ComplaintReply reply) {
        this.reply = reply;
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

        if (!(o instanceof Complaint)) {
            return false;
        }

        return Objects.equals(getId(), ((Complaint) o).getId());
    }
}
