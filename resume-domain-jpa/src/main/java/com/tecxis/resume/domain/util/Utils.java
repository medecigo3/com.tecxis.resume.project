package com.tecxis.resume.domain.util;

import static com.tecxis.resume.domain.util.function.AgreementValidator.isContractValid;
import static com.tecxis.resume.domain.util.function.AgreementValidator.isServiceValid;
import static com.tecxis.resume.domain.util.function.AgreementValidator.AgreementValidationResult.SUCCESS;

import java.util.Date;

import javax.persistence.EntityManager;

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
import com.tecxis.resume.domain.util.function.SetContractAgreementFunction;

public class Utils {

	private Utils() {
		super();
	}

	public static Task insertTask(String desc, EntityManager entityManager) {
		Task task = new Task();
		task.setDesc(desc);		
		entityManager.persist(task);
		entityManager.flush();
		return task;
	}
	
	public static Task insertTask(String desc, TaskRepository taskRepo) {
		Task task = new Task();
		task.setDesc(desc);		
		taskRepo.saveAndFlush(task);
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
		Client client = new Client();
		client.setName(name);	
		entityManager.persist(client);		
		entityManager.flush();
		return client;
		
	}
	
	public static Client insertClient(String name, ClientRepository clientRepo) {
		Client client = new Client();
		client.setName(name);	
		clientRepo.saveAndFlush(client);		
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
		Agreement agreement = new Agreement(contract, service);		
		entityManager.persist(agreement);
		entityManager.flush();
		return agreement;
	}
	
	public static Agreement insertAgreement(Contract contract, Service service, AgreementRepository contractServiceAgreementRepo) {
		Agreement agreement = new Agreement(contract, service);		
		contractServiceAgreementRepo.saveAndFlush(agreement);
		return agreement;
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
		Contract contract  = new Contract();
		contract.setName(name);
		contract.setClient(client);
		entityManager.persist(contract);
		entityManager.flush();
		return contract;
		
	}
	
	public static Contract insertContract(Client client, String name, ContractRepository contractRepo) {
		Contract contract  = new Contract();
		contract.setName(name);
		contract.setClient(client);		
		contractRepo.saveAndFlush(contract);
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
		Project project = new Project();
		project.setClient(client);		
		project.setName(name);
		project.setVersion(version);
		entityManager.persist(project);
		entityManager.flush();
		return project;
	
	}
	
	public static Project insertProject(String name, String version, Client client, ProjectRepository projectRepo) {
		Project project = new Project();
		project.setClient(client);		
		project.setName(name);
		project.setVersion(version);		
		projectRepo.saveAndFlush(project);
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
		Service service = new Service();
		service.setName(name);			
		entityManager.persist(service);
		entityManager.flush();
		return service;
	}
	
	public static Service insertService(String name, ServiceRepository serviceRepo) {
		Service service = new Service();
		service.setName(name);					
		serviceRepo.saveAndFlush(service);
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
		Assignment assignment = new Assignment(project, staff, task);
		entityManager.persist(assignment);
		entityManager.flush();
		return assignment;
		
	}

	public static Assignment insertAssignment(Project project, Staff staff,  Task task, AssignmentRepository assignmentRepo) {
		Assignment assignment = new Assignment(project, staff, task);		
		assignmentRepo.saveAndFlush(assignment);
		return assignment;
		
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
		Staff staff = new Staff();
		staff.setFirstName(firstName);
		staff.setLastName(lastName);
		staff.setBirthDate(birthDate);
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
		Staff staff = new Staff();
		staff.setFirstName(firstName);
		staff.setLastName(lastName);
		staff.setBirthDate(birthDate);		
		staffRepo.saveAndFlush(staff);
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

	public static void doInJpa(SetContractAgreementFunction <EntityManager> function, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(entityManager);
		function.afterTransactionCompletion(jdbcTemplate);
	
	}
	
	public static void doInJpaRepository(SetContractAgreementFunction <AgreementRepository> function, AgreementRepository  repository, JdbcTemplate jdbcTemplate) {
		function.beforeTransactionCompletion(jdbcTemplate);
		function.accept(repository);
		function.afterTransactionCompletion(jdbcTemplate);
	
	}

	public static boolean isAgreementValid(Agreement agreement, String contractName, String serviceName) {
		if(	isContractValid(contractName).
			and(isServiceValid(serviceName)).
			apply(agreement).equals(SUCCESS)) {
			return true;
		}	else {
			return false;
		}
	}				

}
