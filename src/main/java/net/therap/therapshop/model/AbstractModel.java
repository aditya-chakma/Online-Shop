package net.therap.therapshop.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * @author aditya.chakma
 * @since 6/21/21
 */
@MappedSuperclass
public abstract class AbstractModel implements Serializable {

    public static final long serialVersionUID = 1l;

    @Version
    int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public abstract int getId();

    public abstract void setId(int id);

    public abstract boolean isNew();
}
