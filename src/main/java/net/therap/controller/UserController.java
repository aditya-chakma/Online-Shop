package net.therap.controller;

import net.therap.dao.UserDao;
import net.therap.model.User;
import net.therap.service.UserService;
import net.therap.util.AccesChecker;
import net.therap.validator.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import static net.therap.util.StringConst.SESSION_KEY_USER_ID;

/**
 * @author aditya.chakma
 * @since 6/5/21
 */
@Controller
public class UserController {

    private final String ATTRIBUTE_USER = "user";
    private final String VIEW_USER = "user";

    private final String REDIRECT_HOME = "redirect:/";

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationValidator registrationValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(registrationValidator);
    }

    @GetMapping(value = "/user")
    public String show(@RequestParam(defaultValue = "0") int id,
                       HttpSession session,
                       ModelMap model) {

        if (AccesChecker.isLoggedin(session)) {
            return REDIRECT_HOME;
        }

        User user = id == 0 ? new User() : userDao.findById(id);
        model.addAttribute(ATTRIBUTE_USER, user);
        return VIEW_USER;
    }

    @PostMapping(value = "/user")
    public String process(@Valid @ModelAttribute User user,
                          BindingResult result,
                          HttpSession session,
                          ModelMap model) throws IOException {

        if (AccesChecker.isLoggedin(session)) {
            return REDIRECT_HOME;
        }

        if (result.hasErrors()) {
            model.addAttribute(ATTRIBUTE_USER, user);
            return VIEW_USER;
        }

        userService.saveOrUpdate(user);
        return REDIRECT_HOME;
    }

    @GetMapping("/profileImage")
    public void image(HttpSession session,
                      HttpServletResponse response) throws IOException {

        response.getOutputStream().write(
                userService.getImageByteArray(
                        (int) session.getAttribute(SESSION_KEY_USER_ID)));
    }
}
