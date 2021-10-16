package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.ACCENTURE_SUPPLIER;
import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.ALTERNA;
import static com.tecxis.resume.domain.Constants.AMESYS;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaSupplierDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private SupplyContractRepository supplyContractRepo;
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testSave() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));
		
		Supplier accenture = Utils.insertSupplier(ACCENTURE_SUPPLIER,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(1, accenture.getId().longValue());
		
	}
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testAdd() {		
		Supplier supplierIn = Utils.insertSupplier(ALPHATRESS, entityManager);
		Supplier supplierOut = supplierRepo.getSupplierByName(ALPHATRESS);
		assertEquals(supplierIn, supplierOut);
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));		
		Supplier tempSupplier = Utils.insertSupplier(AMESYS, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));
		supplierRepo.delete(tempSupplier);
		assertNull(supplierRepo.getSupplierByName(AMESYS));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Supplier> suppliers = supplierRepo.findAll();
		assertEquals(5, suppliers.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Supplier> pageableSupplier = supplierRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableSupplier.getSize());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplierByName() {
		Supplier accenture= supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertEquals(ACCENTURE_SUPPLIER, accenture.getName());
		
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());
		
		Supplier alterna = supplierRepo.getSupplierByName(ALTERNA);
		assertEquals(ALTERNA, alterna.getName());
		
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);		
		assertEquals(ALPHATRESS, alphatress.getName());	
		
	}
}
