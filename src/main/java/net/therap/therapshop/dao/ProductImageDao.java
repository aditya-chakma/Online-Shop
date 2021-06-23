package net.therap.therapshop.dao;

import net.therap.therapshop.model.ProductImage;
import org.springframework.stereotype.Repository;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Repository
public class ProductImageDao extends Dao {

    public ProductImage findById(int id) {
        return super.findById(id, ProductImage.class);
    }
}
