package com.github.nanoyou.akariyumetabackend.dao;


import com.github.nanoyou.akariyumetabackend.entity.user.User;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<User, UUID> {
//
//    int addUser(@Nonnull User user);
//
//    User login(@Nonnull String username, @Nonnull String password);

    User queryUser(@Nonnull String username, @Nonnull String password);

    User admin(@Nonnull User user);

    User volunteer(@Nonnull User user);

    User sponsor(@Nonnull User user);

    User child(@Nonnull User user);

}
