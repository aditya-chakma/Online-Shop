package net.therap.therapshop.controller;

import net.therap.therapshop.service.CategoryService;
import net.therap.therapshop.service.ProductImageService;
import net.therap.therapshop.service.ProductService;
import net.therap.therapshop.util.AccesChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author al.imran
 * @since 10/06/2021
 */
@Controller
public class ProductImageController {

    private static final String COMMAND_NAME_PRODUCT = "product";
    private static final String COMMAND_NAME_CATEGORY_LIST = "listOfCategory";

    private static final String PRODUCT_VIEW = "product";

    private static final String REDIRECT_LOGIN = "redirect:/login";

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductImageService productImageService;

    @RequestMapping(method = RequestMethod.GET, value = "/image")
    public void getImage(@RequestParam(defaultValue = "0") int productId,
                         @RequestParam(defaultValue = "0") int imageId,
                         HttpServletResponse res) {

        try {
            res.getOutputStream().write(imageId == 0 ?
                    productService.getImageByteArray(productId) :
                    productImageService.getImageByteArray(imageId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/removeImage")
    public String process(@RequestParam int productId,
                          @RequestParam int imageId,
                          ModelMap modelMap,
                          HttpSession httpSession) {

        if (AccesChecker.isAdmin(httpSession)) {
            productImageService.remove(imageId);

            modelMap.addAttribute(COMMAND_NAME_CATEGORY_LIST, categoryService.findAll());
            modelMap.addAttribute(COMMAND_NAME_PRODUCT, productService.findById(productId));
            return PRODUCT_VIEW;

        } else {
            return REDIRECT_LOGIN;
        }
    }
}
