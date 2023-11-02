package com.github.nanoyou.akariyumetabackend.dao;


import com.github.nanoyou.akariyumetabackend.entity.user.User;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public interface UserDao extends JpaRepository<User, UUID> {
//
//    int addUser(@Nonnull User user);
//
//    User login(@Nonnull String username, @Nonnull String password);

    User queryUser(@Nonnull String username, @Nonnull String password);

    Optional<User> findByUsernameAndPassword(@Nonnull String username, @Nonnull String password);

}
