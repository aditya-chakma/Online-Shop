package net.therap.service;

import net.therap.dao.ProductImageDao;
import net.therap.model.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Service
public class ProductImageService {

    @Autowired
    private ProductImageDao productImageDao;

    public byte[] getImageByteArray(int id) throws IOException {
        ProductImage productImage = productImageDao.findById(id);
        return Files.readAllBytes(Paths.get(productImage.getImageLink()));
    }

    public ProductImage saveOrUpdate(ProductImage productImage) {
        return productImageDao.saveOrUpdate(productImage);
    }

    public void remove(int id) {
        productImageDao.delete(id);
    }
}
