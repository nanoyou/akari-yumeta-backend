package com.github.nanoyou.akariyumetabackend.dao;


import com.github.nanoyou.akariyumetabackend.entity.user.User;
import jakarta.annotation.Nonnull;

public interface UserDao {

    int addUser(@Nonnull User user);

    User queryUser(@Nonnull String username, @Nonnull String password);

    User admin(@Nonnull User user);

    User volunteer(@Nonnull User user);

    User sponsor(@Nonnull User user);

    User child(@Nonnull User user);

}
