package com.tecxis.resume.domain;

import org.springframework.jdbc.core.JdbcTemplate;

import static com.tecxis.resume.domain.SchemaConstants.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

public final class SchemaUtils {
	private SchemaUtils() {}

	public static void testInitialState(JdbcTemplate jdbcTemplate) {			
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	public static void testStateAfter_ClientAxeltis_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); // 2 Client orphans removed
		assertEquals(47, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE)); // 16 Client orphans removed
		assertEquals(11, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE)); // 2 Client orphans removed 
		assertEquals(11, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); // 2 Client orphans removed
		assertEquals(11	, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); // 2 Client orphans removed
		assertEquals(11, countRowsInTable(jdbcTemplate, CLIENT_TABLE)); // 1 Client parent removed				
		assertEquals(12, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//2 Contracts removed -> cascades to 2 SupplyContracts removed
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));	
		
	}

	public static void testStateAfter_LondonCity_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));		
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));						
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(4, countRowsInTable(jdbcTemplate, CITY_TABLE)); // 1 City parent removed
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));  
		assertEquals(12, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 2 child City entities being removed 
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); 
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));			
	}
	

	public static void testStateAfter_Task_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));				
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));			
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));			
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));			
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));  // Cascaded to 1 child Assignmentremoved
		assertEquals(53, countRowsInTable(jdbcTemplate, TASK_TABLE));	//1 Task parent entity removed 
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}

	public static void testStateAfter_AgreementAxeltisFastconnect_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));		
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));			
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));				
		assertEquals(12, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE)); //1 parent entity removed
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		
	}

	public static void testStateAfter_ContractFastconnectMicropole_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));		
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		
		/**See SQL cascadings applied to one-to-many relations*/
		/**CONTRACT 	-> 		SUPPLY_CONTRACT					CascadeType.ALL*/
		/**CONTRACT 	-> 		AGREEMENT		CascadeType.ALL*/
		
		/*** Cascadings in this sequence*/
		/**  CONTRACT (P) -> SUPPLY_CONTRACT (c) */	
		/**      |                               */
		/**      |                      		 */
		/**      v                               */
		/** AGREEMENT (c)       */
		
		/**Tests the cascaded parent of the OneToMany association between Contract -> SupplyContract*/		
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); //Parent is removed
		/**Tests the cascaded children of the OneToMany association between Supplier -> SupplyContract*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//1 child with CONTRACT_ID='5' removed from the SUPPLY_CONTRACT table.
		/**Tests the cascaded children of the OneToMany association between Contract -> Agreement */
		assertEquals(12, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE)); //1 child with CONTRACT_ID='5' removed from the AGREEMENT table. 		
		/**Tests post state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));  //1 child with CONTRACT_ID='5' previously removed from SUPPLY_CONTRACT table. That cascades to 0 parent being removed from the SUPPLIER table. 
		/**Tests the cascaded children of the OneToMany association between Supplier -> EmploymentContract*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//0 children previously removed from SUPPLIER table. That cascades to 0 children being removed from the EMPLOYMENT_CONTRACT table.		
		/**Tests the cascaded parent of the OneToMany association between  Staff -> EmploymentContract */
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //0 children previously removed from EMPLOYMENT_CONTRACT table. That cascades to 0 parent being removed from the STAFF table.		
	}

	public static void testStateAfter_CourseBw6_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));				
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));			
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));			
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		/**Test course was removed*/
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE)); //1 parent entity removed
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE)); //Cascaded 1 child Course entity being removed
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}

	public static void testStateAfter_CountryFrance_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));			
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));					
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE)); //1 City removed in previous remove transaction, 1 new City inserted = same no. of Cities
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE)); // 1 Country parent entity removed
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 9 child City entities being removed in previous remove transaction 
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); //No changes
	}

	public static void testStateAfter_ParisCity_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));			
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));						
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(4, countRowsInTable(jdbcTemplate, CITY_TABLE)); //1 parent entity removed
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 9 child City entities being removed (13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
	}

	public static void testStateAfter_CountryFrance_Delete_WithDetachedChildren(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 9 child City entities being removed
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}

	public static void testStateAfter_EmploymentContractJohnAlhpatress_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//1 entity removed				
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
	}

	public static void testStateAfter_Hobby_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));				
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));				
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE)); //1 parent entity removed
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		
	}

	public static void testStateAfter_MorningstarV1Project_Locations_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //1 entity removed
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}

	public static void testStateAfter_MorningstarV1Project_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(53, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));  //Cascaded 10 child Project entities being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE));   //Cascaded 1 child Project entities being removed 
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); //1 parent entity removed
		
	}

	public static void testStateAfter_ServiceTibcoBwConsultant_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, SERVICE_TABLE)); //1 parent service deleted	
		assertEquals(5, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));	//Cascaded 8 child Service entities being removed
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));					
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}

	public static void testStateAfter_SkillTibco_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));				
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, SKILL_TABLE));  //1 parent entity removed
		/***Test Skill DELETE many-to-many cascadings*/
		assertEquals(4, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE)); //Cascaded 1 child Skill entity being removed
		/**Test Staff hasn't changed*/
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}

	public static void testStateAfter_StaffSkillAmtTibco_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));				
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(4, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE)); //1 entity removed
	}

	public static void testStateAfter_AssignmentParcoursAmtTask14_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests initial state children tables*/
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));//1 entity removed
		/**Test other parents for control*/ 
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
	}

	public static void testStateAfter_StaffJohn_Delete_WithDetachedChildren(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));						
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		/**Test Staff is removed**/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		/**Test non-identifying Staff-> Interest children table didn't change*/
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		/**Tests state of children tables*/		
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));  //O children for John Staff removed here
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE)); 	 //O children for John Staff removed here
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//1 Staff parent removed, cascaded to 1 child EMPLOYMENT_CONTRACT entity being removed 
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); //1 Supplier parent removed, cascaded to 1 child SUPPLY CONTRACT being removed 	
		assertEquals(62, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE)); //1 Staff parent removed, cascaded to 1 child ASSIGNMENT being removed. 
		/**Test other parents for control*/ 
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));		
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		
	}

	public static void testStateAfter_StaffJohn_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));				
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));							
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));				
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		/**Tests initial state parent table*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //1 parent entity removed
		/**Tests initial state children tables*/
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE)); // Cascaded to 1 child Staff being removed
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));				
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));  // Cascaded to 1 child Staff being removed
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	// Cascaded to 1 child Employment being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));  //Cascaded to 1 Child SupplyContract being removed
		assertEquals(62, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	//Cascaded to 1 Child Assignment being removed
		/**Test other parents for control*/ 
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		
	}

	public static void testStateAfter_StaffAmt_Delete(JdbcTemplate jdbcTemplate) {

		/**See SQL cascadings applied to one-to-many relations*/
		/**STAFF 	-> 	ENROLMENT_CONTRACT 			CascadeType.ALL*/
		/**STAFF 	-> 	SUPPLY_CONTRACT 			CascadeType.ALL*/
		/**STAFF 	->	INTEREST (Non-Identifying)  CascadeType.ALL*/
		
		/**See SQL cascadings applied to many-to-many relations*/
		/**STAFF 	-> ENROLEMENT 	-> COURSE*/
		/**STAFF	-> STAFF_SKILL	-> SKILL */
		
		/**Cascadings in this sequence*/
		/**STAFF (P)  ------>  INTEREST (c)
		 *    ¦
		 *    --------------> STAFF_SKILL (c)
		 *    ¦
		 *    --------------> ENROLMENT (c)
		 *    ¦
		 *    --------------> EMPLOYMENT_CONTRACT (c) 
		 *    ¦
		 *    --------------> SUPPLY_CONTRACT (c)
		 *    ¦
		 *    --------------> ASSIGNMENT (c)
		 *    
		 */
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		/**HAS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE)); // 1 child with STAFF_ID=1 removed from INTEREST table.
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE)); // 5 children with STAFF_ID=1 removed from STAFF_SKILL table.
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));	
		/**ENROLS*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE)); // 1 child with STAFF_ID=1 removed from ENROLMENT table. 
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE)); // 5 children with STAFF_ID=1 removed from EMPLOYMENT_CONTRACT table.  
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); // 13 children with STAFF_ID=1 removed from SUPPLY_CONTRACT table. 
		/**WORKS ON*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE)); // 62 children with STAFF_ID=1 removed from  table. 
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));		
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));  
		/**Finally the state of Staff table (the parent)*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //1 Parent removed
		
	}

	public static void testStateAfter_SupplierAccenture_Delete(JdbcTemplate jdbcTemplate) {
		/**See SQL cascadings applied to one-to-many relations*/
		/**SUPPLIER 	-> SUPPLY_CONTRACT 				Cascade.REMOVE*/
		/**SUPPLIER 	-> EMPLOYMENT_CONTRACT 			Cascade.REMOVE*/
	
			
		/**Cascadings in this sequence*/
		/**  SUPPLIER (P) -> SUPPLY_CONTRACT (c) */	
		/**      |                               */
		/**      |                      		 */
		/**      v                               */
		/** EMPLOYMENT_CONTRACT (c)              */
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		/**Tests post state of Suppliers table (the parent)*/
		assertEquals(4, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //Parent is removed
		/**Tests the cascaded children of the OneToMany association between Supplier -> SupplyContract*/
		assertEquals(11, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//3 children with SUPPLIER_ID = '1' removed from the SUPPLY_CONTRACT table.
		/**Tests the cascaded children of the OneToMany association between Supplier -> EmploymentContract*/
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//1 child with SUPPLIER_ID = '1' removed from the EMPLOYMENT_CONTRACT table. 
		/**Tests the cascaded parent of the OneToMany association between Contract -> SupplyContract*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); //3 children with SUPPLIER_ID = '1' previously removed from SUPPLY_CONTRACT table. That cascades to 0 parent being removed from the CONTRACT table. 
		/**Tests the cascaded parent of the OneToMany association between  Staff -> EmploymentContract */
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //1 child with with SUPPLIER_ID = '1' previously removed from EMPLOYMENT_CONTRACT table. That cascades to 0 parent being removed from the STAFF table.
		/**Tests the cascaded children of the OneToMany association between Contract -> Agreement */
		assertEquals(13, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE)); //0 parents previously removed from CONTRACT table. That cascades to 0 children removed from the AGREEMENT table.
		
	}

	public static void testStateAfter_SupplyContractFastconnectMicropole_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));	
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));			
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));							
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));				
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));//1 parent entity removed	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));			
	}

	public static void testStateAfter_EnrolmentBw_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));		
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));  //1 parent entity removed	
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //No Cascade REMOVE
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));	//No cascade REMOVE		
	}

	public static void testStateBefore_Agreement_Insert(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));						
	}
	
	public static void testStateAfter_Agreement_Insert(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));		
	}

	public static void testStateBefore_Assignment_Insert(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));				
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
	}

	public static void testStateAfter_Assignment_Insert(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
	}
		
	public static void testStateBefore_City_Insert(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		
	}

	public static void testStateAfter_City_Insert(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		
	}
	
	public static void testStateAfter_CityBrussels_Update(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	//Delete on CITY cascades to LOCATION			
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));			
	}
	
	public static void testStateAfter_LondonCity_Update(JdbcTemplate jdbcTemplate) {			
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));	
		assertEquals(12, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	//Deletes 1 CITY cascades to 2 LOCATIONs			
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));			
	}
	
	public static void testStateAfter_LondonCity_Locations_Update(JdbcTemplate jdbcTemplate) {			
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));	
		assertEquals(15, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	//London City +1 Location			
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));			
	}
	
	public static void testStateBefore_Client_Insert(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}
	
	public static void testStateAfter_Client_Insert(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));				
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}
	
	public static void testStateAfter_SagemContract_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); //1 Contract removed
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));//1 orphan removed
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); //1 orphan removed
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}
	
	public static void testStateAfter_SagemContract_Client_Update(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE)); //1 orphan removed
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
	}
	
	public static void testStateAfter_MorningstarV1Project_Location_Delete_ByParisCity(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //1 child removed
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	public static void testStateAfter_MorningstarV1Project_Client_Update(JdbcTemplate jdbcTemplate) {
		 assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(53, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE)); //Casacades to remove 10 Project assignments belonging to 'morningstartV1Project'. 
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		
	}

	public static void testStateAfter_ContractArval_Update_Agreements(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE)); //1 orphan removed and 1 new child created in EMPLOYMENT_CONTRACT table. Other tables remain unchanged.
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));

	}

	public static void testStateAfter_ContractAmesysSagem_Update_SupplyContracts(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));//1 orphan removed and 1 new child created in SUPPLY_CONTRACT table. Other tables remain unchanged.
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		
	}
	
	public static void testStateAfter_ContractAmesysSagem_Update_NullSupplyContracts(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); //1 orphan removed.
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	public static void testStateAfter_StaffAmt_Delete_SupplyContracts(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	//1 orphan child is removed.	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		
	}

	public static void testStateAfter_SupplierAccenture_Update_EmploymentContracts(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE)); //1 orphan removed and 1 new child created in EMPLOYMENT_CONTRACT table. Other tables remain unchanged.
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
			
	}

	public static void testStateAfter_SupplierAccenture_Update_SupplyContracts(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); //3 orphans removed and 1 new child created in SUPPLY_CONTRACT table.  Other tables remain unchanged.
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));	
		 
		
	}

	public static void testStateAfter_ProjectAdirV1_Delete_Locations(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); //Remove 1 orphan
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));	
		
		
	}
	
	public static void testStateAfter_ProjectAdirV1_Update_Assignments(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); //1 Parent (non-owner) removed, 1 inserted. 
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE)); //1 child (owner) Location removed
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); 
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(58, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE)); //6 child (owners) assignments removed, 1 inserted
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));	
		
		
	}

	public static void testStateAfter_UnrelatedProject_Location_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		
	}

	public static void testStateAfter_ServiceBw_Delete_Agreements(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));	//1 orphan child is removed.
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		
	}

	public static void testStateAfter_ServiceBw_Update_Agreement(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE)); //1 orphan child is removed	
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
		
	}

	public static void testStateAfter_StaffAmt_Update_NullEmploymentContracts(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));  //AMT STAFF_ID='1'	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	// 5 orphans removed in EMPLOYMENT_CONTRACT table. Other tables ramain unchanged.
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		
	}

	public static void testStateAfter_StaffJohn_Update_NullEmploymentContracts(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));		 //John STAFF_ID='2'
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE)); // 1 orphan removed in EMPLOYMENT_CONTRACT table. Other tables ramain unchanged.
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		

		
	}

	public static void testStateAfter_CityParis_Update_NullLocations(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); // // 9 orphan removed in LOCATION table.
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		
	}
	
	public static void testStateAfter_ContractArval_Update_NullAgreements(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE)); //1 orphan removed, other tables remain unchanged.
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));

	}
	
	public static void testStateAfter_ProjectAdirV1_Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
	}

	public static void testStateAfter_ClientAgeas_Update_Contract(JdbcTemplate jdbcTemplate){//RES-42
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));  //1 orphan removed in CONTRACT table
		assertEquals(12, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE)); // 1 orphan removed in AGREEMENT table
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); //1 orphan removed in SUPPLY_CONTRACT table
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	public static void testStateAfter_ClientAgeas_Update_NullContracts(JdbcTemplate jdbcTemplate){
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));  //1 orphan removed in CONTRACT table
		assertEquals(12, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE)); // 1 orphan removed in AGREEMENT table
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); //1 orphan removed in SUPPLY_CONTRACT table
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	public static void testStateAfter_CountryFrance_Update_Cities(JdbcTemplate jdbcTemplate){
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(TOTAL_LOCATION, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, CITY_TABLE)); //2 new inserted, 1 orphan not removed
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	public static void testStateAfter_ProjectSherpaV1_Update_Cities(JdbcTemplate jdbcTemplate){
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(15, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //1 orphan removed, 2 LOCATIONS inserted.
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	public static void testStateAfter_ProjectSherpaV1_Update_NullCities(JdbcTemplate jdbcTemplate){
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //1 orphan removed
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}
}
