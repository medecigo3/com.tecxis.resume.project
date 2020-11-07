package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.commons.persistence.id.LocationId;
import com.tecxis.resume.Location;

public interface LocationRepository extends JpaRepository <Location, LocationId> {

}
