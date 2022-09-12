package com.tecxis.resume.domain.util;

import static com.tecxis.resume.domain.util.function.AgreementValidator.isServiceValid;
import static com.tecxis.resume.domain.util.function.CityValidator.isNameValid;
import static com.tecxis.resume.domain.util.function.ClientValidator.areContractsValid;
import static com.tecxis.resume.domain.util.function.ClientValidator.isClientNameValid;
import static com.tecxis.resume.domain.util.function.ContractValidator.areSupplyContractsValid;
import static com.tecxis.resume.domain.util.function.ContractValidator.isContractIdValid;
import static com.tecxis.resume.domain.util.function.ProjectValidator.isProjectClientValid;
import static com.tecxis.resume.domain.util.function.ProjectValidator.isProjectNameValid;
import static com.tecxis.resume.domain.util.function.ProjectValidator.isProjectVersionValid;
import static com.tecxis.resume.domain.util.function.StaffValidator.isStaffFirstNameValid;
import static com.tecxis.resume.domain.util.function.StaffValidator.isStaffLastNameValid;
import static com.tecxis.resume.domain.util.function.SupplyContractValidator.isEndDateValid;
import static com.tecxis.resume.domain.util.function.SupplyContractValidator.isStartDateValid;
import static com.tecxis.resume.domain.util.function.TaskValidator.isTaskValid;
import static com.tecxis.resume.domain.util.function.ValidationResult.CITY_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CITY_LOCATIONS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CLIENT_CONTRACTS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CLIENT_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_AGREEMENTS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_CLIENT_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_ID_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CONTRACT_SUPPLYCONTRACTS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.COUNTRY_CITIES_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.COUNTRY_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_ASSIGNMENTS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_CLIENT_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_LOCATIONS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_VERSION_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SERVICE_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.STAFF_FIRSTNAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.STAFF_LASTNAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUPPLYCONTRACT_ENDDATE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUPPLYCONTRACT_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUPPLYCONTRACT_STARTDATE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.TASK_DESC_IS_NOT_VALID;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tecxis.resume.domain.Agreement;
import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Enrolment;
import com.tecxis.resume.domain.Interest;
import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.Task;
import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.AgreementRepository;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;
import com.tecxis.resume.domain.repository.EnrolmentRepository;
import com.tecxis.resume.domain.repository.InterestRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.SkillRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.StaffSkillRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;
import com.tecxis.resume.domain.repository.TaskRepository;
import com.tecxis.resume.domain.util.function.AgreementValidator;
import com.tecxis.resume.domain.util.function.CityValidator;
import com.tecxis.resume.domain.util.function.ContractValidator;
import com.tecxis.resume.domain.util.function.CountryValidator;
import com.tecxis.resume.domain.util.function.DeleteAgreementFunction;
import com.tecxis.resume.domain.util.function.DeleteAssignmentFunction;
import com.tecxis.resume.domain.util.function.DeleteCityFunction;
import com.tecxis.resume.domain.util.function.DeleteClientFunction;
import com.tecxis.resume.domain.util.function.DeleteContractFunction;
import com.tecxis.resume.domain.util.function.DeleteLocationFunction;
import com.tecxis.resume.domain.util.function.InsertAgreementFunction;
import com.tecxis.resume.domain.util.function.InsertAssignmentFunction;
import com.tecxis.resume.domain.util.function.InsertCityFunction;
import com.tecxis.resume.domain.util.function.InsertClientFunction;
import com.tecxis.resume.domain.util.function.ProjectValidator;
import com.tecxis.resume.domain.util.function.SetAssignmentAssociationFunction;
import com.tecxis.resume.domain.util.function.SetBrusselsInFranceFunction;
import com.tecxis.resume.domain.util.function.SetCityLocationsFunction;
import com.tecxis.resume.domain.util.function.SetContractAgreementFunction;
import com.tecxis.resume.domain.util.function.SetContractClientFunction;
import com.tecxis.resume.domain.util.function.SetLondonInFranceFunction;
import com.tecxis.resume.domain.util.function.SupplyContractValidator;
import com.tecxis.resume.domain.util.function.UnDeleteAssignmentFunction;
import com.tecxis.resume.domain.util.function.ValidationResult;

public class Utils {

	private Utils() {
		super();
	}

