package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.repository.StaffSkillRepository;

public class JpaStaffSkillDao implements StaffSkillDao {
	
	@Autowired
	private StaffSkillRepository staffSkillRepo;
	
	@PersistenceContext
	private EntityManager em;
	

	@Override
	public void save(StaffSkill staffSkill) {
		em.merge(staffSkill);

	}

	@Override
	public void add(StaffSkill staffSkill) {
		em.persist(staffSkill);

	}

	@Override
	public void delete(StaffSkill staffSkill) {
		em.remove(staffSkill);

	}

	@Override
	public List<StaffSkill> findAll() {
		return staffSkillRepo.findAll();
	}

	@Override
	public Page<StaffSkill> findAll(Pageable pageable) {
		return findAll(pageable);
	}

}
