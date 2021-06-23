package net.therap.therapshop.dao;

import net.therap.therapshop.model.ProductImage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Repository
public class ProductImageDao extends Dao {

    public ProductImage findById(int id) {
        return super.finById(id, ProductImage.class);
    }

    @Transactional
    public void delete(int id) {
        ProductImage productImage = new ProductImage();
        productImage.setId(id);

        super.remove(productImage);
    }
}
