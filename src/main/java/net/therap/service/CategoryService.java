package net.therap.service;

import net.therap.dao.CategoryDao;
import net.therap.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> getAllCategory() {
        return categoryDao.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryDao.findById(id);
    }

    public Category getCategoryByName(String name) {
        return categoryDao.findByName(name);
    }

    public Category saveOrUpdate(Category category) {
        return categoryDao.saveOrUpdate(category);
    }
}
