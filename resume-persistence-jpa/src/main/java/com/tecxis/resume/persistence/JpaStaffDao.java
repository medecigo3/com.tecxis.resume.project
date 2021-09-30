package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Staff staff) {
		em.merge(staff);

	}

	@Override
	public void add(Staff staff) {
		em.persist(staff);

	}

	@Override
	public void delete(Staff staff) {
		em.remove(staff);

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
