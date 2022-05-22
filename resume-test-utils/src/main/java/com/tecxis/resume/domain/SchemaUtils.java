package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.SchemaConstants.AGREEMENT_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.CITY_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.CLIENT_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.CONTRACT_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.COUNTRY_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.COURSE_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.ENROLMENT_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.INTEREST_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.LOCATION_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.PROJECT_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.SERVICE_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.SKILL_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.STAFF_SKILL_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.STAFF_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.SUPPLY_CONTRACT_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.TASK_TABLE;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_AGREEMENT;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_ASSIGNMENT;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_CITY;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_CLIENT;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_CONTRACT;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_COUNTRY;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_COURSE;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_EMPLOYMENT_CONTRACT;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_ENROLMENT;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_INTEREST;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_LOCATION;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_PROJECT;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_SERVICE;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_SKILL;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_STAFF;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_STAFF_SKILL;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_SUPPLIER;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_SUPPLY_CONTRACT;
import static com.tecxis.resume.domain.SchemaConstants.TOTAL_TASK;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import org.springframework.jdbc.core.JdbcTemplate;

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

	public static void testStateAfterAxeltisClientDelete(JdbcTemplate jdbcTemplate) {
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

	public static void testStateAfterLondonCityDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(4, countRowsInTable(jdbcTemplate, CITY_TABLE)); // 1 City parent removed
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));  
		assertEquals(12, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 2 child City entities being removed 
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); 
	}

	public static void testStateAfterTask12Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));  // Cascaded to 1 child Assignmentremoved
		assertEquals(53, countRowsInTable(jdbcTemplate, TASK_TABLE));	//1 Task parent entity removed 
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}

	public static void testStateAfterAxeltisFastconnectAgreementDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(12, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE)); //1 parent entity removed
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		
	}

	public static void testStateAfterContract5Delete(JdbcTemplate jdbcTemplate) {
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

	public static void testStateAfterBw6CourseDelete(JdbcTemplate jdbcTemplate) {
		/**Test course was removed*/
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE)); //1 parent entity removed
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE)); //Cascaded 1 child Course entity being removed
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}

	public static void testStateAfterFranceDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE)); //1 City removed in previous remove transaction, 1 new City inserted = same no. of Cities
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE)); // 1 Country parent entity removed
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 9 child City entities being removed in previous remove transaction 
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); //No changes
	}

	public static void testStateAfterParisDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(4, countRowsInTable(jdbcTemplate, CITY_TABLE)); //1 parent entity removed
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 9 child City entities being removed (13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
	}

	public static void testStateAfterFranceCountryWithDetachedChildrenDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 9 child City entities being removed
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}

	public static void testStateAfterJohnAlhpatressEmploymentContractDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//1 entity removed				
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
	}

	public static void testStateAfterHobbyDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE)); //1 parent entity removed
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		
	}

	public static void testStateAfterMorningstartV1ProjectLocationDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //1 entity removed
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}

	public static void testStateAfterMorningstartV1ProjectDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(53, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));  //Cascaded 10 child Project entities being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE));   //Cascaded 1 child Project entities being removed 
		assertEquals(TOTAL_CITY, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); //1 parent entity removed
		
	}

	public static void testStateAfterTibcoBwConsultantServiceDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(5, countRowsInTable(jdbcTemplate, SERVICE_TABLE)); //1 parent service deleted	
		assertEquals(5, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));	//Cascaded 8 child Service entities being removed
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));					
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}

	public static void testStateAfterTibcoSkillDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(6, countRowsInTable(jdbcTemplate, SKILL_TABLE));  //1 parent entity removed
		/***Test Skill DELETE many-to-many cascadings*/
		assertEquals(4, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE)); //Cascaded 1 child Skill entity being removed
		/**Test Staff hasn't changed*/
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}

	public static void testStateAfterAmtTibcoStaffSkillDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(4, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE)); //1 entity removed
	}

	public static void testStateAfterAmtParcoursAssignment14AssignmentDelete(JdbcTemplate jdbcTemplate) {		
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

	public static void testStateAfterJohnStaffWithDetachedChildrenDelete(JdbcTemplate jdbcTemplate) {		
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

	public static void testStateAfterJohnStaffDelete(JdbcTemplate jdbcTemplate) {		
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

	public static void testStateAfterAMtStaffDelete(JdbcTemplate jdbcTemplate) {		
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

	public static void testStateAfterAccentureSupplierDelete(JdbcTemplate jdbcTemplate) {	
		/**See SQL cascadings applied to one-to-many relations*/
		/**SUPPLIER 	-> SUPPLY_CONTRACT 				Cascade.REMOVE*/
		/**SUPPLIER 	-> EMPLOYMENT_CONTRACT 			Cascade.REMOVE*/
	
			
		/**Cascadings in this sequence*/
		/**  SUPPLIER (P) -> SUPPLY_CONTRACT (c) */	
		/**      |                               */
		/**      |                      		 */
		/**      v                               */
		/** EMPLOYMENT_CONTRACT (c)              */
		
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

	public static void testStateAfterFastconnectMicropoleSupplyContractDelete(JdbcTemplate jdbcTemplate) {	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));//1 parent entity removed	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));			
	}

	public static void testStateAfterBwEnrolmentDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));  //1 parent entity removed	
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //No Cascade REMOVE
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));	//No cascade REMOVE		
	}

	public static void testInsertAgreementInitialState(JdbcTemplate jdbcTemplate) {
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));						
	}
	
	public static void testStateAfterAgreementInsert(JdbcTemplate jdbcTemplate) {		
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));		
	}

	public static void testInsertAssignmentInitialState(JdbcTemplate jdbcTemplateProxy) {
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, PROJECT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, ASSIGNMENT_TABLE));		
	}

	public static void testStateAfterAssignmentInsert(JdbcTemplate jdbcTemplateProxy) {
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, PROJECT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, ASSIGNMENT_TABLE));		
	}
		
	public static void testStateAfterAssignmentDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
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
		assertEquals(62, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	//One Assignment deleted
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	public static void testInsertCityInitialState(JdbcTemplate jdbcTemplateProxy) {
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, COUNTRY_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, CITY_TABLE));	
		
	}

	public static void testStateAfterCityInsert(JdbcTemplate jdbcTemplateProxy) {
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, COUNTRY_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, CITY_TABLE));	
		
	}
	
	public static void testStateAfterCityBrusslesUpdate(JdbcTemplate jdbcTemplate) {			
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));			
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));			
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
	
	public static void testStateAfterCityLondonUpdate(JdbcTemplate jdbcTemplate) {			
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));			
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));			
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
	
	public static void testStateAfterLondonCityLocationsUpdate(JdbcTemplate jdbcTemplate) {			
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));			
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));			
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
	
	public static void testStateBeforeClientInsert(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}
	
	public static void testStateAfterClientInsert(JdbcTemplate jdbcTemplate) {
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}
	
	public static void testStateAfterCityLondonDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(TOTAL_ENROLMENT, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(TOTAL_CLIENT, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(TOTAL_PROJECT	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(TOTAL_CONTRACT, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(TOTAL_AGREEMENT, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));			
		assertEquals(TOTAL_PROJECT, countRowsInTable(jdbcTemplate, PROJECT_TABLE));			
		assertEquals(12, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		//Delete of London City cascades to 2 Locations deleted		
		assertEquals(TOTAL_SUPPLY_CONTRACT, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(TOTAL_COURSE, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(TOTAL_INTEREST, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(TOTAL_STAFF, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(TOTAL_COUNTRY, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(4, countRowsInTable(jdbcTemplate, CITY_TABLE)); //1 parent removed
		assertEquals(TOTAL_STAFF_SKILL, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(TOTAL_SUPPLIER, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(TOTAL_TASK, countRowsInTable(jdbcTemplate, TASK_TABLE));	
		assertEquals(TOTAL_EMPLOYMENT_CONTRACT, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(TOTAL_SERVICE, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(TOTAL_ASSIGNMENT, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(TOTAL_SKILL, countRowsInTable(jdbcTemplate, SKILL_TABLE));		
	}
}
