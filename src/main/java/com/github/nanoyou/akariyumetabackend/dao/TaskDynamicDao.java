package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.task.TaskDynamic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskDynamicDao extends JpaRepository<TaskDynamic, TaskDynamic._TaskDynamicCombinedPrimaryKey> {

    @Override
    Optional<TaskDynamic> findById(TaskDynamic._TaskDynamicCombinedPrimaryKey taskDynamicCombinedPrimaryKey);


}
