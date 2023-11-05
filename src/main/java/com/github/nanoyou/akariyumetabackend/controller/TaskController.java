package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.constant.SessionConst;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.exception.NoSuchCourseException;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskRecordStatus;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskStatus;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskCourseDTO;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskCourseUploadDTO;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskDTO;
import com.github.nanoyou.akariyumetabackend.dto.task.TaskRecordDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.task.Course;
import com.github.nanoyou.akariyumetabackend.entity.task.Task;
import com.github.nanoyou.akariyumetabackend.entity.task.TaskRecord;
import com.github.nanoyou.akariyumetabackend.service.CourseService;
import com.github.nanoyou.akariyumetabackend.service.TaskService;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskStatus.*;
import static java.time.LocalDateTime.now;

@RestController
public class TaskController {
    private final TaskService taskService;
    private final CourseService courseService;

    @Autowired
    private TaskController(TaskService taskService, CourseService courseService) {
        this.taskService = taskService;
        this.courseService = courseService;
    }

    /**
     * 创建学习任务
     *
     * @param taskCourseUploadDTO
     * @return Result类型的对象
     */
    @RequestMapping(path = "/task", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result task(@RequestBody TaskCourseUploadDTO taskCourseUploadDTO) {
        try {
            // 验证时间,设置状态
            LocalDateTime time = now();

            assert taskCourseUploadDTO.getStartTime() != null;
            assert taskCourseUploadDTO.getEndTime() != null;

            if (taskCourseUploadDTO.getStartTime().isAfter(taskCourseUploadDTO.getEndTime())) {
                return Result.builder()
                        .ok(false)
                        .code(ResponseCode.PARAM_ERR.value)
                        .message("课程的结束时间不能在开始时间之前")
                        .data(null)
                        .build();
            }

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
            Integer videoDuration = 1;
            var uploadCourse = Course.builder()
                    .taskID(taskDTO.getId())
                    .watchedCount(watchedCount)
                    .videoURL(taskCourseUploadDTO.getVideoURL())
                    .videoDuration(videoDuration)
                    .build();
            courseService.addCourse(uploadCourse);


            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
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

    /**
     * 根据任务ID获取该任务所属课程的相关信息以及任务的详细信息。
     *
     * @param taskID 任务ID
     * @return 包含课程信息和任务信息的DTO对象
     */
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
                            .status(task.getStatus())
                            .description(task.getDescription())
                            .category(task.getCategory())
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

    /**
     * 获取当前用户的课程任务列表
     *
     * @param loginUserID HTTP会话对象，用于获取登录用户信息
     * @return 返回Result对象，包含查询结果信息
     */
    @RequestMapping(path = "/my/task", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result myTask(@ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {
        try {

            val tasks = taskService.getMyTasks(loginUserID);

            val courses = tasks.stream().map(
                    task -> courseService.getCourse(task.getId()).orElseThrow(NoSuchCourseException::new)
            ).toList();

            val taskCourseDTOs = this.concat(tasks, courses);

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("查询我的课程任务成功")
                    .data(taskCourseDTOs)
                    .build();
        } catch (NullPointerException e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.MY_TASK_FAILED.value)
                    .message("查询我的课程任务失败")
                    .data(null)
                    .build();
        } catch (NoSuchCourseException e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.MY_TASK_FAILED.value)
                    .message("这个任务没有绑定课程")
                    .data(null)
                    .build();
        }
    }

    /**
     * 查询所有学习任务列表
     *
     * @return 包含所有课程学习任务的Result对象
     */
    @RequestMapping(path = "/task", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result task() {

        val allTasks = taskService.getAllTasks();
        val allCourses = courseService.getAllCourses();
        val taskCourseDTOs = this.concat(allTasks, allCourses);

        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .message("总共有 " + taskCourseDTOs.size() + " 个课程任务")
                .data(taskCourseDTOs)
                .build();
    }

    private List<TaskCourseDTO> concat(@Nonnull List<Task> tasks, @Nonnull List<Course> courses) {
        return IntStream.range(0, tasks.size())
                .mapToObj(i -> {
                    val task = tasks.get(i);
                    val course = courses.get(i);
                    return TaskCourseDTO.builder()
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
                            .watchedCount(course.getWatchedCount())
                            .videoURL(course.getVideoURL())
                            .videoDuration(course.getVideoDuration())
                            .build();
                }).toList();
    }

    /**
     * 开启学习任务
     *
     * @param taskID      任务 ID
     * @param loginUserID 自动注入的登录用户 ID
     * @return Result
     */
    @RequestMapping(path = "/task/{taskID}/open", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result record(@PathVariable String taskID, @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {
        try {

            var comID = TaskRecord._TaskRecordCombinedPrimaryKey.builder()
                    .taskID(taskID)
                    .childID(loginUserID)
                    .build();

            if(taskService.validateMyTask(comID)){
                return Result.builder()
                        .ok(true)
                        .code(ResponseCode.TASK_OPEN_AGAIN.value)
                        .message("学习任务重复开启")
                        .build();
            }

            var taskRecord = TaskRecord.builder()
                    .taskRecordCombinedPrimaryKey(comID)
                    .endTime(null)
                    .startTime(now())
                    .status(TaskRecordStatus.UNCOMPLETED)
                    .build();

            val taskRecordDTO = saveRecord(taskRecord);

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("视频观看任务创建成功")
                    .data(taskRecordDTO)
                    .build();

        } catch (NullPointerException e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.TASK_RECORD_FAIL.value)
                    .message("内部服务器错误")
                    .build();
        }
    }

    private TaskRecordDTO saveRecord(TaskRecord taskRecord) {
        return taskService.addRecord(taskRecord).map(
                record -> TaskRecordDTO.builder()
                        .taskID(record.getTaskRecordCombinedPrimaryKey().getTaskID())
                        .childID(record.getTaskRecordCombinedPrimaryKey().getChildID())
                        .endTime(record.getEndTime())
                        .startTime(record.getStartTime())
                        .status(record.getStatus())
                        .build()
        ).orElseThrow(NullPointerException::new);
    }


    /**
     * 完成学习任务（视频观看修改状态）
     *
     * @param taskID      任务 ID
     * @param loginUserID 自动注入的登录用户 ID
     * @return Result
     */
    @RequestMapping(path = "/task/{taskID}/finish", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result finish(@PathVariable String taskID, @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {
        try {
            val course = courseService.getCourse(taskID).orElseThrow(NullPointerException::new);

            var comID = TaskRecord._TaskRecordCombinedPrimaryKey.builder()
                    .taskID(taskID)
                    .childID(loginUserID)
                    .build();
            val record = taskService.getRecord(comID).map(
                    taskRecord -> TaskRecord.builder()
                            .taskRecordCombinedPrimaryKey(taskRecord.getTaskRecordCombinedPrimaryKey())
                            .endTime(taskRecord.getEndTime())
                            .startTime(taskRecord.getStartTime())
                            .status(taskRecord.getStatus())
                            .build()
            ).orElseThrow(NullPointerException::new);

            // 判断是否完成
            LocalDateTime time = now();
            long betweenSeconds = ChronoUnit.SECONDS.between(record.getStartTime(), time);
            if (betweenSeconds >= course.getVideoDuration()) {
                record.setEndTime(time);
                record.setStatus(TaskRecordStatus.COMPLETED);
                val taskRecordDTO = saveRecord(record);

                // 视频观看次数加一
                int count = course.getWatchedCount();
                course.setWatchedCount(++count);
                courseService.addCourse(course);

                return Result.builder()
                        .ok(true)
                        .code(ResponseCode.SUCCESS.value)
                        .message("视频观看完成")
                        .data(taskRecordDTO)
                        .build();
            } else {
                return Result.builder()
                        .ok(false)
                        .code(ResponseCode.VIDEO_UNCOMPLETED.value)
                        .message("视频观看未完成")
                        .build();
            }
        } catch (NullPointerException e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.VIDEO_DISAPPEARED.value)
                    .message("视频不见喽~")
                    .build();
        }
    }


    /**
     * 获取学习积分
     *
     * @param userID
     * @return score
     */
    @RequestMapping(path = "/user/{userID}/score", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result score(@PathVariable String userID) {
        try {
            val records = taskService.getRecords(userID, TaskRecordStatus.COMPLETED);
            val taskIDs = records.stream()
                    .map(taskRecord -> taskRecord.getTaskRecordCombinedPrimaryKey().getTaskID())
                    .collect(Collectors.toList());

            val score = taskService.getBonuses(taskIDs);

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("学习积分获取成功")
                    .data(score)
                    .build();
        } catch (Exception e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.SCORE_GET_FAIL.value)
                    .message("内部服务器错误")
                    .build();
        }

    }

}
