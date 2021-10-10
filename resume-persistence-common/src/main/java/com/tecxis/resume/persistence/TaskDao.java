package com.tecxis.resume.persistence;

import java.util.List;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.Task;

public interface TaskDao extends Dao<Task> {

	public List <Assignment> getTaskLikeDesc(String name);

	public Assignment getTaskByDesc(String name);

}
