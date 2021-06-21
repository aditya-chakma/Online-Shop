package net.therap.therapshop.controller;

import net.therap.therapshop.cmd.LoginCommand;
import net.therap.therapshop.util.AccesChecker;
import net.therap.therapshop.util.SessionHelper;
import net.therap.therapshop.validator.LoginValidator;
import org.apache.log4j.Logger;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author aditya.chakma
 * @since 6/3/21
 */
@Controller
public class LoginController {

    private static final String VIEW_LOGIN = "login";

    private static final String REDIRECT_HOME = "redirect:/";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_PL = "redirect:/productList";

    private static final String COMMAND_LANG = "lang";
    private static final String COMMAND_LOGIN = "loginCommand";

    private static final String EN = "en";
    private static final String BN = "bn";

    @Autowired
    private LoginValidator loginValidator;

    @Autowired
    private SessionHelper sessionHelper;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(loginValidator);
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        ModelMap model) {

        if (AccesChecker.isLoggedin(request.getSession())) {
            return REDIRECT_HOME;
        }

        setUpAttribute(model, request, new LoginCommand());
        return VIEW_LOGIN;
    }

    @PostMapping(value = "/login")
    public String login(@Valid @ModelAttribute LoginCommand loginCommand,
                        BindingResult result,
                        HttpServletRequest request,
                        ModelMap model) {

        if (result.hasErrors()) {
            setUpAttribute(model, request, loginCommand);
            return VIEW_LOGIN;
        }

        sessionHelper.setSession(request, loginCommand);
        return REDIRECT_PL;
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {

        sessionHelper.clearSession(request);
        return REDIRECT_LOGIN;
    }

    private void setUpAttribute(ModelMap model, HttpServletRequest request, LoginCommand login) {
        if (BN.equals(request.getParameter(COMMAND_LANG))) {
            model.addAttribute(COMMAND_LANG, BN);

        } else {
            model.addAttribute(COMMAND_LANG, EN);
        }

        model.addAttribute(COMMAND_LOGIN, login);
    }
}
