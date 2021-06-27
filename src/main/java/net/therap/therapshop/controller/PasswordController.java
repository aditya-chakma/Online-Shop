package net.therap.therapshop.controller;

import net.therap.therapshop.cmd.PasswordCommand;
import net.therap.therapshop.model.User;
import net.therap.therapshop.service.UserService;
import net.therap.therapshop.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author aditya.chakma
 * @since 6/10/21
 */
@Controller
public class PasswordController {

    private static final String VIEW_UPDATE_PW = "updatePassword";

    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_PROFILE = "redirect:/profile";

    private static final String COMMAND_PW = "passwordCommand";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordValidator passwordValidator;

    @InitBinder
    public void initBInder(WebDataBinder binder) {
        binder.addValidators(passwordValidator);
    }

    @RequestMapping(method = RequestMethod.GET, value = "updatePassword")
    public String updatePassword(HttpSession session,
                                 ModelMap model) {

        PasswordCommand pc = new PasswordCommand();
        pc.setUserId((int) session.getAttribute("id"));
        model.addAttribute(COMMAND_PW, pc);

        return VIEW_UPDATE_PW;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updatePassword")
    public String updatePassword(@Valid @ModelAttribute PasswordCommand passwordCommand,
                                 BindingResult result,
                                 ModelMap model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute(COMMAND_PW, passwordCommand);
            return VIEW_UPDATE_PW;
        }

        User user = userService.findById(passwordCommand.getUserId());
        user.setHashedPassword(passwordCommand.getNewPassword());
        userService.saveOrUpdatePwOnly(user);

        return REDIRECT_PROFILE;
    }
}
