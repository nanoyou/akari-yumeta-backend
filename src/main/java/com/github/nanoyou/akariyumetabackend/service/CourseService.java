package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.common.NotImplementedException;
import com.github.nanoyou.akariyumetabackend.dao.CourseDao;
import com.github.nanoyou.akariyumetabackend.entity.task.Course;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseDao courseDao;

    @Autowired
    private CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public Optional<Course> addCourse(@Nonnull Course course) {
        // TODO: 请 flozxwer 完成这里
        throw new NotImplementedException("flozxwer");
    }

    /**
     *
     * @apiNote Task和Course是一一对应的, 具有相同的ID
     * @param courseID
     * @return
     */
    public Optional<Course> getCourse(@Nonnull String courseID) {
        return courseDao.findById(courseID);
    }



}
