package net.therap.controller;

import net.therap.model.Complaint;
import net.therap.model.ComplaintReply;
import net.therap.service.ComplaintReplyService;
import net.therap.service.ComplaintService;
import net.therap.util.AccesChecker;
import net.therap.util.ComplaintStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static net.therap.util.StringConst.IS_ADMIN;

/**
 * @author aditya.chakma
 * @since 6/8/21
 */
@Controller
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

    @GetMapping(value = "/complaintReply")
    public String show(@RequestParam int complaintId,
                       HttpSession session,
                       ModelMap model) {

        Complaint complaint = complaintService.findById(complaintId);
        setUpAttribute(model, complaint, new ComplaintReply(), session);
        return VIEW_CREPLY;
    }

    @PostMapping(value = "/complaintReply")
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
        model.addAttribute(IS_ADMIN, AccesChecker.isAdmin(session));
    }
}
