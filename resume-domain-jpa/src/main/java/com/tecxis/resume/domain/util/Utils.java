package com.tecxis.resume.domain.util;


import com.tecxis.resume.domain.*;
import com.tecxis.resume.domain.id.*;
import com.tecxis.resume.domain.repository.*;
import com.tecxis.resume.domain.util.function.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import static com.tecxis.resume.domain.util.function.AgreementValidator.isServiceValid;
import static com.tecxis.resume.domain.util.function.CityValidator.isNameValid;
import static com.tecxis.resume.domain.util.function.ClientValidator.areContractsValid;
import static com.tecxis.resume.domain.util.function.ClientValidator.isClientNameValid;
import static com.tecxis.resume.domain.util.function.ContractValidator.*;
import static com.tecxis.resume.domain.util.function.ProjectValidator.*;
import static com.tecxis.resume.domain.util.function.StaffValidator.isStaffFirstNameValid;
import static com.tecxis.resume.domain.util.function.StaffValidator.isStaffLastNameValid;
import static com.tecxis.resume.domain.util.function.SupplyContractValidator.isEndDateValid;
import static com.tecxis.resume.domain.util.function.SupplyContractValidator.isStartDateValid;
import static com.tecxis.resume.domain.util.function.TaskValidator.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;

public class Utils {

	private Utils() {
	}

	public static Task insertTask(String desc, Integer priority, EntityManager entityManager) {
		Task task = buildTask(0L, desc, priority);//RES-14, RES-72
		entityManager.persist(task);
		entityManager.flush();
		return task;
	}
	
	public static Task insertTask(String desc, Integer priority, TaskRepository taskRepo) {
		Task task = buildTask(0L, desc, priority);//RES-14, RES-72
		taskRepo.saveAndFlush(task);
		return task;
	}
	
	public static Task buildTask(long taskId, String desc, Integer priority) {
		Task task = new Task();
		task.setId(taskId);//RES-14
		task.setDesc(desc);
		task.setPriority(priority);//RES-72
		return task;
	}
	
	public static void deleteTask(Task task, EntityManager entityManager) {		
		entityManager.remove(task);
		entityManager.flush();
	}
	
	public static void deleteTask(Task task, TaskRepository taskRepo) {
		taskRepo.delete(task);
		taskRepo.flush();
	}

	public static City insertCity(String name, Country country, EntityManager entityManager) {
		City city = new City();
		city.setName(name);				
		city.setCountry(country);
		entityManager.persist(city);
		entityManager.flush();	
		return city;
		
	}
	
	public static City insertCity(String name, Country country, CityRepository cityRepo) {
		City city = new City();
		city.setName(name);				
		city.setCountry(country);
		cityRepo.saveAndFlush(city);		
		return city;
		
	}
	
	public static void deleteCity(City city, EntityManager entityManager) {		
		entityManager.remove(city);
		entityManager.flush();
	}
	
	public static void deleteCity(City city, CityRepository assignmentRepo) {
		assignmentRepo.delete(city);
		assignmentRepo.flush();
	}

	public static Client insertClient(String name, EntityManager entityManager) {
		Client client = buildClient(name, 0L); //RESB-9 fix
		entityManager.persist(client);		
		entityManager.flush();
		return client;
		
	}
	
	public static Client insertClient(String name, ClientRepository clientRepo) {
		Client client = buildClient(name, 0L);//RESB-9 fix
		clientRepo.saveAndFlush(client);		
		return client;
		
	}	
	
	public static Client buildClient(String name, long id) {//RESB-9 fix
		Client client = new Client();
		client.setName(name);
		client.setId(id);
		return client;		
	}
	
	public static void deleteClient(Client client, EntityManager entityManager) {		
		entityManager.remove(client);
		entityManager.flush();
	}
	
	public static void deleteClient(Client client, ClientRepository clientRepo) {
		clientRepo.delete(client);
		clientRepo.flush();
	}

	public static Agreement insertAgreement(Contract contract, Service service, EntityManager entityManager) {
		Agreement agreement = buildAgreement(contract, service);		
		entityManager.persist(agreement);
		entityManager.flush();
		return agreement;
	}
	
	public static Agreement insertAgreement(Contract contract, Service service, AgreementRepository contractServiceAgreementRepo) {
		Agreement agreement = buildAgreement(contract, service);		
		contractServiceAgreementRepo.saveAndFlush(agreement);
		return agreement;
	}
	
	public static Agreement buildAgreement(Contract contract, Service service) {
		return new Agreement(contract, service);	
	}
	
	public static void deleteAgreement(Agreement agreement, EntityManager entityManager) {		
		entityManager.remove(agreement);
		entityManager.flush();
	}
	
	public static void deleteAgreement(Agreement agreement, AgreementRepository contractServiceAgreementRepo) {
		contractServiceAgreementRepo.delete(agreement);
		contractServiceAgreementRepo.flush();
	}

	public static Contract insertContract(Client client, String name, EntityManager entityManager) {
		Contract contract = buildContract(0L,client, name);
		entityManager.persist(contract);
		entityManager.flush();
		return contract;
		
	}
	
	public static Contract insertContract(Client client, String name, ContractRepository contractRepo) {
		Contract contract  = buildContract(0L, client, name);
		contractRepo.saveAndFlush(contract);
		return contract;
		
	}
	
	public static Contract buildContract(long id, Client client, String contractName) {//RES-10
		Contract contract  = new Contract();
		contract.setId(new ContractId(id, client.getId()));
		contract.setName(contractName);
		contract.setClient(client);	
		return contract;
	}
	
	public static void deleteContract(Contract contract, EntityManager entityManager) {		
		entityManager.remove(contract);
		entityManager.flush();
	}
	
	public static void deleteContract(Contract contract, ContractRepository contractRepo) {
		contractRepo.delete(contract);
		contractRepo.flush();
	}

	public static Country insertCountry(String name, EntityManager entityManager) {
		Country country = new Country();
		country.setName(name);
		entityManager.persist(country);		
		entityManager.flush();
		return country;
	}
	
	public static Country insertCountry(String name, CountryRepository countryRepo) {
		Country country = new Country();
		country.setName(name);
		countryRepo.saveAndFlush(country);		
		return country;
	}
	
	public static void deleteCountry(Country country, EntityManager entityManager) {		
		entityManager.remove(country);
		entityManager.flush();
	}
	
	public static void deleteCountry(Country country, CountryRepository countryRepo) {
		countryRepo.delete(country);
		countryRepo.flush();
	}

	public static Course insertCourse(String title,  EntityManager entityManager) {
		Course course = new Course();
		course.setTitle(title);
		entityManager.persist(course);
		entityManager.flush();
		return course;
	}
	
	public static void deleteCourse(Course course, EntityManager entityManager) {		
		entityManager.remove(course);
		entityManager.flush();
	}
	
	public static void deleteCourse(Course course, CourseRepository courseRepo) {
		courseRepo.delete(course);
		courseRepo.flush();
	}
	
	public static Course insertCourse(String title,  CourseRepository courseRepo) {
		Course course = new Course();
		course.setTitle(title);		
		courseRepo.saveAndFlush(course);
		return course;
	}

