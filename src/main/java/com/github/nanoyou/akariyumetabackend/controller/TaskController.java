package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.NotImplementedException;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskCourseDTO;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskCourseUploadDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.service.CourseService;
import com.github.nanoyou.akariyumetabackend.service.TaskService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    private final TaskService taskService;
    private final CourseService courseService;

    @Autowired
    private TaskController(TaskService taskService, CourseService courseService) {
        this.taskService = taskService;
        this.courseService = courseService;
    }

    @RequestMapping(path = "/task", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result task(@RequestBody TaskCourseUploadDTO taskCourseUploadDTO) {
        // TODO: 请 flozxwer 完成这里! 你可能需要调用两个Service
        throw new NotImplementedException("flozxwer");
    }

    @RequestMapping(path = "/task/{taskID}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result task(@PathVariable String taskID) {

        try {
            val course = courseService.getCourse(taskID).orElseThrow(NullPointerException::new);
            val taskCourseDTO = taskService.getTask(taskID).map(
                    task -> TaskCourseDTO.builder()
                            .id(task.getId())
                            .taskName(task.getTaskName())
                            .taskUploaderID(task.getTaskUploaderID())
                            .createdTime(task.getCreatedTime())
                            .startTime(task.getStartTime())
                            .endTime(task.getEndTime())
                            .status(task.getStatus().value)
                            .description(task.getDescription())
                            .category(task.getCategory().value)
                            .bonus(task.getBonus())
                            .watchedCount(course.getWatchedCount())
                            .videoURL(course.getVideoURL())
                            .videoDuration(course.getVideoDuration())
                            .build()
            ).orElseThrow(NullPointerException::new);

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("成功查询到 1 条课程任务")
                    .data(taskCourseDTO)
                    .build();
        } catch (NullPointerException e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.NO_SUCH_TASK_COURSE.value)
                    .message("课程任务不存在")
                    .data(null)
                    .build();
        }
    }

}
