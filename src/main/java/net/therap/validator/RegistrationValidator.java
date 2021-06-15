package net.therap.validator;

import net.therap.model.User;
import net.therap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author aditya.chakma
 * @since 6/10/21
 */
@Service
public class RegistrationValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (userService.finByEmail(user.getEmail()).size() > 0) {
            errors.rejectValue("email", "user.email.exist");
        }
    }
}
