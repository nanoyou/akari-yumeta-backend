package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.task.Course;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDao extends JpaRepository<Course, String> {
    @Override
    Course saveAndFlush(@Nonnull Course course);
}
