package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.repository.CourseRepository;

@Repository("courseDao")
public class JpaCourseDao implements CourseDao{
	
	@Autowired 
	private CourseRepository courseRepo;

	@Override
	public void save(Course course) {
		courseRepo.save(course);
		
	}

	@Override
	public void add(Course course) {
		courseRepo.save(course);
		
	}

	@Override
	public void delete(Course course) {
		courseRepo.delete(course);
		
	}

	@Override
	public List<Course> findAll() {
		return courseRepo.findAll();
	}

	@Override
	public Page<Course> findAll(Pageable pageable) {
		return courseRepo.findAll(pageable);
	}

	@Override
	public List<Course> getCourseLikeTitle(String title) {
		return courseRepo.getCourseLikeTitle(title);
	}

	@Override
	public Course getCourseByTitle(String title) {
		return courseRepo.getCourseByTitle(title);
	}

}
