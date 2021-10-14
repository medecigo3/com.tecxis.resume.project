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

	

}
