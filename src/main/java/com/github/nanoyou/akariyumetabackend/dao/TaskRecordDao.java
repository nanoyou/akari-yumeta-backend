package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskRecordStatus;
import com.github.nanoyou.akariyumetabackend.entity.task.TaskRecord;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRecordDao extends JpaRepository<TaskRecord, TaskRecord._TaskRecordCombinedPrimaryKey> {
    List<TaskRecord> findByTaskRecordCombinedPrimaryKeyChildID(@Nonnull String childID);

    Optional<TaskRecord> findByTaskRecordCombinedPrimaryKey(@Nonnull TaskRecord._TaskRecordCombinedPrimaryKey taskRecordCombinedPrimaryKey);

    List<TaskRecord> findByTaskRecordCombinedPrimaryKeyChildIDAndStatus(@Nonnull String childID, @Nonnull TaskRecordStatus status);
}
