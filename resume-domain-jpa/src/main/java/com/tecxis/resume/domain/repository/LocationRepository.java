package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.id.LocationId;

public interface LocationRepository extends JpaRepository <Location, LocationId> {

}
