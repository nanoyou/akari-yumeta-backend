package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.dto.user.UserUpdateDTO;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {
    Optional<User> findByUsernameAndPassword(@Nonnull String username, @Nonnull String password);

}
