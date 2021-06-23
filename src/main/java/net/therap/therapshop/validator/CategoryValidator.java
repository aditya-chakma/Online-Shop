package net.therap.therapshop.validator;

import net.therap.therapshop.model.Category;
import net.therap.therapshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author al.imran
 * @since 13/06/2021
 */
@Component
public class CategoryValidator implements Validator {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (Objects.nonNull(categoryService.findByName((((Category) target).getName())))) {
            errors.rejectValue("name", "category.name",
                    messageSource.getMessage("message.categoryExist", null, null));
        }
    }
}
