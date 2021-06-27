package net.therap.therapshop.service;

import net.therap.therapshop.dao.ComplaintReplyDao;
import net.therap.therapshop.model.ComplaintReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/8/21
 */
@Service
public class ComplaintReplyService {

    @Autowired
    private ComplaintReplyDao complaintReplyDao;

    public List<ComplaintReply> findByComplaintId(int id) {
        List<ComplaintReply> complaintReplies = complaintReplyDao.findByComplaintId(id);
        Collections.sort(complaintReplies);
        return complaintReplies;
    }

    public ComplaintReply saveOrUpdate(ComplaintReply complaintReply) {
        return complaintReplyDao.saveOrUpdate(complaintReply);
    }
}
