package net.therap.therapshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

/**
 * @author aditya.chakma
 * @since 6/3/21
 */
@Entity
@Table(name = "complaint_reply")
@NamedQueries(value = {
        @NamedQuery(name = "complaintReply.findByComplaintId",
                query = "SELECT c FROM ComplaintReply c WHERE c.complaint.id = :complaintId")
})
public class ComplaintReply extends AbstractModel implements Comparable<ComplaintReply> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 1, max = 500)
    private String message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "complaint_id", referencedColumnName = "id")
    private Complaint complaint;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
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

        if (!(o instanceof ComplaintReply)) {
            return false;
        }

        return Objects.equals(getId(), ((ComplaintReply) o).getId());
    }

    @Override
    public int compareTo(ComplaintReply cr) {
        return this.getId() >= cr.getId() ? 1 : -1;
    }
}
