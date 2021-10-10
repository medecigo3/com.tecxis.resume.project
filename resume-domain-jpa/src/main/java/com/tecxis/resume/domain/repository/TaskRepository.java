package com.tecxis.resume.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("select a from Task a where a.desc LIKE %?1")
	public List <Task> getTaskLikeDesc(String name);
	
	public Task getTaskByDesc(String name);
}
