package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Task;
import com.tecxis.resume.domain.repository.TaskRepository;

@Repository("taskDao")
public class JpaTaskDao implements TaskDao {
	
	@Autowired
	private TaskRepository taskRepo;

	@Override
	public void save(Task task) {
		taskRepo.save(task);

	}

	@Override
	public void add(Task task) {
		taskRepo.save(task);

	}

	@Override
	public void delete(Task task) {
		taskRepo.delete(task);

	}

	@Override
	public List<Task> findAll() {
		return taskRepo.findAll();
	}

	@Override
	public Page<Task> findAll(Pageable pageable) {
		return taskRepo.findAll(pageable);
	}

	@Override
	public List<Task> getTaskLikeDesc(String desc) {		
		return taskRepo.getTaskLikeDesc(desc);
	}

	@Override
	public Task getTaskByDesc(String desc) {
		return taskRepo.getTaskByDesc(desc);
	}

}
