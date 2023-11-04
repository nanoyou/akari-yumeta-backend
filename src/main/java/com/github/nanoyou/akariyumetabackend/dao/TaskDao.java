package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.task.Task;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskDao extends JpaRepository<Task, String> {
    @Override
    List<Task> findAll(Sort sort);

    @Override
    Optional<Task> findById(@Nonnull String id);

    @Override
    boolean existsById(String id);
}
