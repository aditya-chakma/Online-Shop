package net.therap.service;

import net.therap.dao.ProductDao;
import net.therap.dao.ProductImageDao;
import net.therap.model.Product;
import net.therap.model.ProductImage;
import net.therap.util.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.therap.util.StringConst.*;

/**
 * @author al.imran
 * @since 03/06/2021
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImageDao productImageDao;

    public Product getProductById(int id) {
        return productDao.findById(id);
    }

    public Product getProductByName(String name) {
        return productDao.findByName(name);
    }

    public List<Product> getAllProductByName(String name) {
        return productDao.findAllByName(name);
    }

    public List<Product> getProductByCategoryId(int id) {
        return productDao.findProductByCategoryId(id);
    }

    public byte[] getImageByteArray(int id) throws IOException {
        Set<ProductImage> imageSet = productDao.findById(id).getProductImages();
        String path = imageSet.isEmpty() ? DEFAULT_IMAGE : imageSet.iterator().next().getImageLink();
        return Files.readAllBytes(Paths.get(path));
    }

    public void discontinueProduct(int productId, boolean isContinue) {
        Product product = productDao.findById(productId);

        if (isContinue) {
            product.setStatus(product.getQuantity() == 0 ?
                    ProductStatus.OUT_OF_STOCK :
                    ProductStatus.IN_STOCK);
        } else {
            product.setStatus(ProductStatus.DISCONTINUED);
        }

        productDao.saveOrUpdate(product);
    }

    public void saveOrUpdate(Product product) {
        if (!product.getStatus().equals(ProductStatus.DISCONTINUED)) {
            product.setStatus(product.getQuantity() == 0 ?
                    ProductStatus.OUT_OF_STOCK :
                    ProductStatus.IN_STOCK);
        }

        List<MultipartFile> images = product.getImages();
        product = productDao.saveOrUpdate(product);

        Set<ProductImage> productImages = new HashSet<>();

        for (MultipartFile multipartFile : images) {
            if (multipartFile.getContentType().startsWith("image/")) {
                String fileName = DATA_ROOT + product.getName() + "-" +
                        new Date() + "-" + multipartFile.getOriginalFilename();

                File imageFile = new File(fileName);

                try {
                    multipartFile.transferTo(imageFile);

                    ProductImage productImage = new ProductImage();
                    productImage.setImageLink(fileName);
                    productImage.setProduct(product);
                    productImageDao.saveOrUpdate(productImage);

                    productImages.add(productImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!productImages.isEmpty()) {
            product.setProductImages(productImages);
            productDao.saveOrUpdate(product);
        }
    }
}
