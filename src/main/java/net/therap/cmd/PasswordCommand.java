package net.therap.cmd;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author aditya.chakma
 * @since 6/10/21
 */
public class PasswordCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private int userId;

    @NotNull
    @Size(min = 1, max = 50)
    private String oldPassword;

    @NotNull
    @Size(min = 1, max = 50)
    private String newPassword;

    @NotNull
    @Size(min = 1, max = 50)
    private String reTypePassword;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReTypePassword() {
        return reTypePassword;
    }

    public void setReTypePassword(String reTypePassword) {
        this.reTypePassword = reTypePassword;
    }
}
