package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.common.enumeration.TaskRecordStatus;
import com.github.nanoyou.akariyumetabackend.dao.TaskDao;
import com.github.nanoyou.akariyumetabackend.dao.TaskDynamicDao;
import com.github.nanoyou.akariyumetabackend.dao.TaskRecordDao;
import com.github.nanoyou.akariyumetabackend.entity.task.Task;
import com.github.nanoyou.akariyumetabackend.entity.task.TaskRecord;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskDao taskDao;
    private final TaskDynamicDao taskDynamicDao;
    private final TaskRecordDao taskRecordDao;

    @Autowired
    private TaskService(TaskDao taskDao, TaskDynamicDao taskDynamicDao, TaskRecordDao taskRecordDao) {
        this.taskDynamicDao = taskDynamicDao;
        this.taskDao = taskDao;
        this.taskRecordDao = taskRecordDao;
    }

    public Optional<Task> addTask(@Nonnull Task task) {
        taskDao.save(task);

        return Optional.of(Task.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .taskUploaderID(task.getTaskUploaderID())
                .createdTime(task.getCreatedTime())
                .startTime(task.getStartTime())
                .endTime(task.getEndTime())
                .status(task.getStatus())
                .description(task.getDescription())
                .category(task.getCategory())
                .bonus(task.getBonus())
                .build());
    }

    public Optional<Task> getTask(@Nonnull String taskID) {
        return taskDao.findById(taskID);
    }

    public List<Task> getAllTasks() {
        return taskDao.findAll();
    }

    public List<Task> getMyTasks(@Nonnull String childID) {
        // 根据 childID 获取任务-儿童关系对
        val taskRecords = taskRecordDao.findByTaskRecordCombinedPrimaryKeyChildID(childID);
        // 遍历关系对, 映射为任务列表
        return taskRecords.stream().map(
                taskRecord -> taskDao.findById(taskRecord.getTaskRecordCombinedPrimaryKey().getTaskID()).orElseThrow(NullPointerException::new)
        ).toList();
    }

    public Optional<TaskRecord> getRecord(@Nonnull TaskRecord._TaskRecordCombinedPrimaryKey taskRecordCombinedPrimaryKey) {
        return taskRecordDao.findByTaskRecordCombinedPrimaryKey(taskRecordCombinedPrimaryKey);
    }

    public Optional<TaskRecord> saveRecord(@Nonnull TaskRecord taskRecord) {
        taskRecordDao.save(taskRecord);

        return Optional.of(TaskRecord.builder()
                .taskRecordCombinedPrimaryKey(taskRecord.getTaskRecordCombinedPrimaryKey())
                .endTime(taskRecord.getEndTime())
                .startTime(taskRecord.getStartTime())
                .status(taskRecord.getStatus())
                .build());
    }

    public List<TaskRecord> getRecords(@Nonnull String childID, @Nonnull TaskRecordStatus status) {
        return taskRecordDao.findByTaskRecordCombinedPrimaryKeyChildIDAndStatus(childID, status);
    }

    public Integer getBonuses(@Nonnull List<String> taskIDs) {
        val tasks = taskIDs.stream().map(
                id -> taskDao.findById(id).orElseThrow(NullPointerException::new)
        ).toList();

        return tasks.stream()
                .map(Task::getBonus)
                .reduce(0, Integer::sum);
    }

}
