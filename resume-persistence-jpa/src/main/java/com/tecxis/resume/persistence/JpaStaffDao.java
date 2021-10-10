package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.repository.StaffRepository;

@Repository("staffDao")
public class JpaStaffDao implements StaffDao {

	@Autowired
	private StaffRepository staffRepo;


	@Override
	public void save(Staff staff) {
		staffRepo.save(staff);

	}

	@Override
	public void add(Staff staff) {
		staffRepo.save(staff);

	}

	@Override
	public void delete(Staff staff) {
		staffRepo.delete(staff);

	}

	@Override
	public List<Staff> findAll() {
		return staffRepo.findAll();
	}

	@Override
	public Page<Staff> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public Staff getStaffLikeFirstName(String firstName) {
		return staffRepo.getStaffLikeFirstName(firstName);
	}

	@Override
	public Staff getStaffLikeLastName(String lastName) {
		return staffRepo.getStaffLikeLastName(lastName);
	}

	@Override
	public Staff getStaffByFirstNameAndLastName(String firstName, String lastName) {
		return staffRepo.getStaffByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public List<Project> getStaffProjects(Staff staff) {
		return staffRepo.getStaffProjects(staff);
	}

}
