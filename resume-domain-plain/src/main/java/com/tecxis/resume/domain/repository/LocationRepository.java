package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.commons.persistence.id.LocationId;
import com.tecxis.resume.domain.Location;

public interface LocationRepository extends JpaRepository <Location, LocationId> {

}
