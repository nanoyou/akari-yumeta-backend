package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.TaskDao;
import com.github.nanoyou.akariyumetabackend.entity.task.Task;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    private final TaskDao taskDao;

    @Autowired
    private TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
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

}
