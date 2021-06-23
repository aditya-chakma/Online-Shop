package net.therap.therapshop.dao;

import net.therap.therapshop.model.ComplaintReply;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/8/21
 */
@Repository
public class ComplaintReplyDao extends Dao {

    public List<ComplaintReply> findByComplaintId(int complaintId) {
        return em.createNamedQuery("complaintReply.findByComplaintId", ComplaintReply.class)
                .setParameter("complaintId", complaintId)
                .getResultList();
    }
}
