package net.therap.therapshop.dao;

import net.therap.therapshop.model.AbstractModel;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/22/21
 */
public class Dao {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public <T extends AbstractModel> T saveOrUpdate(T t) {
        if (t.isNew()) {
            em.persist(t);
            em.flush();
        } else {
            t = em.merge(t);
        }

        return t;
    }

    public <T extends AbstractModel> List<T> findAll(String queryName, Class<T> c) {
        return em.createNamedQuery(queryName, c)
                .getResultList();
    }

    public <T extends AbstractModel> T finById(int primaryKey, Class<T> c) {
        return em.find(c, primaryKey);
    }

    @Transactional
    public <T extends AbstractModel> void remove(T t) {
        em.remove(em.getReference(t.getClass(), t.getId()));
    }
}
