package net.therap.therapshop.controller;

import net.therap.therapshop.model.User;
import net.therap.therapshop.service.UserService;
import net.therap.therapshop.util.StringConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author aditya.chakma
 * @since 6/10/21
 */
@Controller
public class ProfileController {

    private static final String VIEW_PROFILE = "profile";
    private static final String VIEW_USER_UPDATE = "userUpdate";

    private static final String COMMAND_USER = "user";

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(value = "/profile")
    public String show(HttpSession session,
                       ModelMap model) {

        model.addAttribute(COMMAND_USER, userService.findById((int) session.getAttribute(StringConst.SESSION_KEY_USER_ID)));
        return VIEW_PROFILE;
    }

    @GetMapping(value = "/updateProfile")
    public String updateProfile(HttpSession session,
                                ModelMap model) {

        int id = (int) session.getAttribute(StringConst.SESSION_KEY_USER_ID);
        User user = userService.findById(id);

        model.addAttribute(StringConst.SESSION_KEY_LOGGEDIN, true);
        model.addAttribute(COMMAND_USER, user);

        return VIEW_USER_UPDATE;
    }

    @PostMapping(value = "/updateProfile")
    public String updateProfile(@Valid @ModelAttribute User user,
                                BindingResult result,
                                HttpSession session,
                                ModelMap model) throws IOException {

        model.addAttribute(COMMAND_USER, user);

        if (result.hasErrors()) {
            return VIEW_USER_UPDATE;
        }

        userService.saveOrUpdate(user);
        return VIEW_PROFILE;
    }
}
