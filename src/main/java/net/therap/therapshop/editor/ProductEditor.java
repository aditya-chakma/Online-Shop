package net.therap.therapshop.editor;

import net.therap.therapshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author al.imran
 * @since 10/06/2021
 */
@Service
public class ProductEditor extends PropertyEditorSupport {

    @Autowired
    private ProductService productService;

    @Override
    public String getAsText() {
        return String.valueOf(getValue());
    }

    @Override
    public void setAsText(String hashCode) throws IllegalArgumentException {
        if (Objects.nonNull(hashCode)) {
            String[] token = hashCode.split("@");
            int productId = Integer.parseInt(token[1]);
            setValue(productService.findById(productId));

        } else {
            setValue(null);
        }
    }
}
