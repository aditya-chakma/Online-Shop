package net.therap.controller;

import net.therap.model.Category;
import net.therap.service.CategoryService;
import net.therap.util.AccesChecker;
import net.therap.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static net.therap.util.StringConst.*;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Controller
public class CategoryController {

    private static final String COMMAND_CATEGORY = "category";
    private static final String COMMAND_ERROR = "error";
    private static final String COMMAND_MESSAGE = "message";

    private static final String VIEW_CATEGORY = "category";

    private static final String REDIRECT_CATEGORY = "redirect:/category";
    private static final String REDIRECT_LOGIN = "redirect:/";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(categoryValidator);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private void setUpErrorField(BindingResult bindingResult,
                                 ModelMap modelMap) {

        if (bindingResult.hasFieldErrors("name")) {
            modelMap.addAttribute(COMMAND_ERROR, bindingResult.getFieldError("name").getDefaultMessage());
        }
    }

    @GetMapping(value = "/category")
    public String show(@RequestParam(defaultValue = "0") int categoryId,
                       ModelMap modelMap,
                       HttpSession httpSession) {

        if (AccesChecker.isAdmin(httpSession)) {
            modelMap.addAttribute(COMMAND_CATEGORY,
                    categoryId == 0 ? new Category() : categoryService.getCategoryById(categoryId));

            return VIEW_CATEGORY;
        } else {
            return REDIRECT_LOGIN;
        }
    }

    @PostMapping(value = "/category")
    public String process(@Valid @ModelAttribute Category category,
                          BindingResult bindingResult,
                          HttpSession httpSession,
                          ModelMap modelMap,
                          RedirectAttributes redirectAttributes,
                          SessionStatus sessionStatus) {

        if (AccesChecker.isAdmin(httpSession)) {
            if (bindingResult.hasErrors()) {
                modelMap.addAttribute(COMMAND_CATEGORY, category);
                setUpErrorField(bindingResult, modelMap);
                return VIEW_CATEGORY;
            }

            redirectAttributes.addFlashAttribute(COMMAND_MESSAGE,
                    messageSource.getMessage("message.categoryAdd", null, null));

            categoryService.saveOrUpdate(category);
            httpSession.setAttribute(SESSION_KEY_CATEGORY_LIST, categoryService.getAllCategory());
            return REDIRECT_CATEGORY;
        } else {
            return REDIRECT_LOGIN;
        }
    }
}
