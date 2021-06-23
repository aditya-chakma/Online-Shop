package net.therap.therapshop.dao;

import net.therap.therapshop.model.Complaint;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/8/21
 */
@Repository
public class ComplaintDao extends Dao {

    public List<Complaint> findByUserId(int userId) {
        return em.createNamedQuery("complaint.findByUserId", Complaint.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
