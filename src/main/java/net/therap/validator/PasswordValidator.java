package net.therap.validator;

import net.therap.cmd.PasswordCommand;
import net.therap.model.User;
import net.therap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author aditya.chakma
 * @since 6/10/21
 */
@Component
public class PasswordValidator implements Validator {

    @Autowired
    private PasswordEncoder pe;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordCommand pc = (PasswordCommand) target;

        User user = userService.findById(pc.getUserId());

        if (!pc.getNewPassword().equals(pc.getReTypePassword())) {
            errors.rejectValue("reTypePassword", "error.pwmitchmatch");
        }

        if (!pe.matches(pc.getOldPassword(), user.getHashedPassword())) {
            errors.rejectValue("oldPassword", "error.pwmitchmatch");
        }
    }
}
