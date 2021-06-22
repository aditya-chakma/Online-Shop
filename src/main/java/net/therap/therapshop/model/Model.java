package net.therap.therapshop.model;

import javax.persistence.Version;

/**
 * @author aditya.chakma
 * @since 6/21/21
 */
public class Model {

    @Version
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
