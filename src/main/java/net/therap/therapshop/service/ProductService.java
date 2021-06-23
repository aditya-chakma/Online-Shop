package net.therap.therapshop.service;

import net.therap.therapshop.dao.ProductDao;
import net.therap.therapshop.dao.ProductImageDao;
import net.therap.therapshop.model.Product;
import net.therap.therapshop.model.ProductImage;
import net.therap.therapshop.util.ProductStatus;
import net.therap.therapshop.util.StringConst;
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

    public List<Product> findAll() {
        return productDao.findAll("product.findAll", Product.class);
    }

    public Product findById(int id) {
        return productDao.findById(id, Product.class);
    }

    public Product findByName(String name) {
        return productDao.findByName(name);
    }

    public List<Product> findAllByName(String name) {
        return productDao.findAllByName(name);
    }

    public List<Product> getProductByCategoryId(int id) {
        return productDao.findProductByCategoryId(id);
    }

    public byte[] getImageByteArray(int id) throws IOException {
        Set<ProductImage> imageSet = productDao.findById(id, Product.class).getProductImages();
        String path = imageSet.isEmpty() ? StringConst.DEFAULT_IMAGE : imageSet.iterator().next().getImageLink();
        return Files.readAllBytes(Paths.get(path));
    }

    public void discontinueProduct(int productId, boolean isContinue) {
        Product product = productDao.findById(productId, Product.class);

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

        Set<ProductImage> productImages = saveMultipartFiles(images, product);

        if (!productImages.isEmpty()) {
            product.setProductImages(productImages);
            productDao.saveOrUpdate(product);
        }
    }

    private Set<ProductImage> saveMultipartFiles(List<MultipartFile> images, Product product) {
        Set<ProductImage> productImages = new HashSet<>();

        for (MultipartFile multipartFile : images) {
            if (multipartFile.getContentType().startsWith("image/")) {
                String fileName = StringConst.DATA_ROOT + product.getName() + "-" +
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

        return productImages;
    }
}
