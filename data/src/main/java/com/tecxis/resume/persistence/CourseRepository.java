package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ver2.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
