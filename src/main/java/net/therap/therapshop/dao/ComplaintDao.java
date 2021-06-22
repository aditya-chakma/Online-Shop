package net.therap.therapshop.dao;

import net.therap.therapshop.model.Complaint;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/8/21
 */
@Repository
public class ComplaintDao extends Dao {

    public List<Complaint> findAll() {
        return super.findAll("complaint.findAll", Complaint.class);
    }

    public Complaint findById(int id) {
        return super.finById(id, Complaint.class);
    }

    public List<Complaint> findByUserId(int userId) {
        return em.createNamedQuery("complaint.findByUserId", Complaint.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Transactional
    public Complaint saveOrUpdate(Complaint complaint) {
        return super.saveOrUpdate(complaint);
    }
}
