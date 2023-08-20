package com.tecxis.resume.domain.util.function;

public enum ValidationResult {	
	SUCCESS(""),
	PROJECT_NAME_IS_NOT_VALID(""),
	PROJECT_VERSION_IS_NOT_VALID(""),
	PROJECT_CLIENT_IS_NOT_VALID (""),
	PROJECT_LOCATIONS_ARE_NOT_VALID(""),
	PROJECT_LOCATIONS_ARE_NULL("Comparing 'actual' or 'expected' Project with null Locations association."),
	PROJECT_SIZE_LOCATIONS_ARE_DIFFERENT(""),
	PROJECT_ASSIGNMENTS_ARE_NULL("Comparing 'actual' or 'expected' Project with null Assignments association."),
	PROJECT_SIZE_ASSIGNMENTS_ARE_DIFFERENT(""),
	PROJECT_ASSIGNMENTS_ARE_NOT_VALID(""),
	STAFF_FIRSTNAME_IS_NOT_VALID(""),
	STAFF_LASTNAME_IS_NOT_VALID(""),
	TASK_DESC_IS_NOT_VALID(""),
	CITY_NAME_IS_NOT_VALID(""),//RES-65
	CITY_COUNTRY_IS_NOT_VALID(""),//RES-65
	CITY_LOCATIONS_ARE_NOT_VALID(""),
	CITY_SIZE_LOCATIONS_ARE_DIFFERENT(""),
	CITY_LOCATIONS_ARE_NULL("Comparing 'actual' or 'expected' City with null Locations association."),
	SERVICE_NAME_IS_NOT_VALID(""),
	COUNTRY_CITIES_ARE_NOT_VALID(""),
	COUNTRY_SIZE_CITIES_ARE_DIFFERENT(""),
	COUNTRY_CITIES_ARE_NULL("Comparing 'actual' or 'expected' Country with null Cities association."),
	COUNTRY_NAME_IS_NOT_VALID(""),
	CLIENT_NAME_IS_NOT_VALID(""),
	CLIENT_CONTRACTS_ARE_NOT_VALID(""),
	CLIENT_CONTRACTS_ARE_NULL("Comparing 'actual' or 'expected' Client with null Contracts association."),
	CLIENT_SIZE_CONTRACTS_ARE_DIFFERENT(""),
	CONTRACT_NAME_IS_NOT_VALID(""),
	CONTRACT_ID_IS_NOT_VALID(""),
	CONTRACT_CLIENT_IS_NOT_VALID(""),
	CONTRACT_AGREEMENTS_ARE_NOT_VALID(""),
	CONTRACT_AGREEMENTS_ARE_NULL("Comparing 'actual' or 'expected' Contract with null Agreements association."),
	CONTRACT_SIZE_AGREEMENTS_ARE_DIFFERENT(""),
	CONTRACT_SUPPLYCONTRACTS_ARE_NOT_VALID(""),
	CONTRACT_SUPPLYCONTRACTS_ARE_NULL("Comparing 'actual' or 'expected' Contract with null SupplyContracts association."),
	CONTRACT_SIZE_SUPPLYCONTRACTS_ARE_DIFFERENT(""),
	SUPPLYCONTRACT_CONTRACT_IS_NOT_VALID(""),//RES-60
	SUPPLYCONTRACT_SUPPLIER_IS_NOT_VALID(""),//RES-60
	SUPPLYCONTRACT_STARTDATE_NOT_VALID(""),
	SUPPLYCONTRACT_ENDDATE_NOT_VALID(""),
	SUPPLIER_NAME_IS_NOT_VALID(""),
	SUPPLIER_EMPLOYMENTCONTRACTS_ARE_NOT_VALID(""),
	SUPPLIER_EMPLOYMENTCONTRACTS_ARE_NULL("Comparing 'actual' or 'expected' Supplier with null EmploymentContracts association."),
	SUPPLIER_SIZE_EMPLOYMENTCONTRACTS_ARE_DIFFERENT(""),
	SUPPLIER_SUPPLYCONTRACTS_ARE_NOT_VALID(""),
	SUPPLIER_SIZE_SUPPLYCONTRACTS_ARE_DIFFERENT(""),
	SUPPLIER_SUPPLYCONTRACTS_ARE_NULL("Comparing 'actual' or 'expected' Supplier with null SupplyContracts association.");

	private String description; //RES-58
	private ValidationResult(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
