package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.NotImplementedException;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskCourseDTO;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskCourseSearchDTO;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskCourseUploadDTO;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.task.Task;
import com.github.nanoyou.akariyumetabackend.entity.task.Course;
import com.github.nanoyou.akariyumetabackend.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.enumeration.TaskStatus;
import com.github.nanoyou.akariyumetabackend.service.CourseService;
import com.github.nanoyou.akariyumetabackend.service.TaskService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.github.nanoyou.akariyumetabackend.enumeration.TaskStatus.*;

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
        try {
            // 验证时间,设置状态
            LocalDateTime time = LocalDateTime.now();
            TaskStatus status = NOT_STARTED;
            if (time.isAfter(taskCourseUploadDTO.getStartTime()))
                status = IN_PROGRESS;
            if (time.isAfter(taskCourseUploadDTO.getEndTime()))
                status = FINISHED;

            var uploadTask = Task.builder()
                    .taskName(taskCourseUploadDTO.getTaskName())
                    .taskUploaderID(taskCourseUploadDTO.getTaskUploaderID())
                    .createdTime(time)
                    .startTime(taskCourseUploadDTO.getStartTime())
                    .endTime(taskCourseUploadDTO.getEndTime())
                    .status(status)
                    .description(taskCourseUploadDTO.getDescription())
                    .category(taskCourseUploadDTO.getCategory())
                    .bonus(taskCourseUploadDTO.getBonus())
                    .build();

            // 新建，创建UUID
            var taskDTO = taskService.addTask(uploadTask).map(
                    task -> TaskDTO.builder()
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
                            .build()
            ).orElseThrow(NullPointerException::new);

            // course同步task UUID
            //TODO：COURSE观看次数&视频时长
            Integer watchedCount = 0;
            Integer videoDuration = 0;
            var uploadCourse = Course.builder()
                    .taskID(taskDTO.getId())
                    .watchedCount(watchedCount)
                    .videoURL(taskCourseUploadDTO.getVideoURL())
                    .videoDuration(videoDuration)
                    .build();

            // TODO: 怎么处理courseDTO？（返回响应里看起来不需要course的信息）
            var courseDTO = courseService.addCourse(uploadCourse);

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.TASK_UPLOAD_SUCCESS.value)
                    .message("学习任务创建成功")
                    .data(taskDTO)
                    .build();
        } catch (Exception e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.TASK_UPLOAD_FAIL.value)
                    .message("内部服务器错误")
                    .build();
        }

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


    @RequestMapping(path = "/my/task", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result search(@RequestBody TaskCourseSearchDTO taskCourseSearchDTO) {
        // TODO: use session
        throw new NotImplementedException("akagawa");
    }

}
