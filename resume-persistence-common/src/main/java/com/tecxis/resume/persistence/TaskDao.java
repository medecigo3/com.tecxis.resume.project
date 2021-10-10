package com.tecxis.resume.persistence;

import java.util.List;

import com.tecxis.resume.domain.Task;

public interface TaskDao extends Dao<Task> {

	public List <Task> getTaskLikeDesc(String name);

	public Task getTaskByDesc(String name);

}
