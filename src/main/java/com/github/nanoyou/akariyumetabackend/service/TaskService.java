package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.common.NotImplementedException;
import com.github.nanoyou.akariyumetabackend.dao.TaskDao;
import com.github.nanoyou.akariyumetabackend.entity.task.Task;
import jakarta.annotation.Nonnull;
import lombok.val;
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
        // TODO: 这里调用Dao
        throw new NotImplementedException("flozxwer");
    }

    public Optional<Task> getTask(@Nonnull String taskID) {
        return taskDao.findById(taskID);
    }

}
