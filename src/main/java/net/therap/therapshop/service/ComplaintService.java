package net.therap.therapshop.service;

import net.therap.therapshop.dao.ComplaintDao;
import net.therap.therapshop.model.Complaint;
import net.therap.therapshop.model.ComplaintReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/8/21
 */
@Service
public class ComplaintService {

    @Autowired
    private ComplaintDao complaintDao;

    public List<Complaint> findAll() {
        return complaintDao.findAll();
    }

    public Complaint findById(int id) {
        return complaintDao.findById(id);
    }

    public List<Complaint> findByUserId(int userId) {
        return complaintDao.findByUserId(userId);
    }

    public Complaint saveOrUpdate(Complaint complaint) {
        if (complaint.isNew()) {
            complaint.setCreatedAt(new Date());

            ComplaintReply complaintReply = complaint.getReply();
            complaintReply.setComplaint(complaint);

            complaint.getComplaintReplies().add(complaintReply);
        }

        return complaintDao.saveOrUpdate(complaint);
    }
}
