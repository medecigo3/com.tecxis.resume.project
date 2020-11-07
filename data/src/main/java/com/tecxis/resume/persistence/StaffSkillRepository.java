package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.commons.persistence.id.StaffSkillId;
import com.tecxis.resume.StaffSkill;

public interface StaffSkillRepository extends JpaRepository<StaffSkill, StaffSkillId> {

}
