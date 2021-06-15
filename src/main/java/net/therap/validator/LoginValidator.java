package net.therap.validator;

import net.therap.cmd.LoginCommand;
import net.therap.dao.UserDao;
import net.therap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/9/21
 */
@Component
public class LoginValidator implements Validator {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder pe;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginCommand loginCommand = (LoginCommand) target;

        List<User> users = userDao.findByEmail(loginCommand.getEmail());

        if (users.size() == 0) {
            errors.rejectValue("password", "login.failed");
            return;
        }

        User user = users.get(0);

        if (!pe.matches(loginCommand.getPassword(), user.getHashedPassword())) {
            errors.rejectValue("password", "login.failed");
        }
    }
}
