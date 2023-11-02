package com.github.nanoyou.akariyumetabackend.dao;


import com.github.nanoyou.akariyumetabackend.entity.user.User;
import jakarta.annotation.Nonnull;

public interface UserDao {

    int addUser(@Nonnull User user);

    User queryUser(@Nonnull String username, @Nonnull String password);

}
