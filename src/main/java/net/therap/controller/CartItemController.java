package net.therap.controller;

import net.therap.editor.ProductEditor;
import net.therap.editor.UserEditor;
import net.therap.model.CartItem;
import net.therap.model.Product;
import net.therap.model.User;
import net.therap.service.CartItemService;
import net.therap.util.AccesChecker;
import net.therap.util.Pair;
import net.therap.validator.CartItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static net.therap.util.StringConst.*;

/**
 * @author al.imran
 * @since 08/06/2021
 */
@Controller
public class CartItemController {

    private static final String COMMAND_CART_ITEMS = "cartItemsCmd";
    private static final String COMMAND_GRAND_TOTAL = "grandTotalCmd";
    private static final String COMMAND_CART_ITEM = "cartItemCmd";
    private static final String COMMAND_ERROR = "error";
    private static final String COMMAND_MESSAGE = "message";

    private static final String CART_ITEM_VIEW = "cartItem";

    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_CART_ITEM_VIEW = "redirect:/cartItem";

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductEditor productEditor;

    @Autowired
    private UserEditor userEditor;

    @Autowired
    private CartItemValidator cartItemValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(cartItemValidator);
        webDataBinder.registerCustomEditor(Product.class, "product", productEditor);
        webDataBinder.registerCustomEditor(User.class, "user", userEditor);
    }

    private void setUpModelMapForCartItemAddUpdate(ModelMap modelMap,
                                                   CartItem cartItem,
                                                   int userId) {

        Pair<List<CartItem>, Double> pair = cartItemService.getCartItemByUserId(userId);
        modelMap.addAttribute(COMMAND_CART_ITEMS, pair.getFirst());
        modelMap.addAttribute(COMMAND_GRAND_TOTAL, pair.getSecond());
        modelMap.addAttribute(COMMAND_CART_ITEM, cartItem);
    }

    private void setUpErrorField(BindingResult bindingResult,
                                 ModelMap modelMap) {

        if (bindingResult.hasFieldErrors("quantity")) {
            modelMap.addAttribute(COMMAND_ERROR, bindingResult.getFieldError("quantity").getDefaultMessage());
        } else if (bindingResult.hasFieldErrors("id")) {
            modelMap.addAttribute(COMMAND_ERROR, bindingResult.getFieldError("id").getDefaultMessage());
        }
    }

    @GetMapping(value = "/cartItem")
    public String show(ModelMap modelMap,
                       HttpSession httpSession) {

        if (AccesChecker.isCustomer(httpSession)) {
            setUpModelMapForCartItemAddUpdate(modelMap, new CartItem(),
                    (Integer) httpSession.getAttribute(SESSION_KEY_USER_ID));

            return CART_ITEM_VIEW;
        } else {
            return REDIRECT_LOGIN;
        }
    }

    @PostMapping(value = "/cartItem")
    public String process(@Valid @ModelAttribute(name = "cartItemCmd") CartItem cartItem,
                          BindingResult bindingResult,
                          HttpSession httpSession,
                          ModelMap modelMap,
                          RedirectAttributes redirectAttributes) {

        if (AccesChecker.isCustomer(httpSession)) {
            if (bindingResult.hasErrors()) {
                setUpModelMapForCartItemAddUpdate(modelMap, cartItem,
                        (Integer) httpSession.getAttribute(SESSION_KEY_USER_ID));

                setUpErrorField(bindingResult, modelMap);

                return CART_ITEM_VIEW;
            }

            redirectAttributes.addFlashAttribute(COMMAND_MESSAGE,
                    messageSource.getMessage("message.cartUpdate", null, null));

            cartItemService.saveOrUpdate(cartItem);
            return REDIRECT_CART_ITEM_VIEW;
        } else {
            return REDIRECT_LOGIN;
        }
    }

    @PostMapping(value = "/cartItemDelete")
    public String processDelete(@RequestParam(defaultValue = "0") int cartItemId,
                                HttpSession httpSession,
                                RedirectAttributes redirectAttributes) {

        if (AccesChecker.isCustomer(httpSession)) {
            cartItemService.remove(cartItemId);

            redirectAttributes.addFlashAttribute(COMMAND_MESSAGE,
                    messageSource.getMessage("message.cartRemoved", null, null));

            return REDIRECT_CART_ITEM_VIEW;
        } else {
            return REDIRECT_LOGIN;
        }
    }
}
