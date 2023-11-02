package com.github.nanoyou.akariyumetabackend.dao;


import com.github.nanoyou.akariyumetabackend.entity.user.User;
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

    @Override
    Optional<User> findById(@NotNull UUID uuid);

    @Override
    <S extends User> List<S> saveAllAndFlush(@NotNull Iterable<S> entities);

    @Override
    <S extends User> S save(@NotNull S entity);
}
