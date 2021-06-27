package net.therap.therapshop.controller;

import net.therap.therapshop.exception.NoAccessException;
import net.therap.therapshop.model.Complaint;
import net.therap.therapshop.service.ComplaintService;
import net.therap.therapshop.service.UserService;
import net.therap.therapshop.util.AccesChecker;
import net.therap.therapshop.util.ComplaintStatus;
import net.therap.therapshop.util.StringConst;
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
@SessionAttributes("complaint")
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/complaintList")
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

    @RequestMapping(method = RequestMethod.GET, value = "/complaint")
    public String show(@ModelAttribute Complaint complaint,
                       HttpSession session,
                       ModelMap model) throws NoAccessException {

        if (!AccesChecker.isCustomer(session)) {
            throw new NoAccessException();
        }

        model.addAttribute(COMMAND_COMPLAINT, complaint);

        return VIEW_COMPLAINT;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/complaint")
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

    @RequestMapping(method = RequestMethod.POST, value = "/closeComplaint")
    public String close(@ModelAttribute Complaint complaint,
                        HttpSession session,
                        ModelMap model) {

        if (!AccesChecker.isAdmin(session)) {
            return REDIRECT_HOME;
        }


        complaint.setStatus(ComplaintStatus.CLOSED);
        complaintService.saveOrUpdate(complaint);

        return REDIRECT_CLIST;
    }

    @ModelAttribute(COMMAND_COMPLAINT)
    private Complaint complaint(HttpSession session) {
        Complaint complaint = new Complaint();
        int userId = (int) session.getAttribute(StringConst.SESSION_KEY_USER_ID);

        complaint.setUser(userService.findById(userId));
        return complaint;
    }
}
