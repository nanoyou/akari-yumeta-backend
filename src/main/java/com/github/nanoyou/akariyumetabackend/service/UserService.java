package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    private UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> getUser(@Nonnull String userID) {
        return userDao.findById(userID);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
