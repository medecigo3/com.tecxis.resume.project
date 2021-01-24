package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.id.StaffSkillId;

public interface StaffSkillRepository extends JpaRepository<StaffSkill, StaffSkillId> {

}
