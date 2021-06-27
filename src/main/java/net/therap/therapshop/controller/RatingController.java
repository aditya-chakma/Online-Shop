package net.therap.therapshop.controller;

import net.therap.therapshop.editor.ProductEditor;
import net.therap.therapshop.editor.UserEditor;
import net.therap.therapshop.model.Product;
import net.therap.therapshop.model.Rating;
import net.therap.therapshop.model.User;
import net.therap.therapshop.service.RatingService;
import net.therap.therapshop.util.AccesChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author al.imran
 * @since 09/06/2021
 */
@Controller
public class RatingController {

    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_PRODUCT_DETAILS = "redirect:/productDetails";

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ProductEditor productEditor;

    @Autowired
    private UserEditor userEditor;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Product.class, "product", productEditor);
        webDataBinder.registerCustomEditor(User.class, "user", userEditor);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rate")
    public String process(@Valid @ModelAttribute Rating rating,
                          BindingResult bindingResult,
                          ModelMap modelMap,
                          RedirectAttributes redirectAttributes,
                          HttpSession httpSession) {

        redirectAttributes.addFlashAttribute("redirectProductId", rating.getProduct().getId());

        if (AccesChecker.isCustomer(httpSession)) {
            if (bindingResult.hasErrors()) {
                return REDIRECT_PRODUCT_DETAILS;
            }

            ratingService.saveOrUpdate(rating);
            return REDIRECT_PRODUCT_DETAILS;

        } else {
            return REDIRECT_LOGIN;
        }
    }
}
