package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.repository.StaffSkillRepository;

@Repository("staffSkillDao")
public class JpaStaffSkillDao implements StaffSkillDao {
	
	@Autowired
	private StaffSkillRepository staffSkillRepo;	

	@Override
	public void save(StaffSkill staffSkill) {
		staffSkillRepo.save(staffSkill);

	}

	@Override
	public void add(StaffSkill staffSkill) {
		staffSkillRepo.save(staffSkill);

	}

	@Override
	public void delete(StaffSkill staffSkill) {
		staffSkillRepo.delete(staffSkill);

	}

	@Override
	public List<StaffSkill> findAll() {
		return staffSkillRepo.findAll();
	}

	@Override
	public Page<StaffSkill> findAll(Pageable pageable) {
		return staffSkillRepo.findAll(pageable);
	}

}
