package com.tecxis.resume.persistence;

import static com.tecxis.resume.StaffTest.insertAStaff;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT10_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT10_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT11_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT11_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT13_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT13_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT5_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT5_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT6_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT6_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT7_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT7_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT8_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT8_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT9_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT9_STARTDATE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JHON_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
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

import com.tecxis.resume.Contract;
import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;
import com.tecxis.resume.SupplierTest;

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
	private SupplierRepository supplierRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		
		Supplier accenture = SupplierTest.insertASupplier(amt, ACCENTURE,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, accenture.getId());
		
	}
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void shouldBeAbleToFindInsertedSupplier() {
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Supplier supplierIn = SupplierTest.insertASupplier(amt, ALPHATRESS, entityManager);
		Supplier supplierOut = supplierRepo.getSupplierByNameAndStaff(ALPHATRESS, amt);
		assertEquals(supplierIn, supplierOut);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindSupplierByName() {
		List <Supplier> accentureSuppliers = supplierRepo.getSuppliersByName(ACCENTURE);
		assertEquals(1, accentureSuppliers.size());
		Supplier accenture = accentureSuppliers.get(0);
		assertNotNull(accenture);
		assertEquals(ACCENTURE, accenture.getName());
		
		List <Supplier> fastconnectSuppliers = supplierRepo.getSuppliersByName(FASTCONNECT);
		assertEquals(1, fastconnectSuppliers.size());
		Supplier fastconnect = fastconnectSuppliers.get(0);
		assertNotNull(fastconnect);		
		assertEquals(FASTCONNECT, fastconnect.getName());
		
		List <Supplier> alternaSuppliers = supplierRepo.getSuppliersByName(ALTERNA);
		assertEquals(1, alternaSuppliers.size());
		Supplier alterna = alternaSuppliers.get(0);
		assertNotNull(alterna);
		assertEquals(ALTERNA, alterna.getName());
		
		List <Supplier> alphatressSuppliers = supplierRepo.getSuppliersByName(ALPHATRESS);
		assertEquals(2, alphatressSuppliers.size());
		Supplier alphatress1 = alphatressSuppliers.get(0);
		assertNotNull(alphatress1);
		assertEquals(ALPHATRESS, alphatress1.getName());
		Supplier alphatress2 = alphatressSuppliers.get(1);
		assertNotNull(alphatress2);
		assertEquals(ALPHATRESS, alphatress2.getName());
		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindSupplierByNameAndStaffId() {
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier accenture = supplierRepo.getSupplierByNameAndStaff(ACCENTURE, amt);
		assertNotNull(accenture);
		assertEquals(ACCENTURE, accenture.getName());
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		assertNotNull(fastconnect);
		assertEquals(FASTCONNECT, fastconnect.getName());
		Supplier alterna = supplierRepo.getSupplierByNameAndStaff(ALTERNA, amt);
		assertNotNull(alterna);
		assertEquals(ALTERNA, alterna.getName());
				
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindSupplierContracts() {
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		assertNotNull(fastconnect.getContracts());
		assertEquals(6, fastconnect.getContracts().size());
		List <Contract> fastconnectContracts = fastconnect.getContracts();
		Contract fastconnectContract1 = fastconnectContracts.get(0);		
		assertThat(fastconnectContract1.getStartDate(), Matchers.is(Matchers.oneOf(CONTRACT5_STARTDATE, CONTRACT6_STARTDATE, CONTRACT7_STARTDATE, CONTRACT8_STARTDATE, CONTRACT9_STARTDATE, CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract1.getEndDate(), Matchers.is(Matchers.oneOf(CONTRACT5_ENDDATE, CONTRACT6_ENDDATE, CONTRACT7_ENDDATE, CONTRACT8_ENDDATE, CONTRACT9_ENDDATE, CONTRACT10_ENDDATE)));
		Contract fastconnectContract2 = fastconnectContracts.get(0);		
		assertThat(fastconnectContract2.getStartDate(), Matchers.is(Matchers.oneOf(CONTRACT5_STARTDATE, CONTRACT6_STARTDATE, CONTRACT7_STARTDATE, CONTRACT8_STARTDATE, CONTRACT9_STARTDATE, CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract2.getEndDate(), Matchers.is(Matchers.oneOf(CONTRACT5_ENDDATE, CONTRACT6_ENDDATE, CONTRACT7_ENDDATE, CONTRACT8_ENDDATE, CONTRACT9_ENDDATE, CONTRACT10_ENDDATE)));
		Contract fastconnectContract3 = fastconnectContracts.get(0);		
		assertThat(fastconnectContract3.getStartDate(), Matchers.is(Matchers.oneOf(CONTRACT5_STARTDATE, CONTRACT6_STARTDATE, CONTRACT7_STARTDATE, CONTRACT8_STARTDATE, CONTRACT9_STARTDATE, CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract3.getEndDate(), Matchers.is(Matchers.oneOf(CONTRACT5_ENDDATE, CONTRACT6_ENDDATE, CONTRACT7_ENDDATE, CONTRACT8_ENDDATE, CONTRACT9_ENDDATE, CONTRACT10_ENDDATE)));
		Contract fastconnectContract4 = fastconnectContracts.get(0);		
		assertThat(fastconnectContract4.getStartDate(), Matchers.is(Matchers.oneOf(CONTRACT5_STARTDATE, CONTRACT6_STARTDATE, CONTRACT7_STARTDATE, CONTRACT8_STARTDATE, CONTRACT9_STARTDATE, CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract4.getEndDate(), Matchers.is(Matchers.oneOf(CONTRACT5_ENDDATE, CONTRACT6_ENDDATE, CONTRACT7_ENDDATE, CONTRACT8_ENDDATE, CONTRACT9_ENDDATE, CONTRACT10_ENDDATE)));
		Contract fastconnectContract5 = fastconnectContracts.get(0);		
		assertThat(fastconnectContract5.getStartDate(), Matchers.is(Matchers.oneOf(CONTRACT5_STARTDATE, CONTRACT6_STARTDATE, CONTRACT7_STARTDATE, CONTRACT8_STARTDATE, CONTRACT9_STARTDATE, CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract5.getEndDate(), Matchers.is(Matchers.oneOf(CONTRACT5_ENDDATE, CONTRACT6_ENDDATE, CONTRACT7_ENDDATE, CONTRACT8_ENDDATE, CONTRACT9_ENDDATE, CONTRACT10_ENDDATE)));
		Contract fastconnectContract6 = fastconnectContracts.get(0);		
		assertThat(fastconnectContract6.getStartDate(), Matchers.is(Matchers.oneOf(CONTRACT5_STARTDATE, CONTRACT6_STARTDATE, CONTRACT7_STARTDATE, CONTRACT8_STARTDATE, CONTRACT9_STARTDATE, CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract6.getEndDate(), Matchers.is(Matchers.oneOf(CONTRACT5_ENDDATE, CONTRACT6_ENDDATE, CONTRACT7_ENDDATE, CONTRACT8_ENDDATE, CONTRACT9_ENDDATE, CONTRACT10_ENDDATE)));

		
		/**Validate contract for supplier alphatress-amt */
		Supplier alphatressAmt = supplierRepo.getSupplierByNameAndStaff(ALPHATRESS, amt);		
		assertEquals(1, alphatressAmt.getContracts().size());		
		Contract alphatressAmtContract = alphatressAmt.getContracts().get(0);
		assertEquals(CONTRACT13_STARTDATE, alphatressAmtContract.getStartDate());
		assertEquals(CONTRACT13_ENDDATE, alphatressAmtContract.getEndDate());			
		assertEquals(amt, alphatressAmtContract.getSupplier().getStaff());		
		
		/**Validate contract for supplier alphatress-john*/
		Staff john = staffRepo.getStaffLikeName(JHON_NAME);		
		assertNotNull(john);
		Supplier alphatressJohn = supplierRepo.getSupplierByNameAndStaff(ALPHATRESS, john);
		assertNotNull(alphatressJohn);
		assertEquals(1, alphatressJohn.getContracts().size());
		Contract alphatressJohnContract = alphatressJohn.getContracts().get(0);
		assertEquals(CONTRACT13_STARTDATE, alphatressJohnContract.getStartDate());
		assertEquals(CONTRACT13_ENDDATE, alphatressJohnContract.getEndDate());
		assertEquals(john.getId(), alphatressJohnContract.getSupplier().getStaff().getId());
		
		
		Supplier alterna = supplierRepo.getSupplierByNameAndStaff(ALTERNA, amt);
		assertNotNull(alterna.getContracts());
		assertEquals(2, alterna.getContracts().size());
		Contract alternaContract1 = alterna.getContracts().get(0);
		assertThat(alternaContract1.getStartDate(), Matchers.is(Matchers.oneOf(CONTRACT11_STARTDATE, CONTRACT12_STARTDATE)));
		assertThat(alternaContract1.getEndDate(), Matchers.is(Matchers.oneOf(CONTRACT11_ENDDATE, CONTRACT12_ENDDATE)));
		
//		assertThat(alphatressContract2.getStaffId(), Matchers.both(Matchers.is(Matchers.oneOf(amt, john.getStaffId()))).and(Matchers.not(alphatressContract1StaffId)));
//		assertThat(alphatressContract1.getStaffId(), Matchers.is(Matchers.oneOf(amt, john.getStaffId())));		
		
			
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteSupplier() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Supplier tempSupplier = SupplierTest.insertASupplier(amt, AMESYS, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		supplierRepo.delete(tempSupplier);
		assertNull(supplierRepo.getSupplierByNameAndStaff(AMESYS, amt));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Supplier> suppliers = supplierRepo.findAll();
		assertEquals(6, suppliers.size());
	}
}
