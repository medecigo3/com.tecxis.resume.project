package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Location;
import com.tecxis.resume.Location.LocationId;

public interface LocationRepository extends JpaRepository <Location, LocationId> {

}
