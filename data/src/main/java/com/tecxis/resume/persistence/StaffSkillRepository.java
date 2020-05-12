package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.StaffSkill;
import com.tecxis.resume.StaffSkill.StaffSkillId;

public interface StaffSkillRepository extends JpaRepository<StaffSkill, StaffSkillId> {

}
