package net.therap.therapshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author aditya.chakma
 * @since 6/21/21
 */
@MappedSuperclass
public abstract class AbstractModel implements Serializable {

    public static final long serialVersionUID = 1l;

    @Version
    int version;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public abstract int getId();

    public abstract void setId(int id);

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

    public abstract boolean isNew();
}
