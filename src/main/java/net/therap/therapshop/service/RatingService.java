package net.therap.therapshop.service;

import net.therap.therapshop.dao.ProductDao;
import net.therap.therapshop.dao.RatingDao;
import net.therap.therapshop.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author al.imran
 * @since 09/06/2021
 */
@Service
public class RatingService {

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserService userService;

    public Rating getRatingByUserIdAndProductId(int userId, int productId) {
        Rating rating = ratingDao.findByUserIdAndProdutId(userId, productId);

        if (Objects.isNull(rating)) {
            rating = new Rating();
            rating.setProduct(productDao.findById(productId));
            rating.setUser(userService.findById(userId));
        }

        return rating;
    }

    public double getRatingValue(int productId) {
        List<Object[]> list = ratingDao.findByProductId(productId);

        if (list.size() > 0) {
            Object[] ob = list.get(0);

            int count = ((Long) ob[0]).intValue();

            if (count == 0) {
                return 0.0;
            }

            return ((Long) ob[1]).doubleValue() / count;

        } else {
            return 0.0;
        }
    }

    public Rating saveOrUpdate(Rating rating) {
        return ratingDao.saveOrUpdate(rating);
    }
}
