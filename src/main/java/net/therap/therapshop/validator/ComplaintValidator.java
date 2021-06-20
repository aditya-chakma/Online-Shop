package net.therap.therapshop.validator;

import net.therap.therapshop.model.Complaint;
import net.therap.therapshop.model.ComplaintReply;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author aditya.chakma
 * @since 6/14/21
 */
@Component
public class ComplaintValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Complaint.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "error.title");
        Complaint complaint = (Complaint) target;
        ComplaintReply reply = complaint.getReply();

        if (Objects.isNull(reply.getMessage()) || reply.getMessage().isEmpty()) {
            errors.rejectValue("reply", "error.details");
        }
    }
}
