package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.repository.CourseRepository;

public class JpaCourseDao implements CourseDao{
	
	@Autowired 
	private CourseRepository courseRepo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Course course) {
		em.merge(course);
		
	}

	@Override
	public void add(Course course) {
		em.persist(course);
		
	}

	@Override
	public void delete(Course course) {
		em.remove(course);
		
	}

	@Override
	public List<Course> findAll() {
		return courseRepo.findAll();
	}

	@Override
	public Page<Course> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> getCourseLikeTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Course getCourseByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

}
