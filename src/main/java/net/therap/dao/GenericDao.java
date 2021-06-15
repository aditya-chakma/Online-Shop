package net.therap.dao;

import java.util.List;

/**
 * @author al.imran
 * @since 03/06/2021
 */
public interface GenericDao<T> {

    default List<T> findAll() {
        return null;
    }

    default T findById(int id) {
        return null;
    }

    default T findByName(String name) {
        return null;
    }

    default T saveOrUpdate(T t) {
        return t;
    }

    default void delete(int id) {
    }
}
