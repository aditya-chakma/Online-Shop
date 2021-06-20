package net.therap.therapshop.controller;

import net.therap.therapshop.model.Order;
import net.therap.therapshop.model.User;
import net.therap.therapshop.service.OrderService;
import net.therap.therapshop.service.UserService;
import net.therap.therapshop.util.AccesChecker;
import net.therap.therapshop.util.OrderStatus;
import net.therap.therapshop.util.StringConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author aditya.chakma
 * @since 6/6/21
 */
@Controller
public class OrderController {

    private static final String VIEW_ORDER = "order";
    private static final String VIEW_ORDER_LIST = "orderList";

    private static final String ATTRIBUTE_OPLIST = "listOfOrderProduct";
    private static final String ATTRIBUTE_ORDER_LIST = "listOfOrder";
    private static final String ATTRIBUTE_ORDER = "order";
    private static final String ATTRIBUTE_STATUS = "listOfStatus";

    private static final String REDIRECT_OL = "redirect:/orderList";
    private static final String REDIRECT_HOME = "redirect:/";

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat(StringConst.DATE_TIME_FORMAT), false));
    }

    @GetMapping(value = "/order")
    public String show(@RequestParam(name = "id") int orderId,
                       HttpSession session,
                       ModelMap model) {

        Order order = orderService.findById(orderId);
        setUpAttributes(model, order);
        model.addAttribute(StringConst.IS_ADMIN, AccesChecker.isAdmin(session));

        return VIEW_ORDER;
    }

    @PostMapping("/order")
    public String process(@Valid @ModelAttribute Order order,
                          BindingResult result,
                          HttpSession session,
                          ModelMap model) {

        if (!AccesChecker.isAdmin(session)) {
            return REDIRECT_OL;
        }

        if (result.hasErrors()) {
            setUpAttributes(model, order);
            return VIEW_ORDER;
        }

        orderService.saveOrUpdate(order);
        return REDIRECT_OL;
    }

    @GetMapping(value = "/orderList")
    public String show(HttpSession session,
                       ModelMap model) {

        List<Order> orders;

        if (AccesChecker.isCustomer(session)) {
            int id = (int) session.getAttribute(StringConst.SESSION_KEY_USER_ID);
            orders = orderService.findByUserId(id);

        } else {
            orders = orderService.findAll();
        }

        model.addAttribute(ATTRIBUTE_ORDER_LIST, orders);
        return VIEW_ORDER_LIST;
    }

    @PostMapping(value = "/proceed")
    public String proceed(HttpSession session) {
        int id = (int) session.getAttribute(StringConst.SESSION_KEY_USER_ID);
        User user = userService.findById(id);
        orderService.saveOrUpdateFromCart(user);

        return REDIRECT_OL;
    }

    private void setUpAttributes(ModelMap model, Order order) {
        model.addAttribute(ATTRIBUTE_OPLIST, order.getOrderProducts());
        model.addAttribute(ATTRIBUTE_ORDER, order);
        model.addAttribute(ATTRIBUTE_STATUS, OrderStatus.values());
    }
}
