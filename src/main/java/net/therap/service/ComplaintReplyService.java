package net.therap.service;

import net.therap.comparator.ComplaintReplyComparator;
import net.therap.dao.ComplaintReplyDao;
import net.therap.model.ComplaintReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        complaintReplies.sort(new ComplaintReplyComparator());
        return complaintReplies;
    }

    public ComplaintReply saveOrUpdate(ComplaintReply complaintReply) {
        if (complaintReply.isNew()) {
            complaintReply.setCreatedAt(new Date());
        }

        return complaintReplyDao.saveOrUpdate(complaintReply);
    }
}
