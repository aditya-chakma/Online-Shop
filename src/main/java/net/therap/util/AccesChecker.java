package net.therap.util;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static net.therap.util.StringConst.SESSION_KEY_LOGGEDIN;
import static net.therap.util.StringConst.SESSION_KEY_USER_ROLE;

/**
 * @author aditya.chakma
 * @since 6/10/21
 */
public class AccesChecker {

    public static boolean isAdmin(HttpSession session) {
        if (UserRole.ADMIN.equals(session.getAttribute(SESSION_KEY_USER_ROLE))) {
            return true;
        }

        return false;
    }

    public static boolean isCustomer(HttpSession session) {
        if (UserRole.CUSTOMER.equals(session.getAttribute(SESSION_KEY_USER_ROLE))) {
            return true;
        }

        return false;
    }

    public static boolean isLoggedin(HttpSession session) {
        Object o = session.getAttribute(SESSION_KEY_LOGGEDIN);

        return (Objects.nonNull(o) && o.equals(true));
    }
}
