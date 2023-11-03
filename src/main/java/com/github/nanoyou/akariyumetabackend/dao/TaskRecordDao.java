package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.task.TaskRecord;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRecordDao extends JpaRepository<TaskRecord, TaskRecord._TaskRecordCombinedPrimaryKey> {
    List<TaskRecord> findByTaskRecordChildID(@Nonnull String childID);
}
