package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.STAFF_FIRSTNAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.STAFF_LASTNAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.function.Function;

import com.tecxis.resume.domain.Staff;

@FunctionalInterface
public interface StaffValidator extends Function<Staff, ValidationResult> {

	static StaffValidator isStaffFirstNameValid(String firstName) {
		return 	staff -> staff.getFirstName().equals(firstName)  ? 
						SUCCESS : STAFF_FIRSTNAME_IS_NOT_VALID;
	}
	
	static StaffValidator isStaffLastNameValid(String lastName) {
		return 	staff -> staff.getLastName().equals(lastName) ? 
						SUCCESS : STAFF_LASTNAME_IS_NOT_VALID;
	}

}
