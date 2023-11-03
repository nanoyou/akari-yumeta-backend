package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.NotImplementedException;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskCourseUploadDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    private final TaskService taskService;

    @Autowired
    private TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result task(@RequestBody TaskCourseUploadDTO taskCourseUploadDTO) {
        // TODO: 请 flozxwer 完成这里! 你可能需要调用两个Service
        throw new NotImplementedException("flozxwer");
    }

}
