package net.therap.therapshop.util;

import javax.servlet.http.HttpSession;

/**
 * @author aditya.chakma
 * @since 6/10/21
 */
public class AccesChecker {

    public static boolean isAdmin(HttpSession session) {
        return UserRole.ADMIN.equals(session.getAttribute(StringConst.SESSION_KEY_USER_ROLE));
    }

    public static boolean isCustomer(HttpSession session) {
        return UserRole.CUSTOMER.equals(session.getAttribute(StringConst.SESSION_KEY_USER_ROLE));
    }

    public static boolean isLoggedin(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute(StringConst.SESSION_KEY_LOGGEDIN);

        return Boolean.TRUE.equals(loggedIn);
    }
}
