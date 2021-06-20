package net.therap.therapshop.util;

import net.therap.therapshop.cmd.LoginCommand;
import net.therap.therapshop.dao.UserDao;
import net.therap.therapshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author aditya.chakma
 * @since 6/9/21
 */
@Service
public class SessionHelper {

    @Autowired
    private UserDao userDao;

    public void setSession(HttpServletRequest request, LoginCommand loginCommand) {
        HttpSession session = request.getSession();
        User user = userDao.findByEmail(loginCommand.getEmail()).get(0);

        session.setAttribute(StringConst.SESSION_KEY_USER_ID, user.getId());
        session.setAttribute(StringConst.SESSION_KEY_USER_ROLE, user.getRole());
        session.setAttribute(StringConst.SESSION_KEY_LOGGEDIN, true);
    }

    public void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (Objects.nonNull(session.getAttribute(StringConst.SESSION_KEY_USER_ID))) {
            session.removeAttribute(StringConst.SESSION_KEY_USER_ID);
        }

        if (Objects.nonNull(session.getAttribute(StringConst.SESSION_KEY_USER_ROLE))) {
            session.removeAttribute(StringConst.SESSION_KEY_USER_ROLE);
        }

        if (Objects.nonNull(session.getAttribute(StringConst.SESSION_KEY_LOGGEDIN))) {
            session.removeAttribute(StringConst.SESSION_KEY_LOGGEDIN);
        }
    }
}
