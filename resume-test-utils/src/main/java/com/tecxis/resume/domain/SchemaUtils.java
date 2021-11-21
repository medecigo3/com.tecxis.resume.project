package com.tecxis.resume.domain;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import org.springframework.jdbc.core.JdbcTemplate;

public final class SchemaUtils {
	private SchemaUtils() {}

	public static void testInitialState(JdbcTemplate jdbcTemplate) {			
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));	
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.CLIENT_TABLE));	
		assertEquals(13	, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));			
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE));				
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));	
		assertEquals(3, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(54, countRowsInTable(jdbcTemplate, SchemaConstants.TASK_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.SERVICE_TABLE));	
		assertEquals(63, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));	
		assertEquals(7, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));			
	}

	public static void testStateAfterAxeltisClientDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE)); // 2 Client orphans removed
		assertEquals(47, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE)); // 16 Client orphans removed
		assertEquals(11, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE)); // 2 Client orphans removed 
		assertEquals(11, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); // 2 Client orphans removed
		assertEquals(11	, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE)); // 2 Client orphans removed
		assertEquals(11, countRowsInTable(jdbcTemplate, SchemaConstants.CLIENT_TABLE)); // 1 Client parent removed
		
	}

	public static void testStateAfterLondonCityDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(4, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE)); // 1 City parent removed
		assertEquals(3, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));  
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE)); //Cascaded 2 child City entities being removed 
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE)); 
	}

	public static void testStateAfterTask12Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));  // Cascaded to 1 child Assignmentremoved
		assertEquals(53, countRowsInTable(jdbcTemplate, SchemaConstants.TASK_TABLE));	//1 Task parent entity removed 
		assertEquals(13	, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));
		
	}

	public static void testStateAfterAxeltisFastconnectAgreementDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE)); //1 parent entity removed
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE)); 
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.SERVICE_TABLE));	
		
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
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); //Parent is removed
		/**Tests the cascaded children of the OneToMany association between Supplier -> SupplyContract*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	//1 child with CONTRACT_ID='5' removed from the SUPPLY_CONTRACT table.
		/**Tests the cascaded children of the OneToMany association between Contract -> Agreement */
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE)); //1 child with CONTRACT_ID='5' removed from the AGREEMENT table. 		
		/**Tests post state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));  //1 child with CONTRACT_ID='5' previously removed from SUPPLY_CONTRACT table. That cascades to 0 parent being removed from the SUPPLIER table. 
		/**Tests the cascaded children of the OneToMany association between Supplier -> EmploymentContract*/
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	//0 children previously removed from SUPPLIER table. That cascades to 0 children being removed from the EMPLOYMENT_CONTRACT table.		
		/**Tests the cascaded parent of the OneToMany association between  Staff -> EmploymentContract */
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE)); //0 children previously removed from EMPLOYMENT_CONTRACT table. That cascades to 0 parent being removed from the STAFF table.		
	}

	public static void testStateAfterBw6CourseDelete(JdbcTemplate jdbcTemplate) {
		/**Test course was removed*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE)); //1 parent entity removed
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE)); //Cascaded 1 child Course entity being removed
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
	}

	public static void testStateAfterFranceDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE)); //1 City removed in previous remove transaction, 1 new City inserted = same no. of Cities
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE)); // 1 Country parent entity removed
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE)); //Cascaded 9 child City entities being removed in previous remove transaction 
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE)); //No changes
	}

	public static void testStateAfterParisDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(4, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE)); //1 parent entity removed
		assertEquals(3, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE)); //Cascaded 9 child City entities being removed (13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));
	}

	public static void testStateAfterFranceCountryWithDetachedChildrenDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE)); //Cascaded 9 child City entities being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));
		
	}

	public static void testStateAfterJohnAlhpatressEmploymentContractDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); 
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	//1 entity removed				
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));
	}

	public static void testStateAfterHobbyDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE)); //1 parent entity removed
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));	
		
	}

	public static void testStateAfterMorningstartV1ProjectLocationDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE)); //1 entity removed
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));
		
	}

	public static void testStateAfterMorningstartV1ProjectDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.CLIENT_TABLE));
		assertEquals(53, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));  //Cascaded 10 child Project entities being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE)); 
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE)); //1 parent entity removed
		
	}

	public static void testStateAfterTibcoBwConsultantServiceDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SERVICE_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));	//Cascaded 8 child Service entities being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));					
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
	}

	public static void testStateAfterTibcoSkillDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));  //1 parent entity removed
		/***Test Skill DELETE many-to-many cascadings*/
		assertEquals(4, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE)); //Cascaded 1 child Skill entity being removed
		/**Test Staff hasn't changed*/
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
	}

	public static void testStateAfterAmtTibcoStaffSkillDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));
		assertEquals(4, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE)); //1 entity removed
	}

	public static void testStateAfterAmtParcoursAssignment14AssignmentDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		/**Tests initial state children tables*/
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));//1 entity removed
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));			
	}

	public static void testStateAfterJohnStaffWithDetachedChildrenDelete(JdbcTemplate jdbcTemplate) {		
		/**Test Staff is removed**/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));	
		/**Test non-identifying Staff-> Interest children table didn't change*/
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE));
		/**Tests state of children tables*/		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE));  /**O children for John Staff removed here*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE)); 	 /**O children for John Staff removed here*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	
		assertEquals(62, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 
		
	}

	public static void testStateAfterJohnStaffDelete(JdbcTemplate jdbcTemplate) {		
		/**Tests initial state parent table*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE)); //1 parent entity removed
		/**Tests initial state children tables*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE)); // Cascaded to 1 child Staff being removed
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE));				
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));  // Cascaded to 1 child Staff being removed
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	// Cascaded to 1 child Employment being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));  //Cascaded to 1 Child SupplyContract being removed
		assertEquals(62, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));	//Cascaded to 1 Child Assignment being removed
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
		
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
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE)); // 1 child with STAFF_ID=1 removed from INTEREST table.
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE)); // 5 children with STAFF_ID=1 removed from STAFF_SKILL table.
		assertEquals(7, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));	
		/**ENROLS*/
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE)); // 1 child with STAFF_ID=1 removed from ENROLMENT table. 
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE)); // 5 children with STAFF_ID=1 removed from EMPLOYMENT_CONTRACT table.  
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); // 13 children with STAFF_ID=1 removed from SUPPLY_CONTRACT table. 
		/**WORKS ON*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE)); // 62 children with STAFF_ID=1 removed from  table. 
		assertEquals(54, countRowsInTable(jdbcTemplate, SchemaConstants.TASK_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));		
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));  
		/**Finally the state of Staff table (the parent)*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));  //1 Parent removed
		
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
		assertEquals(4, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); //Parent is removed
		/**Tests the cascaded children of the OneToMany association between Supplier -> SupplyContract*/
		assertEquals(11, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	//3 children with SUPPLIER_ID = '1' removed from the SUPPLY_CONTRACT table.
		/**Tests the cascaded children of the OneToMany association between Supplier -> EmploymentContract*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	//1 child with SUPPLIER_ID = '1' removed from the EMPLOYMENT_CONTRACT table. 
		/**Tests the cascaded parent of the OneToMany association between Contract -> SupplyContract*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); //3 children with SUPPLIER_ID = '1' previously removed from SUPPLY_CONTRACT table. That cascades to 0 parent being removed from the CONTRACT table. 
		/**Tests the cascaded parent of the OneToMany association between  Staff -> EmploymentContract */
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE)); //1 child with with SUPPLIER_ID = '1' previously removed from EMPLOYMENT_CONTRACT table. That cascades to 0 parent being removed from the STAFF table.
		/**Tests the cascaded children of the OneToMany association between Contract -> Agreement */
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE)); //0 parents previously removed from CONTRACT table. That cascades to 0 children removed from the AGREEMENT table.
		
	}

	public static void testStateAfterFastconnectMicropoleSupplyContractDelete(JdbcTemplate jdbcTemplate) {	
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));//1 parent entity removed	
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));			
	}

	public static void testStateAfterBwEnrolmentDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));  //1 parent entity removed	
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE)); //No Cascade REMOVE
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));	//No cascade REMOVE		
	}

	public static void testInsertAgreementInitialState(JdbcTemplate jdbcTemplate) {
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SERVICE_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));						
	}
	
	public static void testStateAfterAgreementInsert(JdbcTemplate jdbcTemplate) {		
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SERVICE_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));		
	}

	public static void testInsertAssignmentInitialState(JdbcTemplate jdbcTemplateProxy) {
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));		
	}

	public static void testStateAfterAssignmentInsert(JdbcTemplate jdbcTemplateProxy) {
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));		
	}
	
}
