package net.therap.therapshop.controller;

import net.therap.therapshop.model.Complaint;
import net.therap.therapshop.model.ComplaintReply;
import net.therap.therapshop.service.ComplaintReplyService;
import net.therap.therapshop.service.ComplaintService;
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

/**
 * @author aditya.chakma
 * @since 6/8/21
 */
@Controller
@SessionAttributes(value = {
        "complaint"
})
public class ComplaintReplyController {

    private static final String VIEW_CREPLY = "complaintReply";

    private static final String COMMAND_COMPLAINT = "complaint";
    private static final String COMMAND_CREPLY = "complaintReply";
    private static final String COMMAND_OPEN = "isOpen";
    private static final String COMMAND_REPLIES = "replies";

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private ComplaintReplyService complaintReplyService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/complaintReply")
    public String show(@RequestParam int complaintId,
                       HttpSession session,
                       ModelMap model) {

        Complaint complaint = complaintService.findById(complaintId);
        setUpAttribute(model, complaint, new ComplaintReply(), session);
        return VIEW_CREPLY;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/complaintReply")
    public String process(@Valid @ModelAttribute ComplaintReply complaintReply,
                          BindingResult result,
                          HttpSession session,
                          ModelMap model) {

        if (result.hasErrors()) {
            setUpAttribute(model, complaintReply.getComplaint(), complaintReply, session);
            return VIEW_CREPLY;
        }

        Complaint complaint = complaintService.findById(complaintReply.getComplaint().getId());
        complaintReplyService.saveOrUpdate(complaintReply);
        setUpAttribute(model, complaint, new ComplaintReply(), session);

        return VIEW_CREPLY;
    }

    private void setUpAttribute(ModelMap model,
                                Complaint complaint,
                                ComplaintReply complaintReply,
                                HttpSession session) {

        model.addAttribute(COMMAND_COMPLAINT, complaint);
        model.addAttribute(COMMAND_CREPLY, complaintReply);
        model.addAttribute(COMMAND_REPLIES, complaintReplyService.findByComplaintId(complaint.getId()));
        model.addAttribute(COMMAND_OPEN, complaint.getStatus() == ComplaintStatus.OPEN);
        model.addAttribute(StringConst.IS_ADMIN, AccesChecker.isAdmin(session));
    }
}
