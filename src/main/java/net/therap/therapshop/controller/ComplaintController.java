package net.therap.therapshop.controller;

import net.therap.therapshop.model.Complaint;
import net.therap.therapshop.service.ComplaintService;
import net.therap.therapshop.service.UserService;
import net.therap.therapshop.util.AccesChecker;
import net.therap.therapshop.util.ComplaintStatus;
import net.therap.therapshop.util.StringConst;
import net.therap.therapshop.validator.ComplaintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/8/21
 */
@Controller
public class ComplaintController {

    private static final String VIEW_COMPLAINT_LIST = "complaintList";
    private static final String VIEW_COMPLAINT = "complaint";

    private static final String COMMAND_CLIST = "listOfComplaints";
    private static final String COMMAND_COMPLAINT = "complaint";

    private static final String REDIRECT_CLIST = "redirect:/complaintList";
    private static final String REDIRECT_HOME = "redirect:/";

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService;

    @Autowired
    private ComplaintValidator complaintValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(complaintValidator);
    }

    @GetMapping(value = "/complaintList")
    public String showList(HttpSession session,
                           ModelMap model) {

        List<Complaint> complaints;

        if (AccesChecker.isCustomer(session)) {
            complaints = complaintService.findByUserId((int) session.getAttribute(StringConst.SESSION_KEY_USER_ID));

        } else {
            complaints = complaintService.findAll();
        }

        model.addAttribute(COMMAND_CLIST, complaints);
        model.addAttribute(StringConst.IS_CUSTOMER, AccesChecker.isCustomer(session));
        return VIEW_COMPLAINT_LIST;
    }

    @GetMapping(value = "/complaint")
    public String show(HttpSession session,
                       ModelMap model) {

        if (!AccesChecker.isCustomer(session)) {
            return REDIRECT_HOME;
        }

        Complaint complaint = new Complaint();
        complaint.setUser(userService.findById((int) session.getAttribute(StringConst.SESSION_KEY_USER_ID)));
        model.addAttribute(COMMAND_COMPLAINT, complaint);

        return VIEW_COMPLAINT;
    }

    @PostMapping(value = "/complaint")
    public String process(@Valid @ModelAttribute Complaint complaint,
                          BindingResult result,
                          ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute(COMMAND_COMPLAINT, complaint);
            return VIEW_COMPLAINT;
        }

        complaintService.saveOrUpdate(complaint);
        return REDIRECT_CLIST;
    }

    @PostMapping(value = "/closeComplaint")
    public String close(@RequestParam int id,
                        HttpSession session,
                        ModelMap model) {

        if (!AccesChecker.isAdmin(session)) {
            return REDIRECT_HOME;
        }

        Complaint complaint = complaintService.findById(id);
        complaint.setStatus(ComplaintStatus.CLOSED);
        complaintService.saveOrUpdate(complaint);

        return REDIRECT_CLIST;
    }
}
