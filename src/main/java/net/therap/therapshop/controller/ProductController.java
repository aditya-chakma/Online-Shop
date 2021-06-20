package net.therap.therapshop.controller;

import net.therap.therapshop.editor.CategoryEditor;
import net.therap.therapshop.editor.ProductEditor;
import net.therap.therapshop.editor.UserEditor;
import net.therap.model.*;
import net.therap.service.*;
import net.therap.therapshop.model.*;
import net.therap.therapshop.service.CategoryService;
import net.therap.therapshop.service.ProductService;
import net.therap.therapshop.service.RatingService;
import net.therap.therapshop.service.UserService;
import net.therap.therapshop.util.AccesChecker;
import net.therap.therapshop.util.ProductStatus;
import net.therap.therapshop.util.StringConst;
import net.therap.therapshop.validator.ProductValidator;
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

import java.util.Objects;

/**
 * @author al.imran
 * @since 03/06/2021
 */
@Controller
@SessionAttributes("redirectProductId")
public class ProductController {

    private static final String COMMAND_PRODUCT = "product";
    private static final String COMMAND_PRODUCT_LIST = "productList";
    private static final String COMMAND_CATEGORY_LIST = "listOfCategory";
    private static final String COMMAND_IS_ADMIN = "isAdmin";
    private static final String COMMAND_RATING = "rating";
    private static final String COMMAND_AVERAGE_RATING = "averageRatingValue";
    private static final String COMMAND_CART_ITEM = "cartItemCmd";
    private static final String COMMAND_LANG = "lang";
    private static final String COMMAND_ERROR = "error";
    private static final String COMMAND_MESSAGE = "message";

    private static final String PRODUCT_VIEW = "product";
    private static final String PRODUCT_LIST_VIEW = "productList";
    private static final String PRODUCT_DETAILS_VIEW = "productDetails";

    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_PRODUCT = "redirect:/product";
    private static final String REDIRECT_PRODUCT_LIST = "redirect:/productList";

    private static final String EN = "en";
    private static final String BN = "bn";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryEditor categoryEditor;

    @Autowired
    private ProductEditor productEditor;

    @Autowired
    private UserEditor userEditor;

