package com.tecxis.resume.persistence;

import java.util.List;

import com.tecxis.resume.domain.Course;

public interface CourseDao extends Dao<Course> {
		
	public List <Course> getCourseLikeTitle(String title);
	
	public Course getCourseByTitle(String title);

}