	public static Task insertTask(String desc, EntityManager entityManager) {
		Task task = buildTask(desc);
		entityManager.persist(task);
		entityManager.flush();
		return task;
	}
	
	public static Task insertTask(String desc, TaskRepository taskRepo) {
		Task task = buildTask(desc);		
		taskRepo.saveAndFlush(task);
		return task;
	}
	
	public static Task buildTask(String desc) {
		Task task = new Task();
		task.setDesc(desc);
		return task;
	}
	
	public static void removeTask(Task task, EntityManager entityManager) {		
		entityManager.remove(task);
		entityManager.flush();
	}
	
	public static void removeTask(Task task, TaskRepository taskRepo) {
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
	
	public static void removeCity(City city, EntityManager entityManager) {		
		entityManager.remove(city);
		entityManager.flush();
	}
	
	public static void removeCity(City city, CityRepository assignmentRepo) {
		assignmentRepo.delete(city);
		assignmentRepo.flush();
	}

	public static Client insertClient(String name, EntityManager entityManager) {
		Client client = buildClient(name);
		entityManager.persist(client);		
		entityManager.flush();
		return client;
		
	}
	
	public static Client insertClient(String name, ClientRepository clientRepo) {
		Client client = buildClient(name);
		clientRepo.saveAndFlush(client);		
		return client;
		
	}	
	
	public static Client buildClient(String name) {
		Client client = new Client();
		client.setName(name);	
		return client;		
	}
	
	public static void removeClient(Client client, EntityManager entityManager) {		
		entityManager.remove(client);
		entityManager.flush();
	}
	
	public static void removeClient(Client client, ClientRepository clientRepo) {
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
	
	public static void removeAgreement(Agreement agreement, EntityManager entityManager) {		
		entityManager.remove(agreement);
		entityManager.flush();
	}
	
	public static void removeAgreement(Agreement agreement, AgreementRepository contractServiceAgreementRepo) {
		contractServiceAgreementRepo.delete(agreement);
		contractServiceAgreementRepo.flush();
	}

	public static Contract insertContract(Client client, String name, EntityManager entityManager) {
		Contract contract = buildContract(client, name);
		entityManager.persist(contract);
		entityManager.flush();
		return contract;
		
	}
	
	public static Contract insertContract(Client client, String name, ContractRepository contractRepo) {
		Contract contract  = buildContract(client, name);
		contractRepo.saveAndFlush(contract);
		return contract;
		
	}
	
	public static Contract buildContract(Client client, String name) {
		Contract contract  = new Contract();
		contract.setName(name);
		contract.setClient(client);	
		return contract;
	}
	
	public static void removeContract(Contract contract, EntityManager entityManager) {		
		entityManager.remove(contract);
		entityManager.flush();
	}
	
	public static void removeContract(Contract contract, ContractRepository contractRepo) {
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
	
	public static void removeCountry(Country country, EntityManager entityManager) {		
		entityManager.remove(country);
		entityManager.flush();
	}
	
	public static void removeCountry(Country country, CountryRepository countryRepo) {
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
	
	public static void removeCourse(Course course, EntityManager entityManager) {		
		entityManager.remove(course);
		entityManager.flush();
	}
	
	public static void removeCourse(Course course, CourseRepository courseRepo) {
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
	
	public static void removeEmploymentContract(EmploymentContract employmentContract, EntityManager entityManager) {		
		entityManager.remove(employmentContract);
		entityManager.flush();
	}
	
	public static void removeEmploymentContract(EmploymentContract employmentContract, EmploymentContractRepository employmentContractRepo) {
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
	
	public static void removeInterest(Interest interest, EntityManager entityManager) {		
		entityManager.remove(interest);
		entityManager.flush();
	}
	
	public static void removeInterest(Interest interest, InterestRepository interestRepo) {
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
	
	public static void removeLocation(Location location, EntityManager entityManager) {		
		entityManager.remove(location);
		entityManager.flush();
	}
	
	public static void removeLocation(Location location, LocationRepository locationRepo) {
		locationRepo.delete(location);
		locationRepo.flush();
	}	

	public static Project insertProject(String name, String version, Client client, EntityManager entityManager) {
		Project project = buildProject(name, version, client);
		entityManager.persist(project);
		entityManager.flush();
		return project;
	}
	
	public static Project insertProject(String name, String version, Client client, ProjectRepository projectRepo) {
		Project project = buildProject(name, version, client);		
		projectRepo.saveAndFlush(project);
		return project;	
	}
	
	public static Project buildProject(String name, String version, Client client) {
		Project project = new Project();
		project.setClient(client);		
		project.setName(name);
		project.setVersion(version);
		return project;
	}
	
	public static void removeProject(Project project, EntityManager entityManager) {		
		entityManager.remove(project);
		entityManager.flush();
	}
	
	public static void removeProject(Project project, ProjectRepository projectRepo) {
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
	
	public static void removeService(Service service, EntityManager entityManager) {		
		entityManager.remove(service);
		entityManager.flush();
	}
	
	public static void removeService(Service service, ServiceRepository serviceRepo) {
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
	
	public static void removeSkill(Skill skill, EntityManager entityManager) {		
		entityManager.remove(skill);
		entityManager.flush();
	}
	
	public static void removeSkill(Skill skill, SkillRepository skillRepo) {
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
	
	public static void removeAssignment(Assignment assignment, EntityManager entityManager) {		
		entityManager.remove(assignment);
		entityManager.flush();
	}
	
	public static void removeAssignment(Assignment assignment, AssignmentRepository assignmentRepo) {
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
	
	public static void removeStaffSkill(StaffSkill staffSkill, EntityManager entityManager) {		
		entityManager.remove(staffSkill);
		entityManager.flush();
	}
	
	public static void removeStaffSkill(StaffSkill StaffSkill, StaffSkillRepository StaffSkillRepo) {
		StaffSkillRepo.delete(StaffSkill);
		StaffSkillRepo.flush();
	}
	
	public static Staff insertStaff(String firstName, String lastName, Date birthDate,  EntityManager entityManager) {
		Staff staff = buildStaff(firstName, lastName, birthDate);
		entityManager.persist(staff);
		entityManager.flush();
		return staff;
		
	}
	
	public static void removeStaff(Staff staff, EntityManager entityManager) {		
		entityManager.remove(staff);
		entityManager.flush();
	}
	
	public static void removeStaff(Staff StaffSkill, StaffRepository StaffRepo) {
		StaffRepo.delete(StaffSkill);
		StaffRepo.flush();
	}
	
	public static Staff insertStaff(String firstName, String lastName, Date birthDate,  StaffRepository staffRepo) {
		Staff staff = buildStaff(firstName, lastName, birthDate);			
		staffRepo.saveAndFlush(staff);
		return staff;
		
	}
	
	public static Staff buildStaff(String firstName, String lastName, Date birthDate) {
		Staff staff = new Staff();
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
	
	public static void removeSupplier(Supplier supplier, EntityManager entityManager) {		
		entityManager.remove(supplier);
		entityManager.flush();
	}
	
	public static void removeSupplier(Supplier supplier, SupplierRepository supplierRepo) {
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
	
	public static void removeSupplyContract(SupplyContract supplyContract, EntityManager entityManager) {		
		entityManager.remove(supplyContract);
		entityManager.flush();
	}
	
	public static void removeSupplyContract(SupplyContract supplyContract, SupplyContractRepository supplierContractRepo) {
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
	
	public static void removeEnrolment(Enrolment enrolment, EntityManager entityManager) {		
		entityManager.remove(enrolment);
		entityManager.flush();
	}
	
	public static void removeEnrolment(Enrolment enrolment, EnrolmentRepository enrolmentRepo) {
		enrolmentRepo.delete(enrolment);
		enrolmentRepo.flush();
	}

	public static void setContractAgreementInJpa(SetContractAgreementFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
	
	}
	
	public static void setContractAgreementInJpa(SetContractAgreementFunction <AgreementRepository> function, AgreementRepository  repository, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(repository);
		function.afterTransactionCompletion(jdbcTemplate);
	
	}

	public static ValidationResult isAgreementValid(Agreement agreement, String contractName, String serviceName) {
		if(CONTRACT_NAME_IS_NOT_VALID.equals((AgreementValidator.isContractValid(contractName).apply(agreement))))
			return CONTRACT_NAME_IS_NOT_VALID;
		if(SERVICE_NAME_IS_NOT_VALID.equals(isServiceValid(serviceName).apply(agreement)))
			return SERVICE_NAME_IS_NOT_VALID;
		return SUCCESS;
	}
	
	public static void insertAgreementInJpa(InsertAgreementFunction <AgreementRepository> function, AgreementRepository  repository, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(repository);
		function.afterTransactionCompletion(jdbcTemplate);
	
	}
	
	public static void insertAgreementInJpa(InsertAgreementFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
	
	}
	
	public static void deleteAgreementInJpa(DeleteAgreementFunction <EntityManager> function,EntityManager entityManager, JdbcTemplate jdbcTemplate){
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
	}
	
	public static void deleteAgreementInJpa(DeleteAgreementFunction <AgreementRepository> function, AgreementRepository agreementRepository, JdbcTemplate jdbcTemplate){
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(agreementRepository);
		function.afterTransactionCompletion(jdbcTemplate);
	}
	
	public static void insertAssignmentInJpa(InsertAssignmentFunction <AssignmentRepository> function, AssignmentRepository  assignmentRepo, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(assignmentRepo);
		function.afterTransactionCompletion(jdbcTemplate);
	
	}
	
	public static void insertAssignmentInJpa(InsertAssignmentFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
	
	}
	
	public static void deleteAssignmentInJpa(DeleteAssignmentFunction <EntityManager> function,EntityManager entityManager, JdbcTemplate jdbcTemplate){
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
	}
	
	public static void deleteAssignmentInJpa(DeleteAssignmentFunction <AssignmentRepository> function, AssignmentRepository agreementRepository, JdbcTemplate jdbcTemplate){
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(agreementRepository);
		function.afterTransactionCompletion(jdbcTemplate);
	}
	
	public static void unscheduleDeleteAssignmentInJpa(UnDeleteAssignmentFunction <EntityManager> function,EntityManager entityManager, JdbcTemplate jdbcTemplate){
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
	}
	
	public static void setAssignmentAssociationInJpa(SetAssignmentAssociationFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
	}
	
	public static void setAssignmentAssociationInJpa(SetAssignmentAssociationFunction <AssignmentRepository> function, AssignmentRepository assignmentRepo, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(assignmentRepo);
		function.afterTransactionCompletion(jdbcTemplate);
	}	
	
	public static ValidationResult isAssignmentValid(Assignment assignment, String projectName, String projectVersion, String clientName, String staffFirstName, String staffLastName, String taskDesc) {
		if (PROJECT_NAME_IS_NOT_VALID.equals(isProjectNameValid(projectName).apply(assignment.getProject())))
			return PROJECT_NAME_IS_NOT_VALID;
		if (PROJECT_VERSION_IS_NOT_VALID.equals(isProjectVersionValid(projectVersion).apply(assignment.getProject())))
			return PROJECT_VERSION_IS_NOT_VALID;
		if (PROJECT_CLIENT_IS_NOT_VALID.equals(isProjectClientValid(clientName).apply(assignment.getProject())))
			return PROJECT_CLIENT_IS_NOT_VALID;
		if (STAFF_FIRSTNAME_IS_NOT_VALID.equals(isStaffFirstNameValid(staffFirstName).apply(assignment.getStaff())))
			return STAFF_FIRSTNAME_IS_NOT_VALID;
		if (STAFF_LASTNAME_IS_NOT_VALID.equals(isStaffLastNameValid(staffLastName).apply(assignment.getStaff())))
			return STAFF_LASTNAME_IS_NOT_VALID;
		if (TASK_DESC_IS_NOT_VALID.equals(isTaskValid(taskDesc).apply(assignment.getTask())))
			return TASK_DESC_IS_NOT_VALID;
		return SUCCESS;		
	}
	
	public static void insertCityInJpa(InsertCityFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);		
	}
	
	public static void insertCityInJpa(InsertCityFunction <CityRepository> function, CityRepository cityRepo, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(cityRepo);
		function.afterTransactionCompletion(jdbcTemplate);		
	}
	
	public static ValidationResult isCityValid(City city, String cityName, String countryName, final List<Location> locations) {
		if (CITY_IS_NOT_VALID.equals(CityValidator.isCountryValid(countryName).apply(city)))
			return CITY_IS_NOT_VALID;
		if (CITY_IS_NOT_VALID.equals(isNameValid(cityName).apply(city)))
			return CITY_IS_NOT_VALID;
		if(CITY_LOCATIONS_ARE_NOT_VALID.equals(CityValidator.areLocationsValid(locations).apply(city)))
			return CITY_LOCATIONS_ARE_NOT_VALID;
		return SUCCESS;		
	}
	
	public static void setLondonToFranceInJpa(SetLondonInFranceFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplateProxy) {
		function.beforeTransactionCompletion(jdbcTemplateProxy);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplateProxy);
		
	}
	
	public static void setBrusslesToFranceInJpa(SetBrusselsInFranceFunction <CityRepository> function, CityRepository cityRepo, JdbcTemplate jdbcTemplateProxy) {
		function.beforeTransactionCompletion(jdbcTemplateProxy);
		function.accept(cityRepo);
		function.afterTransactionCompletion(jdbcTemplateProxy);
		
	}
	
	public static ValidationResult isCountryValid(Country country, String countryName, final List<City> cities) {
		if (COUNTRY_CITIES_ARE_NOT_VALID.equals(CountryValidator.areCitiesValid(cities).apply(country)))
			return COUNTRY_CITIES_ARE_NOT_VALID;
		if (COUNTRY_NAME_IS_NOT_VALID.equals(CountryValidator.isNameValid(countryName).apply(country)))
			return COUNTRY_NAME_IS_NOT_VALID;
		return SUCCESS;		
	}
 
	public static void setCityLocationsInJpa(SetCityLocationsFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplateProxy) {
		function.beforeTransactionCompletion(jdbcTemplateProxy);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplateProxy);
		
	}

	public static void setCityLocationsInJpa(SetCityLocationsFunction <CityRepository> function, CityRepository cityRepo, JdbcTemplate jdbcTemplateProxy) {
		function.beforeTransactionCompletion(jdbcTemplateProxy);
		function.accept(cityRepo);
		function.afterTransactionCompletion(jdbcTemplateProxy);
		
	}
	
	public static long insertClientInJpa(InsertClientFunction <EntityManager, Long> function, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		Long clientId = function.apply(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
		return clientId;
	}
	
	public static long insertClientInJpa(InsertClientFunction <ClientRepository, Long> function, ClientRepository clientRepo, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		Long clientId = function.apply(clientRepo);
		function.afterTransactionCompletion(jdbcTemplate);
		return clientId;
	}
	
	public static ValidationResult isClientValid(Client client, String clientName,@Null List <Contract> contracts) {
		if (CLIENT_NAME_IS_NOT_VALID.equals(isClientNameValid(clientName).apply(client)))
			return CLIENT_NAME_IS_NOT_VALID;
		if (CLIENT_CONTRACTS_ARE_NOT_VALID.equals(areContractsValid(contracts).apply(client)))
			return CLIENT_CONTRACTS_ARE_NOT_VALID;
		return SUCCESS;		
	}
	
	public static void deleteCityInJpa(DeleteCityFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplate){
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
	}
	
	public static void deleteCityInJpa(DeleteCityFunction <CityRepository> function, CityRepository cityRepo, JdbcTemplate jdbcTemplate){
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(cityRepo);
		function.afterTransactionCompletion(jdbcTemplate);
	}

	public static void deleteClientInJpa(DeleteClientFunction<EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);		
	}
	
	public static void deleteClientInJpa(DeleteClientFunction<ClientRepository> function, ClientRepository clientRepo, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(clientRepo);
		function.afterTransactionCompletion(jdbcTemplate);		
	}
	//TODO rename method with ..InJpa
	public static void setSagemContractWithMicropoleClient(DeleteContractFunction <EntityManager> deleteFunction, SetContractClientFunction <EntityManager> setFunction, EntityManager entityManager, JdbcTemplate jdbcTemplate) {		
		deleteFunction.beforeTransactionCompletion(jdbcTemplate);
		Consumer <EntityManager> deleteAndSetFunction = deleteFunction.andThen(setFunction);		
		deleteAndSetFunction.accept(entityManager);
		setFunction.afterTransactionCompletion(jdbcTemplate);
	}
	//TODO rename method with ..InJpa
	public static void setSagemContractWithMicropoleClient(DeleteContractFunction <ContractRepository> deleteFunction, SetContractClientFunction <ContractRepository> setFunction, ContractRepository contractRepo, JdbcTemplate jdbcTemplate) {
		deleteFunction.beforeTransactionCompletion(jdbcTemplate);
		Consumer <ContractRepository> deleteAndSetFunction = deleteFunction.andThen(setFunction);		
		deleteAndSetFunction.accept(contractRepo);			
		setFunction.afterTransactionCompletion(jdbcTemplate);
	}
	
	public static ValidationResult isContractValid(Contract contract, long contractId, Client client, int totalAgreements, int totalSypplyContracts) {
		if(CONTRACT_ID_IS_NOT_VALID.equals(isContractIdValid(contractId).apply(contract)))
			return CONTRACT_ID_IS_NOT_VALID;
		if(CONTRACT_CLIENT_IS_NOT_VALID.equals(ContractValidator.isClientValid(client).apply(contract)))
			return CONTRACT_CLIENT_IS_NOT_VALID;
		if(CONTRACT_AGREEMENTS_ARE_NOT_VALID.equals(ContractValidator.areAgreementsValid(totalAgreements).apply(contract)))
			return CONTRACT_AGREEMENTS_ARE_NOT_VALID;
		if(CONTRACT_SUPPLYCONTRACTS_ARE_NOT_VALID.equals(areSupplyContractsValid(totalSypplyContracts).apply(contract)))
			return CONTRACT_SUPPLYCONTRACTS_ARE_NOT_VALID;
		return SUCCESS;
	}
	
	public static ValidationResult isSupplyContractValid(SupplyContract supplyContract, Supplier supplier, Contract contract,@NotNull Date startDate,@Null Date endDate) {
		if(SUPPLYCONTRACT_IS_NOT_VALID.equals(SupplyContractValidator.isSupplyContractValid(supplier, contract).apply(supplyContract)))
			return SUPPLYCONTRACT_IS_NOT_VALID;
		if (SUPPLYCONTRACT_STARTDATE_NOT_VALID.equals(isStartDateValid(startDate).apply(supplyContract)))
			return SUPPLYCONTRACT_STARTDATE_NOT_VALID;
		if (SUPPLYCONTRACT_ENDDATE_NOT_VALID.equals(isEndDateValid(endDate).apply(supplyContract)))
			return SUPPLYCONTRACT_ENDDATE_NOT_VALID;
		return SUCCESS;
	}
	
	public static ValidationResult isProjectValid(Project project, String name, String version, List<Location> locations, Client client, List <Assignment> assignments) {
		if (PROJECT_NAME_IS_NOT_VALID.equals(ProjectValidator.isProjectNameValid(name).apply(project)))
			return PROJECT_NAME_IS_NOT_VALID;
		if (PROJECT_NAME_IS_NOT_VALID.equals(ProjectValidator.isProjectVersionValid(version).apply(project)))
			return PROJECT_NAME_IS_NOT_VALID;
		if (PROJECT_CLIENT_IS_NOT_VALID.equals(ProjectValidator.isProjectClientValid(client).apply(project)))
			return PROJECT_CLIENT_IS_NOT_VALID;
		if (PROJECT_ASSIGNMENTS_ARE_NOT_VALID.equals(ProjectValidator.areProjectAssignmentsValid(assignments).apply(project)))
			return PROJECT_ASSIGNMENTS_ARE_NOT_VALID;
		if (PROJECT_LOCATIONS_ARE_NOT_VALID.equals(ProjectValidator.areProjectLocationsValid(locations).apply(project)))
			return PROJECT_LOCATIONS_ARE_NOT_VALID;
		return SUCCESS;
		
	}
	
	public static CityId buildCityId(long cityId, long countryId) {
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
	
	public static City buildCity(CityId cityId) {
		return new City (cityId);
	}
	
	public static void removeParisMorningstarV1AxeltisLocationInJpa(DeleteLocationFunction<EntityManager> deleteLocationFunction, EntityManager em, JdbcTemplate jdbcTemplate) {
		deleteLocationFunction.beforeTransactionCompletion(jdbcTemplate);
		deleteLocationFunction.accept(em);
		deleteLocationFunction.afterTransactionCompletion(jdbcTemplate);
	}
	
	public static void removeParisMorningstarV1AxeltisLocationInJpa(DeleteLocationFunction<LocationRepository> deleteLocationFunction, LocationRepository locationRepo, JdbcTemplate jdbcTemplate) {
		deleteLocationFunction.beforeTransactionCompletion(jdbcTemplate);
		deleteLocationFunction.accept(locationRepo);
		deleteLocationFunction.afterTransactionCompletion(jdbcTemplate);
	}
}