    @Autowired
    private ProductValidator productValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder(value = "product")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(productValidator);

        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Category.class, "category", categoryEditor);
        webDataBinder.registerCustomEditor(Product.class, "product", productEditor);
        webDataBinder.registerCustomEditor(User.class, "user", userEditor);
    }

    private void setUpErrorField(BindingResult bindingResult,
                                 ModelMap modelMap) {

        if (bindingResult.hasFieldErrors("name")) {
            modelMap.addAttribute(COMMAND_ERROR, bindingResult.getFieldError("name").getDefaultMessage());

        } else if (bindingResult.hasFieldErrors("quantity")) {
            modelMap.addAttribute(COMMAND_ERROR, bindingResult.getFieldError("quantity").getDefaultMessage());

        } else if (bindingResult.hasFieldErrors("price")) {
            modelMap.addAttribute(COMMAND_ERROR, bindingResult.getFieldError("price").getDefaultMessage());
        }
    }

    private void setUpModelMapForProductAddUpdate(ModelMap modelMap,
                                                  Product product,
                                                  String lang) {

        modelMap.addAttribute(COMMAND_LANG, BN.equals(lang) ? BN : EN);
        modelMap.addAttribute(COMMAND_PRODUCT, product);
        modelMap.addAttribute(COMMAND_CATEGORY_LIST, categoryService.getAllCategory());
    }

    private void setUpModelMapForProductList(ModelMap modelMap,
                                             int categoryId,
                                             int userId,
                                             String productName) {

        CartItem cartItem = new CartItem();
        cartItem.setUser(userService.findById(userId));

        modelMap.addAttribute(COMMAND_CART_ITEM, cartItem);
        modelMap.addAttribute(COMMAND_IS_ADMIN, false);
        modelMap.addAttribute(COMMAND_PRODUCT_LIST, categoryId == 0 ?
                productService.getAllProductByName(productName) :
                productService.getProductByCategoryId(categoryId));
    }

    private void setUpModelMapForProductDetails(ModelMap modelMap,
                                                int productId,
                                                int userId) {

        Product product = productService.getProductById(productId);
        User user = userService.findById(userId);

        Rating rating = ratingService.getRatingByUserIdAndProductId(userId, productId);

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);

        modelMap.addAttribute(COMMAND_CART_ITEM, cartItem);
        modelMap.addAttribute(COMMAND_RATING, rating);
        modelMap.addAttribute(COMMAND_PRODUCT, product);
        modelMap.addAttribute(COMMAND_AVERAGE_RATING, ratingService.getRatingValue(productId));
    }

    @GetMapping(value = {"/", "/product"})
    public String show(@RequestParam(value = "id", defaultValue = "0") int productId,
                       @RequestParam(defaultValue = EN) String lang,
                       ModelMap modelMap,
                       HttpSession httpSession) {

        if (AccesChecker.isAdmin(httpSession)) {
            Product product = (productId == 0 ? new Product() : productService.getProductById(productId));

            if (Objects.isNull(product.getStatus())) {
                product.setStatus(ProductStatus.IN_STOCK);
            }

            setUpModelMapForProductAddUpdate(modelMap, product, lang);
            return PRODUCT_VIEW;

        } else {
            return REDIRECT_LOGIN;
        }
    }

    @PostMapping(value = "/product")
    public String process(@Valid @ModelAttribute("product") Product product,
                          BindingResult bindingResult,
                          @RequestParam(defaultValue = EN) String lang,
                          HttpSession httpSession,
                          ModelMap modelMap,
                          RedirectAttributes redirectAttributes,
                          SessionStatus sessionStatus) {

        if (AccesChecker.isAdmin(httpSession)) {
            if (bindingResult.hasErrors()) {
                setUpModelMapForProductAddUpdate(modelMap, product, lang);
                setUpErrorField(bindingResult, modelMap);
                return PRODUCT_VIEW;
            }

            redirectAttributes.addFlashAttribute(COMMAND_MESSAGE, product.isNew() ?
                    messageSource.getMessage("message.productSuccess", null, null) :
                    messageSource.getMessage("message.productUpdate", null, null));

            productService.saveOrUpdate(product);
            return REDIRECT_PRODUCT;

        } else {
            return REDIRECT_LOGIN;
        }
    }

    @GetMapping(value = "/productList")
    public String showList(@RequestParam(defaultValue = "0") int categoryId,
                           @RequestParam(defaultValue = "") String productName,
                           ModelMap modelMap,
                           HttpSession httpSession) {

        if (Objects.nonNull(httpSession.getAttribute(StringConst.SESSION_KEY_USER_ROLE))) {
            httpSession.setAttribute(StringConst.SESSION_KEY_USER_ID, httpSession.getAttribute(StringConst.SESSION_KEY_USER_ID));
            httpSession.setAttribute(StringConst.SESSION_KEY_CATEGORY_LIST, categoryService.getAllCategory());

            setUpModelMapForProductList(modelMap, categoryId,
                    (Integer) httpSession.getAttribute(StringConst.SESSION_KEY_USER_ID), productName);

            return PRODUCT_LIST_VIEW;

        } else {
            return REDIRECT_LOGIN;
        }
    }

    @GetMapping(value = "/productDetails")
    public String showDetails(@RequestParam(defaultValue = "0") int productId,
                              ModelMap modelMap,
                              HttpSession httpSession) {

        productId = (productId == 0 ?
                (int) modelMap.getAttribute("redirectProductId") : productId);

        setUpModelMapForProductDetails(modelMap, productId,
                (Integer) httpSession.getAttribute(StringConst.SESSION_KEY_USER_ID));

        return PRODUCT_DETAILS_VIEW;
    }

    @PostMapping(value = "/productDiscontinue")
    public String processDiscontinue(@RequestParam int productId,
                                     @RequestParam(defaultValue = "false") boolean isContinue,
                                     HttpSession httpSession) {

        if (AccesChecker.isAdmin(httpSession)) {
            productService.discontinueProduct(productId, isContinue);
            return REDIRECT_PRODUCT_LIST;

        } else {
            return REDIRECT_LOGIN;
        }
    }
}
