package com.tecxis.resume.persistence;

import java.util.List;
import com.tecxis.resume.domain.Assignment;

public interface AssignmentDao extends Dao<Assignment> {

	public List <Assignment> getAssignmentLikeDesc(String name);

	public Assignment getAssignmentByDesc(String name);

}
