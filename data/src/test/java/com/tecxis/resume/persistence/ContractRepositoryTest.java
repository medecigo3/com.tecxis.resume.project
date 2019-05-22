package com.tecxis.resume.persistence;

import static com.tecxis.resume.StaffTest.insertAStaff;
import static com.tecxis.resume.persistence.ClientRepositoryTest.*;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AXELTIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.EULER_HERMES;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ACCENTURE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALPHATRESS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALTERNA;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.FASTCONNECT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.tecxis.resume.Client;
import com.tecxis.resume.ClientTest;
import com.tecxis.resume.Contract;
import com.tecxis.resume.ContractTest;
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
public class ContractRepositoryTest {
	
	public static final String CONTRACT_TABLE = "Contract";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static final Date CONTRACT1_STARTDATE;
	public static final Date CONTRACT1_ENDDATE;
	public static final Date CONTRACT2_STARTDATE;
	public static final Date CONTRACT2_ENDDATE;
	public static final Date CONTRACT3_STARTDATE;
	public static final Date CONTRACT3_ENDDATE;
	public static final Date CONTRACT4_STARTDATE;
	public static final Date CONTRACT4_ENDDATE;
	public static final Date CONTRACT5_STARTDATE;
	public static final Date CONTRACT5_ENDDATE;
	public static final Date CONTRACT6_STARTDATE;
	public static final Date CONTRACT6_ENDDATE;
	public static final Date CONTRACT7_STARTDATE;
	public static final Date CONTRACT7_ENDDATE;
	public static final Date CONTRACT8_STARTDATE;
	public static final Date CONTRACT8_ENDDATE;
	public static final Date CONTRACT9_STARTDATE;
	public static final Date CONTRACT9_ENDDATE;
	public static final Date CONTRACT10_STARTDATE;
	public static final Date CONTRACT10_ENDDATE;
	public static final Date CONTRACT11_STARTDATE;
	public static final Date CONTRACT11_ENDDATE;
	public static final Date CONTRACT12_STARTDATE;
	public static final Date CONTRACT12_ENDDATE;
	public static final Date CONTRACT13_STARTDATE;
	public static final Date CONTRACT13_ENDDATE = null;
	public static final Date CURRENT_DATE = new Date();
	

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private  ContractRepository contractRepo;

	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	static{
		try {
		CONTRACT1_STARTDATE = sdf.parse("01/01/2007");
		CONTRACT1_ENDDATE = sdf.parse("01/02/2008'");
		CONTRACT2_STARTDATE = sdf.parse("01/03/2008");
		CONTRACT2_ENDDATE = sdf.parse("01/05/2008");
		CONTRACT3_STARTDATE = sdf.parse("01/06/2008");
		CONTRACT3_ENDDATE = sdf.parse("01/07/2008");
		CONTRACT4_STARTDATE = sdf.parse("01/10/2008");
		CONTRACT4_ENDDATE = sdf.parse("01/07/2010");
		CONTRACT5_STARTDATE = sdf.parse("01/07/2010");
		CONTRACT5_ENDDATE = sdf.parse("01/08/2010");
		CONTRACT6_STARTDATE = sdf.parse("01/09/2010");
		CONTRACT6_ENDDATE = sdf.parse("01/10/2010");
		CONTRACT7_STARTDATE = sdf.parse("01/11/2010");
		CONTRACT7_ENDDATE = sdf.parse("01/07/2012");
		CONTRACT8_STARTDATE = sdf.parse("01/07/2012");
		CONTRACT8_ENDDATE = sdf.parse("01/03/2013");
		CONTRACT9_STARTDATE = sdf.parse("01/05/2013");
		CONTRACT9_ENDDATE = sdf.parse("01/10/2013");
		CONTRACT10_STARTDATE = sdf.parse("01/10/2013");
		CONTRACT10_ENDDATE = sdf.parse("01/06/2015");
		CONTRACT11_STARTDATE = sdf.parse("01/06/2015");
		CONTRACT11_ENDDATE = sdf.parse("01/03/2016");
		CONTRACT12_STARTDATE = sdf.parse("01/03/2016");
		CONTRACT12_ENDDATE = sdf.parse("01/08/2016");
		CONTRACT13_STARTDATE = sdf.parse("01/08/2016");
		}
		catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
			
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client accenture = ClientTest.insertAClient(AXELTIS, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(amt, ALTERNA,  entityManager);
		Contract accentureContract = ContractTest.insertAContract(accenture, alterna, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, accentureContract.getId());
	}
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testFindInsertedContract() {
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Supplier alphatress = SupplierTest.insertASupplier(amt, ALPHATRESS, entityManager);
		Contract contractIn = ContractTest.insertAContract(barclays, alphatress, CONTRACT12_STARTDATE, CONTRACT12_ENDDATE, entityManager);
		Contract contractOut = contractRepo.getContractByStartDate(CONTRACT12_STARTDATE);
		assertNotNull(contractOut);
		assertEquals(contractIn, contractOut);		
		contractOut = contractRepo.getContractByEndDate(CONTRACT12_ENDDATE);
		assertNotNull(contractOut);
		assertEquals(contractIn, contractOut);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteContract() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client barclays = ClientTest.insertAClient(EULER_HERMES, entityManager);
		Supplier alphatress = SupplierTest.insertASupplier(amt, ALTERNA, entityManager);
		Contract tempContract = ContractTest.insertAContract(barclays, alphatress, CURRENT_DATE, null,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		contractRepo.delete(tempContract);
		assertNull(contractRepo.getContractByStartDate(CURRENT_DATE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindById() {
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		assertEquals(BARCLAYS, barclays.getName());
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertEquals(AMT_NAME, amt.getName());
		Supplier accenture = supplierRepo.getSupplierByNameAndStaff(ACCENTURE, amt);
		assertEquals(ACCENTURE, accenture.getName());
		
		Contract.ContractPK id = new Contract.ContractPK(1, barclays, accenture);		
		Contract contract = contractRepo.findById(id).get();
		assertNotNull(contract);
		assertEquals(1, contract.getClient().getId());
		assertEquals(1, contract.getSupplier().getSupplierId());
		assertEquals(1, contract.getSupplier().getStaff().getId());
		assertEquals(1, contract.getId());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByClientOrderByContractIdAsc() {
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		
		/**Find contracts*/
		List <Contract> axeltisContracts = contractRepo.findByClientOrderByStartDateAsc(axeltis);
		/**Validate contracts*/
		assertEquals(2, axeltisContracts.size());
		Contract contract1 = axeltisContracts.get(0);
		assertEquals(AXELTIS, contract1.getClient().getName());
		assertEquals(FASTCONNECT,contract1.getSupplier().getName());
		Contract contract2 = axeltisContracts.get(1);
		assertEquals(AXELTIS, contract2.getClient().getName());
		assertEquals(FASTCONNECT,contract2.getSupplier().getName());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindBySupplierOrderByContractIdAsc() {
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier accenture = supplierRepo.getSupplierByNameAndStaff(ACCENTURE, amt);
		
		/**Find contracts*/
		List <Contract> accentureContracts = contractRepo.findBySupplierOrderByStartDateAsc(accenture);
		/**Validate contracts*/
		assertEquals(3, accentureContracts.size());
		Contract contract1 = accentureContracts.get(0);
		assertEquals(BARCLAYS, contract1.getClient().getName());
		assertEquals(ACCENTURE,contract1.getSupplier().getName());
		Contract contract2 = accentureContracts.get(1);
		assertEquals(AGEAS, contract2.getClient().getName());
		assertEquals(ACCENTURE,contract2.getSupplier().getName());
		Contract contract3 = accentureContracts.get(2);
		assertEquals(ClientRepositoryTest.ACCENTURE, contract3.getClient().getName());
		assertEquals(ACCENTURE,contract3.getSupplier().getName());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void findByClientAndSupplierOrderByContractIdAsc() {
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier accenture = supplierRepo.getSupplierByNameAndStaff(ACCENTURE, amt);
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		
		/**Find empty contracts*/
		List <Contract> contractsNotFound = contractRepo.findByClientAndSupplierOrderByStartDateAsc(axeltis, accenture);
		assertEquals(0, contractsNotFound.size());
		
		/**Find contracts*/
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		List <Contract> fastconnectContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(micropole, fastconnect);
		
		/**Validate contracts*/
		assertEquals(1, fastconnectContracts.size());
		Contract contract1 = fastconnectContracts.get(0);
		assertEquals(MICROPOLE, contract1.getClient().getName());
		assertEquals(FASTCONNECT,contract1.getSupplier().getName());		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetContractByStartDate() {
		Contract contract = contractRepo.getContractByStartDate(CONTRACT1_STARTDATE);
		assertEquals(CONTRACT1_STARTDATE, contract.getStartDate());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetContractByEndDate() {
		Contract contract = contractRepo.getContractByEndDate(CONTRACT9_ENDDATE);
		assertEquals(CONTRACT9_ENDDATE, contract.getEndDate());
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Contract> contracts = contractRepo.findAll();
		assertEquals(14, contracts.size());
	}
}