	public static EmploymentContract insertEmploymentContract(Supplier supplier, Staff staff, EntityManager entityManager){
		EmploymentContract employmentContract = new EmploymentContract(staff, supplier);
		employmentContract.setStartDate(new Date());
		entityManager.persist(employmentContract);
		entityManager.flush();
		return employmentContract;
		
	}
	
	public static EmploymentContract insertEmploymentContract(Supplier supplier, Staff staff, EmploymentContractRepository employmentContractRepository){
		EmploymentContract employmentContract = new EmploymentContract(staff, supplier);
		employmentContract.setStartDate(new Date());		
		employmentContractRepository.saveAndFlush(employmentContract);
		return employmentContract;
		
	}
	
	public static void deleteEmploymentContract(EmploymentContract employmentContract, EntityManager entityManager) {		
		entityManager.remove(employmentContract);
		entityManager.flush();
	}
	
	public static void deleteEmploymentContract(EmploymentContract employmentContract, EmploymentContractRepository employmentContractRepo) {
		employmentContractRepo.delete(employmentContract);
		employmentContractRepo.flush();
	}

	public static Interest insertInterest(String desc, EntityManager entityManager) {
		Interest interest = new Interest();
		interest.setDesc(desc);		
		entityManager.persist(interest);
		entityManager.flush();
		return interest;
	}

	public static Interest insertInterest(String desc, InterestRepository InterestRepo) {
		Interest interest = new Interest();
		interest.setDesc(desc);		
		InterestRepo.saveAndFlush(interest);
		return interest;
	}
	
	public static void deleteInterest(Interest interest, EntityManager entityManager) {		
		entityManager.remove(interest);
		entityManager.flush();
	}
	
	public static void deleteInterest(Interest interest, InterestRepository interestRepo) {
		interestRepo.delete(interest);
		interestRepo.flush();
	}
	
	public static Location insertLocation(City city, Project project, EntityManager entityManager) {
		Location location = new Location(city, project);
//		location.setCity(city);
//		location.setProject(project);
		entityManager.persist(location);
		entityManager.flush();
		return location;
				
	}
	
	public static Location insertLocation(City city, Project project, LocationRepository locationRepo) {		
		Location location = new Location();	
		location.setCity(city);
		location.setProject(project);
		locationRepo.saveAndFlush(location);
		return location;
				
	}
	
	public static void deleteLocation(Location location, EntityManager entityManager) {		
		entityManager.remove(location);
		entityManager.flush();
	}
	
	public static void deleteLocation(Location location, LocationRepository locationRepo) {
		locationRepo.delete(location);
		locationRepo.flush();
	}	

	public static Project insertProject(String name, String version, Client client, List <Assignment> assignments, EntityManager entityManager) {
		Project project = buildProject(0l, name, version, client, assignments, null);//RES-11
		entityManager.persist(project);
		entityManager.flush();
		return project;
	}
	
	public static Project insertProject(String name, String version, Client client, List <Assignment> assignments, ProjectRepository projectRepo) {
		Project project = buildProject(0l, name, version, client, assignments, null);//RES-11
		projectRepo.saveAndFlush(project);
		return project;	
	}
	
	public static Project buildProject(long projectId, String name, String version, Client client, List <Assignment> assignments, List <Location> locations) {//RES-11
		Project project = new Project();
		ProjectId id = new ProjectId(projectId, client.getId());//RES-11
		project.setId(id);
		project.setClient(client);		
		project.setName(name);
		project.setVersion(version);
		if (assignments != null)
			project.setAssignments(assignments);
		if (locations != null)//RES-16 fix
			project.setLocations(locations); 
		return project;
	}
	
	public static void deleteProject(Project project, EntityManager entityManager) {		
		entityManager.remove(project);
		entityManager.flush();
	}
	
	public static void deleteProject(Project project, ProjectRepository projectRepo) {
		projectRepo.delete(project);
		projectRepo.flush();
	}	

	public static Service insertService(String name, EntityManager entityManager) {
		Service service = buildService(name);
		entityManager.persist(service);
		entityManager.flush();
		return service;
	}
	
	public static Service insertService(String name, ServiceRepository serviceRepo) {
		Service service = buildService(name);					
		serviceRepo.saveAndFlush(service);
		return service;
	}
	
	public static Service buildService(String name) {
		Service service = new Service();
		service.setName(name);
		return service;
	}

	public static Supplier buildSupplier(long id, String name){//RES-56
		Supplier supplier = new Supplier ();
		supplier.setId(id);
		supplier.setName(name);
		return supplier;
	}

	public static void deleteService(Service service, EntityManager entityManager) {
		entityManager.remove(service);
		entityManager.flush();
	}
	
	public static void deleteService(Service service, ServiceRepository serviceRepo) {
		serviceRepo.delete(service);
		serviceRepo.flush();
	}	

	public static Skill insertSkill(String name, EntityManager entityManager) {
		Skill skill = new Skill();
		skill.setName(name);
		entityManager.persist(skill);		
		entityManager.flush();
		return skill;
	}
	
	public static void deleteSkill(Skill skill, EntityManager entityManager) {		
		entityManager.remove(skill);
		entityManager.flush();
	}
	
	public static void deleteSkill(Skill skill, SkillRepository skillRepo) {
		skillRepo.delete(skill);
		skillRepo.flush();
	}	
	
	public static Skill insertSkill(String name, SkillRepository skillRepository) {
		Skill skill = new Skill();
		skill.setName(name);			
		skillRepository.saveAndFlush(skill);
		return skill;
	}

	public static Assignment insertAssignment(Project project, Staff staff,  Task task, EntityManager entityManager) {
		Assignment assignment =  buildAssignment(project, staff, task);
		entityManager.persist(assignment);
		entityManager.flush();
		return assignment;
		
	}

	public static Assignment insertAssignment(Project project, Staff staff,  Task task, AssignmentRepository assignmentRepo) {
		Assignment assignment = buildAssignment(project, staff, task);		
		assignmentRepo.saveAndFlush(assignment);
		return assignment;
		
	}
	
	public static Assignment buildAssignment(Project project, Staff staff,  Task task) {
		return new Assignment(project, staff, task);	
	}
	
	public static void deleteAssignment(Assignment assignment, EntityManager entityManager) {		
		entityManager.remove(assignment);
		entityManager.flush();
	}
	
	public static void deleteAssignment(Assignment assignment, AssignmentRepository assignmentRepo) {
		assignmentRepo.delete(assignment);
		assignmentRepo.flush();
	}	
	
	public static StaffSkill insertStaffSkill(Staff staff, Skill skill, EntityManager entityManager) {
		StaffSkill staffSkill = new StaffSkill(skill, staff);
		entityManager.persist(staffSkill);
		entityManager.flush();
		return staffSkill;
		
	}

	public static StaffSkill insertStaffSkill(Staff staff, Skill skill, StaffSkillRepository StaffSkillRepository) {
		StaffSkill staffSkill = new StaffSkill(skill, staff);		
		StaffSkillRepository.saveAndFlush(staffSkill);
		return staffSkill;
		
	}
	
