package net.therap.service;

import net.therap.dao.ProductDao;
import net.therap.dao.RatingDao;
import net.therap.dao.UserDao;
import net.therap.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private UserDao userDao;

    public Rating getRatingByUserIdAndProductId(int userId, int productId) {
        Rating rating = ratingDao.findByUserIdAndProdutId(userId, productId);

        if (Objects.isNull(rating)) {
            rating = new Rating();
            rating.setProduct(productDao.findById(productId));
            rating.setUser(userDao.findById(userId));
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
