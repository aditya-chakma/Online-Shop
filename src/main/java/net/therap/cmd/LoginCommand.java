package net.therap.cmd;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author aditya.chakma
 * @since 6/9/21
 */
public class LoginCommand implements Serializable {

    private static long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 100)
    @Email
    private String email;

    @NotNull
    @Size(min = 1, max = 100)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
