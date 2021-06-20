package net.therap.therapshop.dao;

import net.therap.therapshop.model.ComplaintReply;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/8/21
 */
@Repository
public class ComplaintReplyDao implements GenericDao<ComplaintReply> {

    @PersistenceContext
    private EntityManager em;

    public List<ComplaintReply> findByComplaintId(int complaintId) {
        return em.createQuery("SELECT c FROM ComplaintReply c WHERE c.complaint.id = :id", ComplaintReply.class)
                .setParameter("id", complaintId)
                .getResultList();
    }

    @Override
    @Transactional
    public ComplaintReply saveOrUpdate(ComplaintReply complaintReply) {
        if (complaintReply.isNew()) {
            em.persist(complaintReply);
            em.flush();

        } else {
            complaintReply = em.merge(complaintReply);
        }

        return complaintReply;
    }
}
