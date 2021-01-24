package com.tecxis.resume.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	
	@Query("select c from Course c where c.title LIKE %?1 ")
	public List <Course> getCourseLikeTitle(String title);
	
	public Course getCourseByTitle(String title);

}
