package net.therap.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author aditya.chakma
 * @since 6/3/21
 */
@Entity
@Table(name = "complaint_reply")
public class ComplaintReply implements Serializable, Comparable<ComplaintReply> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 1, max = 500)
    private String message;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "complaint_id", referencedColumnName = "id")
    private Complaint complaint;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public boolean isNew() {
        return this.id == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (Objects.nonNull(o) && (o instanceof ComplaintReply)) {
            return getId() == ((ComplaintReply) o).getId();
        }

        return false;
    }

    @Override
    public int compareTo(ComplaintReply cr) {
        return this.getId() >= cr.getId() ? 1 : -1;
    }
}
