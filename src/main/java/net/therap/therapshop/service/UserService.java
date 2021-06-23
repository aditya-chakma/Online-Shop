package net.therap.therapshop.service;

import net.therap.therapshop.dao.UserDao;
import net.therap.therapshop.model.User;
import net.therap.therapshop.util.StringConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author aditya.chakma
 * @since 6/6/21
 */
@Service
public class UserService {

    @Autowired
    private PasswordEncoder pe;

    @Autowired
    private UserDao userDao;

    public User findById(int userId) {
        return userDao.findById(userId, User.class);
    }

    public List<User> finByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public User saveOrUpdate(User user) throws IOException {
        if (user.isNew()) {
            user.setCreatedAt(new Date());
            user.setHashedPassword(pe.encode(user.getHashedPassword()));

        } else {
            user.setUpdatedAt(new Date());
        }

        MultipartFile file = user.getImage();

        if (Objects.nonNull(file) && file.getContentType().startsWith("image")) {
            user.setImageLink(StringConst.DATA_ROOT + file.getOriginalFilename());
            FileCopyUtils.copy(file.getBytes(), new File(user.getImageLink()));
        }

        return userDao.saveOrUpdate(user);
    }

    public User saveOrUpdatePwOnly(User user) {
        user.setUpdatedAt(new Date());
        user.setHashedPassword(pe.encode(user.getHashedPassword()));
        return userDao.saveOrUpdate(user);
    }

    public byte[] getImageByteArray(int userId) throws IOException {
        User user = userDao.findById(userId, User.class);
        String imageLink = user.getImageLink();

        return Files.readAllBytes(Paths.get(imageLink));
    }
}
