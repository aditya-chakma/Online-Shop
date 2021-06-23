package net.therap.therapshop.editor;

import net.therap.therapshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Service
public class CategoryEditor extends PropertyEditorSupport {

    @Autowired
    private CategoryService categoryService;

    @Override
    public String getAsText() {
        return String.valueOf(getValue());
    }

    @Override
    public void setAsText(String categoryId) throws IllegalArgumentException {
        if (Objects.nonNull(categoryId)) {
            try {
                setValue(categoryService.findById(Integer.parseInt(categoryId)));
            } catch (NumberFormatException e) {
                setValue(null);
            }
        } else {
            setValue(null);
        }
    }
}