	public static void deleteStaffSkill(StaffSkill staffSkill, EntityManager entityManager) {		
		entityManager.remove(staffSkill);
		entityManager.flush();
	}
	
	public static void deleteStaffSkill(StaffSkill StaffSkill, StaffSkillRepository StaffSkillRepo) {
		StaffSkillRepo.delete(StaffSkill);
		StaffSkillRepo.flush();
	}
	
	public static Staff insertStaff(String firstName, String lastName, Date birthDate,  EntityManager entityManager) {
		Staff staff = buildStaff(0L, firstName, lastName, birthDate);
		entityManager.persist(staff);
		entityManager.flush();
		return staff;
		
	}
	
	public static void deleteStaff(Staff staff, EntityManager entityManager) {		
		entityManager.remove(staff);
		entityManager.flush();
	}
	
	public static void deleteStaff(Staff StaffSkill, StaffRepository StaffRepo) {
		StaffRepo.delete(StaffSkill);
		StaffRepo.flush();
	}
	
	public static Staff insertStaff(String firstName, String lastName, Date birthDate,  StaffRepository staffRepo) {
		Staff staff = buildStaff(0L, firstName, lastName, birthDate);//RES-13
		staffRepo.saveAndFlush(staff);
		return staff;
		
	}
	
	public static Staff buildStaff(long id, String firstName, String lastName, Date birthDate) {//RES-13
		Staff staff = new Staff();
		staff.setId(id);
		staff.setFirstName(firstName);
		staff.setLastName(lastName);
		staff.setBirthDate(birthDate);
		return staff;
	}

	public static Supplier insertSupplier(String name, EntityManager entityManager) {
		Supplier supplier = new Supplier();
		supplier.setName(name);
		entityManager.persist(supplier);
		entityManager.flush();
		return supplier;
	}
	
	public static Supplier insertSupplier(String name, SupplierRepository supplierRepo) {
		Supplier supplier = new Supplier();
		supplier.setName(name);		
		supplierRepo.saveAndFlush(supplier);
		return supplier;
	}
	
	public static void deleteSupplier(Supplier supplier, EntityManager entityManager) {		
		entityManager.remove(supplier);
		entityManager.flush();
	}
	
	public static void deleteSupplier(Supplier supplier, SupplierRepository supplierRepo) {
		supplierRepo.delete(supplier);
		supplierRepo.flush();
	}

	public static SupplyContract insertSupplyContract(Supplier supplier, Contract contract, Staff staff, Date startDate, Date endDate, EntityManager entityManager){
		SupplyContract supplyContract = new SupplyContract(supplier, contract, staff);
		supplyContract.setStartDate(startDate);
		supplyContract.setEndDate(endDate);		
		entityManager.persist(supplyContract);
		entityManager.flush();
		return supplyContract;
		
	}
	
	public static SupplyContract insertSupplyContract(Supplier supplier, Contract contract, Staff staff, Date startDate, Date endDate, SupplyContractRepository supplyContractRepository){
		SupplyContract supplyContract = new SupplyContract(supplier, contract, staff);
		supplyContract.setStartDate(startDate);
		supplyContract.setEndDate(endDate);				
		supplyContractRepository.saveAndFlush(supplyContract);
		return supplyContract;
		
	}
	
	public static void deleteSupplyContract(SupplyContract supplyContract, EntityManager entityManager) {		
		entityManager.remove(supplyContract);
		entityManager.flush();
	}
	
	public static void deleteSupplyContract(SupplyContract supplyContract, SupplyContractRepository supplierContractRepo) {
		supplierContractRepo.delete(supplyContract);
		supplierContractRepo.flush();
	}
	
	public static Enrolment insertEnrolment(Staff staff, Course course, EnrolmentRepository enrolmentRepo) {
		Enrolment enrolment = new Enrolment(staff, course);
		enrolmentRepo.saveAndFlush(enrolment);
		return enrolment;		
	}
	
	public static Enrolment insertEnrolment(Staff staff, Course course, EntityManager entityManager) {
		Enrolment enrolment = new Enrolment(staff, course);		
		entityManager.persist(enrolment);
		entityManager.flush();
		return enrolment;		
	}
	
	public static void deleteEnrolment(Enrolment enrolment, EntityManager entityManager) {		
		entityManager.remove(enrolment);
		entityManager.flush();
	}
	
	public static void deleteEnrolment(Enrolment enrolment, EnrolmentRepository enrolmentRepo) {
		enrolmentRepo.delete(enrolment);
		enrolmentRepo.flush();
	}

	public static ValidationResult isAgreementValid(Agreement agreement, String contractName, String serviceName) {
		if(AgreementValidator.isContractValid(contractName).apply(agreement) != SUCCESS)//RES-67
			return AgreementValidator.isContractValid(contractName).apply(agreement);
		if(isServiceValid(serviceName).apply(agreement) != SUCCESS)//RES-67
			return isServiceValid(serviceName).apply(agreement);
		return SUCCESS;
	}
	
