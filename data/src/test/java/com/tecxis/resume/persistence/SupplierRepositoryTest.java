package com.tecxis.resume.persistence;

import static com.tecxis.resume.persistence.StaffRepositoryTest.*;
import static org.junit.Assert.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;
import com.tecxis.resume.SupplierPK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SupplierRepositoryTest {
	
	public static final String SUPPLIER_TABLE = "Supplier";
	public static final String ACCENTURE = "ACCENTURE";
	public static final String AMESYS = "AMESYS";
	public static final String FASTCONNECT = "FASTCONNECT";
	public static final String ALTERNA = "ALTERNA";
	public static final String ALPHATRESS = "ALPHATRESS";
			
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffRepository staffRepo;
		
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, staffRepo, entityManager);
		insertASupplier(amt.getStaffId(), ACCENTURE, supplierRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void shouldBeAbleToFindInsertedSupplier() {
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, staffRepo, entityManager);
		Supplier supplierIn = insertASupplier(amt.getStaffId(), ALPHATRESS, supplierRepo, entityManager);
		Supplier supplierOut = supplierRepo.getSupplierByName(ALPHATRESS);
		assertEquals(supplierIn, supplierOut);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindSupplierByName() {
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertNotNull(accenture);
		assertEquals(ACCENTURE, accenture.getName());
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertNotNull(fastconnect);
		assertEquals(FASTCONNECT, fastconnect.getName());
		Supplier alterna = supplierRepo.getSupplierByName(ALTERNA);
		assertNotNull(alterna);
		assertEquals(ALTERNA, alterna.getName());
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteSupplier() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, staffRepo, entityManager);
		Supplier tempSupplier = insertASupplier(amt.getStaffId(), AMESYS, supplierRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		supplierRepo.delete(tempSupplier);
		assertNull(supplierRepo.getSupplierByName(AMESYS));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
	}
		
	public static Supplier insertASupplier(long staffId, String name, SupplierRepository supplierRepo, EntityManager entityManager) {
		SupplierPK supplierPk = new SupplierPK();
		supplierPk.setStaffId(staffId);
		assertEquals(0, supplierPk.getSupplierId());
		Supplier supplier = new Supplier();
		supplier.setName(name);
		supplier.setId(supplierPk);
		supplierRepo.save(supplier);
		assertNotNull(supplier.getId().getSupplierId());
		entityManager.flush();
		return supplier;
	}
}
