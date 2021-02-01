package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;

public class JpaStaffDao implements StaffDao {

	@Override
	public void save(Staff k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Staff k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Staff k) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Staff> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Staff> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff getStaffLikeFirstName(String firstName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff getStaffLikeLastName(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff getStaffByFirstNameAndLastName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getStaffProjects(Staff staff) {
		// TODO Auto-generated method stub
		return null;
	}

}
