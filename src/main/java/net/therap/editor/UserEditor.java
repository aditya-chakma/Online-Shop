package net.therap.editor;

import net.therap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author al.imran
 * @since 10/06/2021
 */
@Service
public class UserEditor extends PropertyEditorSupport {

    @Autowired
    private UserService userService;

    @Override
    public String getAsText() {
        return String.valueOf(getValue());
    }

    @Override
    public void setAsText(String hashCode) throws IllegalArgumentException {
        if (Objects.nonNull(hashCode)) {
            String[] token = hashCode.split("@");
            int userId = Integer.parseInt(token[1]);
            setValue(userService.findById(userId));

        } else {
            setValue(null);
        }
    }
}
