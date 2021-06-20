package net.therap.therapshop.dao;

import net.therap.therapshop.model.Complaint;
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
public class ComplaintDao implements GenericDao<Complaint> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Complaint> findAll() {
        return em.createQuery("FROM Complaint", Complaint.class)
                .getResultList();
    }

    @Override
    public Complaint findById(int id) {
        return em.find(Complaint.class, id);
    }

    public List<Complaint> findByUserId(int userId) {
        return em.createQuery("SELECT c FROM Complaint c WHERE c.user.id = :id", Complaint.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Transactional
    @Override
    public Complaint saveOrUpdate(Complaint complaint) {
        if (complaint.isNew()) {
            em.persist(complaint);
            em.flush();

        } else {
            complaint = em.merge(complaint);
        }

        return complaint;
    }
}
