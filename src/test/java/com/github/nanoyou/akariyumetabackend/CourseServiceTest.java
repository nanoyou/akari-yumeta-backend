package com.github.nanoyou.akariyumetabackend;

import com.github.nanoyou.akariyumetabackend.entity.task.Course;
import com.github.nanoyou.akariyumetabackend.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Test
    void addCourseTest(){
        Random random = new Random();
        Course course = new Course();
            course.setTaskID("wdawsfdhfhjguiklhiukjl");
            course.setWatchedCount(random.nextInt());
            course.setVideoURL("dsadasdada");
            course.setVideoDuration(random.nextInt());

        assertTrue(courseService.addCourse(course).isPresent());
    }
}
