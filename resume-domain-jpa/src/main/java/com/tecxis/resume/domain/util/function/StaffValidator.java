package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.STAFF_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.function.Function;

import com.tecxis.resume.domain.Staff;

@FunctionalInterface
public interface StaffValidator extends Function<Staff, ValidationResult> {

	static StaffValidator isStaffValid(String firstName, String lastName) {
		return 	staff -> staff.getFirstName().equals(firstName) && 
				staff.getLastName().equals(lastName) ? 
						SUCCESS : STAFF_IS_NOT_VALID;
	}

}
