package com.tecxis.resume.persistence;

import java.util.List;
import com.tecxis.resume.domain.Assignment;

public interface TaskDao extends Dao<Assignment> {

	public List <Assignment> getTaskLikeDesc(String name);

	public Assignment getTaskByDesc(String name);

}