	public static void insertAgreementInJpa(JPATransactionVoidFunction <AgreementRepository> insertAgreementFunction, AgreementRepository  repository, JdbcTemplate jdbcTemplate) {
		insertAgreementFunction.beforeTransactionCompletion(SchemaUtils::testStateBefore_Agreement_Insert, jdbcTemplate);
		insertAgreementFunction.accept(repository);
		insertAgreementFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_Agreement_Insert, jdbcTemplate);
	
	}
	
	public static void insertAgreementInJpa(JPATransactionVoidFunction <EntityManager> insertAgreementFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		insertAgreementFunction.beforeTransactionCompletion(SchemaUtils::testStateBefore_Agreement_Insert, jdbcTemplate);
		insertAgreementFunction.accept(entityManager);
		insertAgreementFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_Agreement_Insert, jdbcTemplate);
	
	}
	
	public static void deleteAgreementInJpa(JPATransactionVoidFunction <EntityManager> deleteAgreementFunction,EntityManager entityManager, JdbcTemplate jdbcTemplate){
		deleteAgreementFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteAgreementFunction.accept(entityManager);
		deleteAgreementFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_AgreementAxeltisFastconnect_Delete, jdbcTemplate);
	}
	
	public static void deleteAgreementInJpa(JPATransactionVoidFunction <AgreementRepository> deleteAgreementFunction, AgreementRepository agreementRepository, JdbcTemplate jdbcTemplate){
		deleteAgreementFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteAgreementFunction.accept(agreementRepository);
		deleteAgreementFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_AgreementAxeltisFastconnect_Delete, jdbcTemplate);
	}
	
	public static void insertAssignmentInJpa(JPATransactionVoidFunction <AssignmentRepository> insertAssignmentFunction, AssignmentRepository  assignmentRepo, JdbcTemplate jdbcTemplate) {
		insertAssignmentFunction.beforeTransactionCompletion(SchemaUtils::testStateBefore_Assignment_Insert, jdbcTemplate);
		insertAssignmentFunction.accept(assignmentRepo);
		insertAssignmentFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_Assignment_Insert, jdbcTemplate);
	
	}
	
	public static void insertAssignmentInJpa(JPATransactionVoidFunction <EntityManager> insertAssignmentFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		insertAssignmentFunction.beforeTransactionCompletion(SchemaUtils::testStateBefore_Assignment_Insert, jdbcTemplate);
		insertAssignmentFunction.accept(entityManager);
		insertAssignmentFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_Assignment_Insert, jdbcTemplate);
	
	}
	
	public static void deleteAssignmentInJpa(JPATransactionVoidFunction <EntityManager> deleteAssignmentFunction,EntityManager entityManager, JdbcTemplate jdbcTemplate){
		deleteAssignmentFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteAssignmentFunction.accept(entityManager);
		deleteAssignmentFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_AssignmentParcoursAmtTask14_Delete, jdbcTemplate);
	}
	
	public static void deleteAssignmentInJpa(JPATransactionVoidFunction <AssignmentRepository> deleteAssignmentFunction, AssignmentRepository agreementRepository, JdbcTemplate jdbcTemplate){
		deleteAssignmentFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteAssignmentFunction.accept(agreementRepository);
		deleteAssignmentFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_AssignmentParcoursAmtTask14_Delete, jdbcTemplate);
	}
	
	public static void unscheduleDeleteAssignmentInJpa(JPATransactionVoidFunction <EntityManager> unDeleteAssignmentFunction,EntityManager entityManager, JdbcTemplate jdbcTemplate){
		unDeleteAssignmentFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		unDeleteAssignmentFunction.accept(entityManager);
		unDeleteAssignmentFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
	}
	
	public static void update_AssignmentParcoursAmt_With_NewAssociations_InJpa(JPATransactionVoidFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
	}
	
	public static void update_AssignmentParcoursAmt_With_NewAssociations_InJpa(JPATransactionVoidFunction <AssignmentRepository> setAssignmentAssociationFunction, AssignmentRepository assignmentRepo, JdbcTemplate jdbcTemplate) {
		setAssignmentAssociationFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setAssignmentAssociationFunction.accept(assignmentRepo);
		setAssignmentAssociationFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
	}	
	
	public static ValidationResult isAssignmentValid(Assignment assignment, String projectName, String projectVersion, String clientName, String staffFirstName, String staffLastName, String taskDesc) {
		if (isProjectNameValid(projectName).apply(assignment.getProject()) != SUCCESS)//RES-66
			return isProjectNameValid(projectName).apply(assignment.getProject());
		if (isProjectVersionValid(projectVersion).apply(assignment.getProject()) != SUCCESS)//RES-66
			return isProjectVersionValid(projectVersion).apply(assignment.getProject());
		if (isProjectClientValid(clientName).apply(assignment.getProject()) != SUCCESS)//RES-66
			return isProjectClientValid(clientName).apply(assignment.getProject());
		if (isStaffFirstNameValid(staffFirstName).apply(assignment.getStaff()) != SUCCESS)//RES-66
			return isStaffFirstNameValid(staffFirstName).apply(assignment.getStaff());
		if (isStaffLastNameValid(staffLastName).apply(assignment.getStaff()) != SUCCESS)//RES-66
			return isStaffLastNameValid(staffLastName).apply(assignment.getStaff());
		if (isTaskDescValid(taskDesc).apply(assignment.getTask()) != SUCCESS)//RES-66, RES-57
			return isTaskDescValid(taskDesc).apply(assignment.getTask());//RES-57
		return SUCCESS;		
	}
	
	public static void insertCityInJpa(JPATransactionVoidFunction <EntityManager> insertCityFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		insertCityFunction.beforeTransactionCompletion(SchemaUtils::testStateBefore_City_Insert, jdbcTemplate);
		insertCityFunction.accept(entityManager);
		insertCityFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_City_Insert, jdbcTemplate);
	}
	
	public static void insertCityInJpa(JPATransactionVoidFunction <CityRepository> insertCityFunction, CityRepository cityRepo, JdbcTemplate jdbcTemplate) {
		insertCityFunction.beforeTransactionCompletion(SchemaUtils::testStateBefore_City_Insert, jdbcTemplate);
		insertCityFunction.accept(cityRepo);
		insertCityFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_City_Insert, jdbcTemplate);
	}
	
	public static ValidationResult isCityValid(City city, String cityName, String countryName, final List<Location> locations) {
		if (isNameValid(cityName).apply(city) != SUCCESS)//RES-65
			return isNameValid(cityName).apply(city);
		if (CityValidator.isCountryValid(countryName).apply(city) != SUCCESS)//RES-65
			return CityValidator.isCountryValid(countryName).apply(city);
		if(CityValidator.areLocationsValid(locations).apply(city) != SUCCESS)//RES-65
			return CityValidator.areLocationsValid(locations).apply(city);
		return SUCCESS;		
	}
	
	public static void update_CityLondon_With_Country_InJpa(JPATransactionVoidFunction <EntityManager> setLondonInFranceFunction, EntityManager entityManager, JdbcTemplate jdbcTemplateProxy) {
		setLondonInFranceFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplateProxy);
		setLondonInFranceFunction.accept(entityManager);
		setLondonInFranceFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_LondonCity_Update, jdbcTemplateProxy);
		
	}
	
	public static void update_CityBrussles_With_Country_InJpa(JPATransactionVoidFunction <CityRepository> setBrusselsInFranceFunction, CityRepository cityRepo, JdbcTemplate jdbcTemplateProxy) {
		setBrusselsInFranceFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplateProxy);
		setBrusselsInFranceFunction.accept(cityRepo);
		setBrusselsInFranceFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_CityBrussels_Update, jdbcTemplateProxy);
		
	}
	
	public static ValidationResult isCountryValid(Country country, String countryName, final List<City> cities) {
		if (CountryValidator.areCitiesValid(cities).apply(country) != SUCCESS)//RES-62
			return CountryValidator.areCitiesValid(cities).apply(country) ;
		if (CountryValidator.isNameValid(countryName).apply(country) != SUCCESS)//RES-62
			return CountryValidator.isNameValid(countryName).apply(country);
		return SUCCESS;		
	}
	public static long insertClientInJpa(JPATransactionFunction <EntityManager, Long> insertClientFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		insertClientFunction.beforeTransactionCompletion(SchemaUtils::testStateBefore_Client_Insert, jdbcTemplate);
		Long clientId = insertClientFunction.apply(entityManager);
		insertClientFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_Client_Insert, jdbcTemplate);
		return clientId;
	}
	
	public static long insertClientInJpa(JPATransactionFunction <ClientRepository, Long> insertClientFunction, ClientRepository clientRepo, JdbcTemplate jdbcTemplate) {
		insertClientFunction.beforeTransactionCompletion(SchemaUtils::testStateBefore_Client_Insert, jdbcTemplate);
		Long clientId = insertClientFunction.apply(clientRepo);
		insertClientFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_Client_Insert, jdbcTemplate);
		return clientId;
	}
	
	public static ValidationResult isClientValid(Client client, String clientName,@Null List <Contract> contracts) {
		if (isClientNameValid(clientName).apply(client) != SUCCESS)//RES-64
			return isClientNameValid(clientName).apply(client);
		if (areContractsValid(contracts).apply(client) != SUCCESS)//RES-64
			return areContractsValid(contracts).apply(client);
		return SUCCESS;		
	}
	
	public static void deleteCityInJpa(JPATransactionVoidFunction <EntityManager> deleteCityFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate){
		deleteCityFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteCityFunction.accept(entityManager);
		deleteCityFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_LondonCity_Delete, jdbcTemplate);
	}
	
	public static void deleteCityInJpa(JPATransactionVoidFunction <CityRepository> deleteCityFunction, CityRepository cityRepo, JdbcTemplate jdbcTemplate){
		deleteCityFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteCityFunction.accept(cityRepo);
		deleteCityFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_LondonCity_Delete, jdbcTemplate);
	}

	public static void deleteClientInJpa(JPATransactionVoidFunction<EntityManager> deleteClientFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		deleteClientFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteClientFunction.accept(entityManager);
		deleteClientFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ClientAxeltis_Delete, jdbcTemplate);
	}
	
	public static void deleteClientInJpa(JPATransactionVoidFunction<ClientRepository> deleteClientFunction, ClientRepository clientRepo, JdbcTemplate jdbcTemplate) {
		deleteClientFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteClientFunction.accept(clientRepo);
		deleteClientFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ClientAxeltis_Delete, jdbcTemplate);
	}

	public static void update_ContractAmesysSagem_With_Client_InJpa(JPATransactionVoidFunction <EntityManager> deleteContractFunction, JPATransactionVoidFunction <EntityManager> setContractClientFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		deleteContractFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		Consumer <EntityManager> deleteAndSetFunction = deleteContractFunction.andThen(setContractClientFunction);
		deleteAndSetFunction.accept(entityManager);
		setContractClientFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_SagemContract_Client_Update, jdbcTemplate);
	}

	public static void update_ContractAmesysSagem_With_Client_InJpa(JPATransactionVoidFunction <ContractRepository> deleteContractFunction, JPATransactionVoidFunction <ContractRepository> setContractClientFunction, ContractRepository contractRepo, JdbcTemplate jdbcTemplate) {
		deleteContractFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		Consumer <ContractRepository> deleteAndSetFunction = deleteContractFunction.andThen(setContractClientFunction);
		deleteAndSetFunction.accept(contractRepo);			
		setContractClientFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_SagemContract_Client_Update, jdbcTemplate);
	}
	
	public static ValidationResult isContractValid(Contract contract, long contractId, Client client, List<Agreement> agreements, List<SupplyContract> supplyContracts) {//RES-56
		if(isContractIdValid(contractId).apply(contract) != SUCCESS)//RES-63
			return isContractIdValid(contractId).apply(contract) ;
		if(ContractValidator.isClientValid(client).apply(contract) != SUCCESS)//RES-63
			return ContractValidator.isClientValid(client).apply(contract) ;
		if(areAgreementsValid(agreements).apply(contract) != SUCCESS)//RES-63
			return areAgreementsValid(agreements).apply(contract) ;
		if(areSupplyContractsValid(supplyContracts).apply(contract) != SUCCESS)//RES-63
			return areSupplyContractsValid(supplyContracts).apply(contract) ;
		return SUCCESS;
	}
	
	public static ValidationResult isSupplyContractValid(SupplyContract supplyContract, Supplier supplier, Contract contract,@NotNull Date startDate,@Null Date endDate) {
		if(SupplyContractValidator.isSupplyContractValid(supplier, contract).apply(supplyContract) != SUCCESS)//RES-60
			return SupplyContractValidator.isSupplyContractValid(supplier, contract).apply(supplyContract);
		if (isStartDateValid(startDate).apply(supplyContract) != SUCCESS)//RES-60
			return isStartDateValid(startDate).apply(supplyContract);
		if (isEndDateValid(endDate).apply(supplyContract) != SUCCESS)//RES-60
			return isEndDateValid(endDate).apply(supplyContract);
		return SUCCESS;
	}
	
	public static ValidationResult isProjectValid(Project project, String name, String version, List<Location> locations, Client client, List <Assignment> assignments) {
		if (ProjectValidator.isProjectNameValid(name).apply(project) != SUCCESS )//RES-61
			return ProjectValidator.isProjectNameValid(name).apply(project);
		if (ProjectValidator.isProjectVersionValid(version).apply(project) != SUCCESS)//RES-61
			return ProjectValidator.isProjectVersionValid(version).apply(project) ;
		if (ProjectValidator.isProjectClientValid(client).apply(project) != SUCCESS)//RES-61
			return ProjectValidator.isProjectClientValid(client).apply(project);
		if (ProjectValidator.areProjectAssignmentsValid(assignments).apply(project) != SUCCESS)//RES-61
			return ProjectValidator.areProjectAssignmentsValid(assignments).apply(project);
		if (ProjectValidator.areProjectLocationsValid(locations).apply(project) != SUCCESS)//RES-61
			return ProjectValidator.areProjectLocationsValid(locations).apply(project);
		return SUCCESS;
		
	}
	
	public static CityId buildCityId(long cityId, long countryId) {//TODO RES-68
		return new CityId(cityId, countryId);	
	}
	
	public static ProjectId buildProjectId(long clientId, long projectId) {
		return  new ProjectId(clientId, projectId);	
	}
	
	public static LocationId buildLocationId(CityId cityId, ProjectId projectId) {
		return new LocationId(cityId, projectId);
	}
	
	public static Location buildLocation(City city, Project project) {
		return new Location (city, project);
	}
	
	public static City buildCity(CityId cityId, String name) {
		City city = new City (cityId);
		city.setName(name);
		return city;
	}
	
	public static void deleteParisMorningstarV1AxeltisLocationInJpa(JPATransactionVoidFunction<EntityManager> deleteLocationFunction, EntityManager em, JdbcTemplate jdbcTemplate) {
		deleteLocationFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteLocationFunction.accept(em);
		deleteLocationFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_MorningstarV1Project_Location_Delete_ByParisCity, jdbcTemplate);
	}

	public static void deleteParisMorningstarV1AxeltisLocationInJpa(JPATransactionVoidBiFunction<ProjectRepository, CityRepository> deleteLocationFunction, ProjectRepository projectRepo, CityRepository cityRepo, JdbcTemplate jdbcTemplate) {//RES-61
		deleteLocationFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteLocationFunction.accept(projectRepo, cityRepo);
		deleteLocationFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_MorningstarV1Project_Location_Delete_ByParisCity, jdbcTemplate);
	}

	public static void update_CityParis_With_Locations_InJpa(JPATransactionVoidFunction <EntityManager> setLocationFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		setLocationFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setLocationFunction.accept(entityManager);
		setLocationFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_MorningstarV1Project_Locations_Delete, jdbcTemplate);
		
	}
	
	public static void update_CityParis_With_Locations_InJpa(JPATransactionVoidBiFunction <CityRepository, ProjectRepository> setLocationFunction, CityRepository cityRepo, ProjectRepository projectRepo, JdbcTemplate jdbcTemplate) {
		setLocationFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setLocationFunction.accept(cityRepo, projectRepo);
		setLocationFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_MorningstarV1Project_Locations_Delete, jdbcTemplate);
		
	}
	
	public static void update_CityParis_With_Locations_InJpa(JPATransactionVoidFunction <LocationRepository> setLocationFunction, LocationRepository locationRepo, JdbcTemplate jdbcTemplate) {
		setLocationFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setLocationFunction.accept(locationRepo);
		setLocationFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_MorningstarV1Project_Locations_Delete, jdbcTemplate);
		
	}
	
	public static void update_CityParis_With_NullLocations_InJpa(JPATransactionVoidFunction <EntityManager> setCityWithNullLocationFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		setCityWithNullLocationFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setCityWithNullLocationFunction.accept(entityManager);
		setCityWithNullLocationFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_CityParis_Update_NullLocations, jdbcTemplate);
		
	}
	
	public static void update_CityParis_With_NullLocations_InJpa(JPATransactionVoidFunction <CityRepository> setCityWithNullLocationFunction, CityRepository cityRepo, JdbcTemplate jdbcTemplate) {
		setCityWithNullLocationFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setCityWithNullLocationFunction.accept(cityRepo);
		setCityWithNullLocationFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_CityParis_Update_NullLocations, jdbcTemplate);
		
	}

	public static void update_ContractArval_With_Agreements_InJpa(JPATransactionVoidFunction<EntityManager> setContractAgreementFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		setContractAgreementFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setContractAgreementFunction.accept(entityManager);
		setContractAgreementFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ContractArval_Update_Agreements, jdbcTemplate);
		
	}
	
	public static void update_ContractArval_With_Agreements_InJpa(JPATransactionVoidFunction<ContractRepository> setContractAgreementFunction, ContractRepository contractRepo, JdbcTemplate jdbcTemplate) {
		setContractAgreementFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setContractAgreementFunction.accept(contractRepo);
		setContractAgreementFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ContractArval_Update_Agreements, jdbcTemplate);
		
	}
	
	public static void update_ContractArval_With_NullAgreements_InJpa(JPATransactionVoidFunction<EntityManager> setContractAgreementsWithNullFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		setContractAgreementsWithNullFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setContractAgreementsWithNullFunction.accept(entityManager);
		setContractAgreementsWithNullFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ContractArval_Update_NullAgreements, jdbcTemplate);
		
	}
	
	public static void update_ContractArval_With_NullAgreements_InJpa(JPATransactionVoidFunction<ContractRepository> setContractAgreementsWithNullFunction, ContractRepository contractRepo, JdbcTemplate jdbcTemplate) {
		setContractAgreementsWithNullFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setContractAgreementsWithNullFunction.accept(contractRepo);
		setContractAgreementsWithNullFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ContractArval_Update_NullAgreements, jdbcTemplate);
		
	}
	
	public static void update_AgreementAxeltisFastconnect_With_Service_InJpa(JPATransactionVoidFunction <EntityManager> setAgreementServiceFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		setAgreementServiceFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setAgreementServiceFunction.accept(entityManager);
		setAgreementServiceFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
	}
	
	public static void update_AgreementAxeltisFastconnect_With_Service_InJpa(JPATransactionVoidFunction <AgreementRepository> setAgreementServiceFunction, AgreementRepository agreementRepo, JdbcTemplate jdbcTemplate) {
		setAgreementServiceFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setAgreementServiceFunction.accept(agreementRepo);
		setAgreementServiceFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
	}
	
	public static void update_AgreementAxeltisFastConnect_With_Contract_InJpa(JPATransactionVoidFunction <EntityManager> updateAgreementServiceFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		updateAgreementServiceFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		updateAgreementServiceFunction.accept(entityManager);
		updateAgreementServiceFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
	}
	
	public static void update_AgreementAxeltisFastConnect_With_Contract_InJpa(JPATransactionVoidFunction <AgreementRepository> setAgreementServiceFunction, AgreementRepository agreementRepo, JdbcTemplate jdbcTemplate) {
		setAgreementServiceFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setAgreementServiceFunction.accept(agreementRepo);
		setAgreementServiceFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
	}

	public static void update_ContractAmesysSagem_With_SupplyContracts_InJpa(JPATransactionVoidFunction <EntityManager> setContractSupplyContractsFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		setContractSupplyContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setContractSupplyContractsFunction.accept(entityManager);
		setContractSupplyContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ContractAmesysSagem_Update_SupplyContracts, jdbcTemplate);
		
	}
	
	public static void update_ContractAmesysSagem_With_SupplyContracts_InJpa(JPATransactionVoidFunction <ContractRepository> setContractSupplyContractsFunction, ContractRepository contractRepo, JdbcTemplate jdbcTemplate) {
		setContractSupplyContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setContractSupplyContractsFunction.accept(contractRepo);
		setContractSupplyContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ContractAmesysSagem_Update_SupplyContracts, jdbcTemplate);
		
	}
	
	public static void update_ContractAmesysSagem_With_NullSupplyContracts_InJpa(JPATransactionVoidFunction <EntityManager> setContractSupplyContractsWithNullFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		setContractSupplyContractsWithNullFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setContractSupplyContractsWithNullFunction.accept(entityManager);
		setContractSupplyContractsWithNullFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ContractAmesysSagem_Update_NullSupplyContracts, jdbcTemplate);
		
	}
	
	public static void update_ContractAmesysSagem_With_NullSupplyContracts_InJpa(JPATransactionVoidFunction <ContractRepository> setContractSupplyContractsWithNullFunction, ContractRepository contractRepo, JdbcTemplate jdbcTemplate) {
		setContractSupplyContractsWithNullFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setContractSupplyContractsWithNullFunction.accept(contractRepo);
		setContractSupplyContractsWithNullFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ContractAmesysSagem_Update_NullSupplyContracts, jdbcTemplate);
		
	}
	
	public static void update_ProjectAdirV1_With_Assignments_InJpa(JPATransactionVoidFunction <EntityManager>  deleteLocationsFunction, JPATransactionVoidFunction <EntityManager>  deleteAssignmentsFunction,  JPATransactionVoidFunction <EntityManager>  deleteProjectFunction, JPATransactionVoidFunction <EntityManager> setProjectAssignmentsFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		/**Delete locations*/
		deleteLocationsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteLocationsFunction.accept(entityManager);
		/**Delete assignments*/		
		deleteAssignmentsFunction.accept(entityManager);
		/**delete Project*/
		deleteProjectFunction.accept(entityManager);
		/**New Project with previous Project ID with new assignments */
		setProjectAssignmentsFunction.accept(entityManager);
		setProjectAssignmentsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ProjectAdirV1_Update_Assignments, jdbcTemplate);
		
	}
	
	public static void update_ProjectAdirV1_With_Assignments_InJpa(JPATransactionVoidBiFunction <LocationRepository, EntityManager>  deleteLocationsFunction, JPATransactionVoidBiFunction <AssignmentRepository, EntityManager>  deleteAssignmentsFunction, JPATransactionVoidBiFunction <ProjectRepository, EntityManager>  deleteProjectFunction,  JPATransactionVoidBiFunction <ProjectRepository, AssignmentRepository>   setProjectAssignmentsFunction, ProjectRepository projectRepo, LocationRepository locationRepo, AssignmentRepository assignmentRepo, EntityManager em, JdbcTemplate jdbcTemplate) {
		/**Delete locations*/
		deleteLocationsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		deleteLocationsFunction.accept(locationRepo,em);		
		/**Delete assignments*/		
		deleteAssignmentsFunction.accept(assignmentRepo, em);		
		/**delete Project*/		
		deleteProjectFunction.accept(projectRepo, em);		
		/**New Project with previous Project ID with new assignments */		
		setProjectAssignmentsFunction.accept(projectRepo, assignmentRepo);
		setProjectAssignmentsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ProjectAdirV1_Update_Assignments, jdbcTemplate);
		
	}
	
	public static void update_ProjectAdirV1_With_NullAssignments_InJpa(JPATransactionVoidFunction <EntityManager> setProjectAssignmentsFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		/**Project -> Assignments assoc. not set to remove orphans; no change in state*/
		setProjectAssignmentsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setProjectAssignmentsFunction.accept(entityManager);
		setProjectAssignmentsFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		
	}
	
	public static void update_ProjectAdirV1_With_NullAssignments_InJpa(JPATransactionVoidFunction <EntityManager> clearEMFunction, JPATransactionVoidBiFunction <ProjectRepository, AssignmentRepository>   setProjectAssignmentsFunction, JPATransactionVoidFunction <EntityManager> flushEMFunction, ProjectRepository projectRepo, AssignmentRepository assignmentRepo, EntityManager em, JdbcTemplate jdbcTemplate) {
		/**Clear EM*/
		clearEMFunction.accept(em);
		/**Project -> Assignments assoc. not set to remove orphans; no change in state*/		
		setProjectAssignmentsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setProjectAssignmentsFunction.accept(projectRepo, assignmentRepo);
		setProjectAssignmentsFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		/**Flush EM*/
		flushEMFunction.accept(em);
		
	}

	public static Country buildCountry(long countryId, String name) {
		Country country = new Country();
		country.setName(name);
		country.setId(countryId);
		return country;
	}

	public static void update_ClientAgeas_With_Contracts_InJpa(JPATransactionVoidFunction <EntityManager> setContractsFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate){//RES-42
		setContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		/**Set client with existing contracts and remove orphans*/
		setContractsFunction.accept(entityManager);
		setContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ClientAgeas_Update_Contract, jdbcTemplate);
	}
	
	public static void update_ClientAgeas_With_NullContracts_InJpa(JPATransactionVoidFunction <EntityManager> setContractsFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate){//RES-42
		setContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		/**Set client with existing contracts and remove orphans*/
		setContractsFunction.accept(entityManager);
		setContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ClientAgeas_Update_NullContracts, jdbcTemplate);
	}

	public static void update_ClientAgeas_With_Contracts_InJpa(JPATransactionVoidBiFunction <ClientRepository, ContractRepository> setContractsFunction, ClientRepository clientRepo, ContractRepository contractRepo, JdbcTemplate jdbcTemplate){//RES-42
		setContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		/**Set client with existing contracts and remove orphans*/
		setContractsFunction.accept(clientRepo, contractRepo);
		setContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ClientAgeas_Update_Contract, jdbcTemplate);
	}
	
	public static void update_ClientAgeas_With_NullContracts_InJpa(JPATransactionVoidBiFunction <ClientRepository, ContractRepository> setContractsFunction, ClientRepository clientRepo, ContractRepository contractRepo, JdbcTemplate jdbcTemplate){//RES-42
		setContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		/**Set client with existing contracts and remove orphans*/
		setContractsFunction.accept(clientRepo, contractRepo);
		setContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ClientAgeas_Update_NullContracts, jdbcTemplate);
	}


	public static void update_CountryFrance_With_Cities_InJpa(JPATransactionVoidFunction <EntityManager> createNewCitiesFunction, JPATransactionVoidFunction <EntityManager> setCountryCitiesFunction, EntityManager entityManager, JdbcTemplate jdbcTemplateProxy) {
		setCountryCitiesFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplateProxy);
		/**Create new Cities*/
		createNewCitiesFunction.accept(entityManager);
		/**Set Country with new Cities*/
		setCountryCitiesFunction.accept(entityManager);
		setCountryCitiesFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_CountryFrance_Update_Cities, jdbcTemplateProxy);
	}

	public static void update_CountryFrance_With_Cities_InJpa(JPATransactionVoidFunction <CityRepository> createNewCitiesFunction, JPATransactionVoidFunction <CountryRepository> setCountryCitiesFunction, CityRepository cityRepo, CountryRepository countryRepo, JdbcTemplate jdbcTemplateProxy) {
		setCountryCitiesFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplateProxy);
		/**Create new Cities*/
		createNewCitiesFunction.accept(cityRepo);
		/**Set Country with new Cities*/
		setCountryCitiesFunction.accept(countryRepo);
		setCountryCitiesFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_CountryFrance_Update_Cities, jdbcTemplateProxy);
	}

	public static void update_CountryFrance_With_NullCities_InJpa(JPATransactionVoidFunction <EntityManager> createNewCitiesFunction, JPATransactionVoidFunction <EntityManager> setCountryCitiesFunction, EntityManager entityManager, JdbcTemplate jdbcTemplateProxy) {
		setCountryCitiesFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplateProxy);
		/**Create new Cities*/
		createNewCitiesFunction.accept(entityManager);
		/**Set Country with new Cities*/
		setCountryCitiesFunction.accept(entityManager);
		setCountryCitiesFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplateProxy);
	}

	public static void update_CountryFrance_With_NullCities_InJpa(JPATransactionVoidFunction <CountryRepository> setCountryCitiesFunction, CountryRepository countryRepo, JdbcTemplate jdbcTemplateProxy) {
		setCountryCitiesFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplateProxy);
		/**Set Country with new Cities*/
		setCountryCitiesFunction.accept(countryRepo);
		setCountryCitiesFunction.afterTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplateProxy);
	}

	public static void update_ProjectSherpaV1_With_Cities_InJpa(JPATransactionVoidFunction <EntityManager> setProjectCitiesFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate){
		/**Project -> Cities assoc.*/
		setProjectCitiesFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setProjectCitiesFunction.accept(entityManager);
		setProjectCitiesFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ProjectSherpaV1_Update_Cities, jdbcTemplate);
	}

	public static void update_ProjectSherpaV1_With_Cities_InJpa(JPATransactionVoidFunction <ProjectRepository> setProjectCitiesFunction, ProjectRepository projectRepo, JdbcTemplate jdbcTemplate){
		/**Project -> Cities assoc.*/
		setProjectCitiesFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setProjectCitiesFunction.accept(projectRepo);
		setProjectCitiesFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ProjectSherpaV1_Update_Cities, jdbcTemplate);
	}
	public static void update_ProjectSherpaV1_With_NullCities_InJpa(JPATransactionVoidFunction <EntityManager> setProjectCitiesFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate){
		/**Project -> Cities assoc.*/
		setProjectCitiesFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setProjectCitiesFunction.accept(entityManager);
		setProjectCitiesFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ProjectSherpaV1_Update_NullCities, jdbcTemplate);
	}

	public static void update_ProjectSherpaV1_With_NullCities_InJpa(JPATransactionVoidFunction <ProjectRepository> setProjectCitiesFunction, ProjectRepository projectRepo, JdbcTemplate jdbcTemplate){
		/**Project -> Cities assoc.*/
		setProjectCitiesFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setProjectCitiesFunction.accept(projectRepo);
		setProjectCitiesFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_ProjectSherpaV1_Update_NullCities, jdbcTemplate);
	}

	public static SupplyContract buildSupplyContract(Contract contract, Staff staff, Supplier supplier) {
		SupplyContractId supplyContractId = new SupplyContractId(supplier.getId(), contract.getId(), staff.getId());
		SupplyContract ret = new SupplyContract();
		ret.setContract(contract);//RES-60
		ret.setStaff(staff);//RES-60
		ret.setSupplier(supplier);//RES-60
		ret.setId(supplyContractId);
		return ret;
	}

	public static void update_SupplierAccenture_With_SupplyContracts_InJpa(JPATransactionVoidFunction <EntityManager> setSupplierSypplyContractsFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate){//RES-52
		/**Supplier -> SupplierContracts assoc.*/
		setSupplierSypplyContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setSupplierSypplyContractsFunction.accept(entityManager);
		setSupplierSypplyContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_SupplierAccenture_Update_SupplyContracts, jdbcTemplate);
	}

	public static void update_SupplierAccenture_With_SupplyContracts_InJpa(JPATransactionVoidFunction <SupplierRepository> setSupplierSypplyContractsFunction, SupplierRepository  supplierRepo, JdbcTemplate jdbcTemplate){//RES-52
		/**Supplier -> SupplierContracts assoc.*/
		setSupplierSypplyContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setSupplierSypplyContractsFunction.accept(supplierRepo);
		setSupplierSypplyContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_SupplierAccenture_Update_SupplyContracts, jdbcTemplate);
	}

	public static ValidationResult isSupplierValid(Supplier supplier, String name, List <SupplyContract> supplyContracts, List <EmploymentContract> employmentContracts){//RS-58
		if (SupplierValidator.isSupplierNameValid(name).apply(supplier) != SUCCESS)//RES-58
			return SupplierValidator.isSupplierNameValid(name).apply(supplier);
		if (SupplierValidator.areEmploymentContractsValid(employmentContracts).apply(supplier) != SUCCESS)//RES-58
			return SupplierValidator.areEmploymentContractsValid(employmentContracts).apply(supplier);
		if (SupplierValidator.areSupplyContractsValid(supplyContracts).apply(supplier) != SUCCESS)//RES-58
			return SupplierValidator.areSupplyContractsValid(supplyContracts).apply(supplier);
		return SUCCESS;
	}

	public static EmploymentContract buildEmploymentContract(long id, Supplier supplier, Staff staff, Date startDate) { //RES-58
		EmploymentContract ret = new EmploymentContract();
		ret.setId(id);
		ret.setSupplier(supplier);
		ret.setStaff(staff);
		ret.setStartDate(startDate);
		return ret;
	}

	public static void update_SupplierAccenture_With_NullSupplyContracts_InJpa(JPATransactionVoidFunction <EntityManager> setSupplierSypplyContractsFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate){//RES-52
		/**Supplier -> SupplierContracts assoc.*/
		setSupplierSypplyContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setSupplierSypplyContractsFunction.accept(entityManager);
		setSupplierSypplyContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_SupplierAccenture_Update_NullSupplyContracts, jdbcTemplate);
	}

	public static void update_SupplierAccenture_With_NullSupplyContracts_InJpa(JPATransactionVoidFunction <SupplierRepository> setSupplierSypplyContractsFunction, SupplierRepository  supplierRepo, JdbcTemplate jdbcTemplate){//RES-52
		/**Supplier -> SupplierContracts assoc.*/
		setSupplierSypplyContractsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setSupplierSypplyContractsFunction.accept(supplierRepo);
		setSupplierSypplyContractsFunction.afterTransactionCompletion(SchemaUtils::testStateAfter_SupplierAccenture_Update_NullSupplyContracts, jdbcTemplate);
	}
	
	public static ValidationResult isTaskValid(Task task, String taskDesc, Integer priority, List <Assignment> assignments) {//RES-7
		if (isTaskDescValid(taskDesc).apply(task) != SUCCESS)
			return isTaskDescValid(taskDesc).apply(task);
		if (isTaskPriorityValid(priority).apply(task) != SUCCESS)
			return isTaskPriorityValid(priority).apply(task);
		if (areAssignmentsValid(assignments).apply(task) != SUCCESS)
			return areAssignmentsValid(assignments).apply(task);
		return SUCCESS;		
	}

	public static void update_Task12_With_Assignments_InJpa(JPATransactionVoidFunction<EntityManager> setTaskAssignmentsFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate){//RES-7
		setTaskAssignmentsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setTaskAssignmentsFunction.accept(entityManager);
		setTaskAssignmentsFunction.afterTransactionCompletion(SchemaUtils::testAfter_Task12_Update_Assignments, jdbcTemplate);
	}

	public static void update_Task12_With_Assignments_InJpa(JPATransactionVoidFunction<TaskRepository> setTaskAssignmentsFunction, TaskRepository taskRepo, JdbcTemplate jdbcTemplate){//RES-7
		setTaskAssignmentsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setTaskAssignmentsFunction.accept(taskRepo);
		setTaskAssignmentsFunction.afterTransactionCompletion(SchemaUtils::testAfter_Task12_Update_Assignments, jdbcTemplate);
	}

	public static void update_Task12_With_NullAssignments_InJpa(JPATransactionVoidFunction<EntityManager> setTaskAssignmentsFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate){//RES-7
		setTaskAssignmentsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setTaskAssignmentsFunction.accept(entityManager);
		setTaskAssignmentsFunction.afterTransactionCompletion(SchemaUtils::testAfter_Task12_Update_NullAssignments, jdbcTemplate);
	}

	public static void update_Task12_With_NullAssignments_InJpa(JPATransactionVoidFunction<TaskRepository> setTaskAssignmentsFunction, TaskRepository taskRepo, JdbcTemplate jdbcTemplate){//RES-7
		setTaskAssignmentsFunction.beforeTransactionCompletion(SchemaUtils::testInitialState, jdbcTemplate);
		setTaskAssignmentsFunction.accept(taskRepo);
		setTaskAssignmentsFunction.afterTransactionCompletion(SchemaUtils::testAfter_Task12_Update_NullAssignments, jdbcTemplate);
	}
}
