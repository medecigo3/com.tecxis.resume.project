package com.tecxis.resume.domain.util;

import java.util.Date;

import javax.persistence.EntityManager;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.ContractServiceAgreement;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Interest;
import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffProjectAssignment;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ContractServiceAgreementRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;
import com.tecxis.resume.domain.repository.InterestRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.SkillRepository;
import com.tecxis.resume.domain.repository.StaffProjectAssignmentRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.StaffSkillRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;

public class Utils {

	private Utils() {
		super();
	}

	public static Assignment insertAssignment(String desc, EntityManager entityManager) {
		Assignment assignment = new Assignment();
		assignment.setDesc(desc);		
		entityManager.persist(assignment);
		entityManager.flush();
		return assignment;
	}
	
	public static Assignment insertAssignment(String desc, AssignmentRepository assignmentRepo) {
		Assignment assignment = new Assignment();
		assignment.setDesc(desc);		
		assignmentRepo.saveAndFlush(assignment);
		return assignment;
	}

	public static City insertACity(String name, Country country, EntityManager entityManager) {
		City city = new City();
		city.setName(name);				
		city.setCountry(country);
		entityManager.persist(city);
		entityManager.flush();	
		return city;
		
	}
	
	public static City insertACity(String name, Country country, CityRepository cityRepo) {
		City city = new City();
		city.setName(name);				
		city.setCountry(country);
		cityRepo.saveAndFlush(city);		
		return city;
		
	}

	public static Client insertAClient(String name, EntityManager entityManager) {
		Client client = new Client();
		client.setName(name);	
		entityManager.persist(client);		
		entityManager.flush();
		return client;
		
	}
	
	public static Client insertAClient(String name, ClientRepository clientRepo) {
		Client client = new Client();
		client.setName(name);	
		clientRepo.saveAndFlush(client);		
		return client;
		
	}

	public static ContractServiceAgreement insertAContractServiceAgreement(Contract contract, Service service, EntityManager entityManager) {
		ContractServiceAgreement contractServiceAgreement = new ContractServiceAgreement(contract, service);		
		entityManager.persist(contractServiceAgreement);
		entityManager.flush();
		return contractServiceAgreement;
	}
	
	public static ContractServiceAgreement insertAContractServiceAgreement(Contract contract, Service service, ContractServiceAgreementRepository contractServiceAgreementRepo) {
		ContractServiceAgreement contractServiceAgreement = new ContractServiceAgreement(contract, service);		
		contractServiceAgreementRepo.saveAndFlush(contractServiceAgreement);
		return contractServiceAgreement;
	}

	public static Contract insertAContract(Client client, String name, EntityManager entityManager) {
		Contract contract  = new Contract();
		contract.setName(name);
		contract.setClient(client);
		entityManager.persist(contract);
		entityManager.flush();
		return contract;
		
	}
	
	public static Contract insertAContract(Client client, String name, ContractRepository contractRepo) {
		Contract contract  = new Contract();
		contract.setName(name);
		contract.setClient(client);		
		contractRepo.saveAndFlush(contract);
		return contract;
		
	}

	public static Country insertACountry(String name, EntityManager entityManager) {
		Country country = new Country();
		country.setName(name);
		entityManager.persist(country);		
		entityManager.flush();
		return country;
	}
	
	public static Country insertACountry(String name, CountryRepository countryRepo) {
		Country country = new Country();
		country.setName(name);
		countryRepo.saveAndFlush(country);		
		return country;
	}

	public static Course insertACourse(String title,  EntityManager entityManager) {
		Course course = new Course();
		course.setTitle(title);
		entityManager.persist(course);
		entityManager.flush();
		return course;
	}
	
	public static Course insertACourse(String title,  CourseRepository courseRepo) {
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

	public static Interest insertAnInterest(String desc, EntityManager entityManager) {
		Interest interest = new Interest();
		interest.setDesc(desc);		
		entityManager.persist(interest);
		entityManager.flush();
		return interest;
	}

	public static Interest insertAnInterest(String desc, InterestRepository InterestRepo) {
		Interest interest = new Interest();
		interest.setDesc(desc);		
		InterestRepo.saveAndFlush(interest);
		return interest;
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

	public static Project insertAProject(String name, String version, Client client, EntityManager entityManager) {
		Project project = new Project();
		project.setClient(client);		
		project.setName(name);
		project.setVersion(version);
		entityManager.persist(project);
		entityManager.flush();
		return project;
	
	}
	
	public static Project insertAProject(String name, String version, Client client, ProjectRepository projectRepo) {
		Project project = new Project();
		project.setClient(client);		
		project.setName(name);
		project.setVersion(version);		
		projectRepo.saveAndFlush(project);
		return project;
	
	}

	public static Service insertAService(String name, EntityManager entityManager) {
		Service service = new Service();
		service.setName(name);			
		entityManager.persist(service);
		entityManager.flush();
		return service;
	}
	
	public static Service insertAService(String name, ServiceRepository serviceRepo) {
		Service service = new Service();
		service.setName(name);					
		serviceRepo.saveAndFlush(service);
		return service;
	}

	public static Skill insertASkill(String name, EntityManager entityManager) {
		Skill skill = new Skill();
		skill.setName(name);
		entityManager.persist(skill);		
		entityManager.flush();
		return skill;
	}
	
	public static Skill insertASkill(String name, SkillRepository skillRepository) {
		Skill skill = new Skill();
		skill.setName(name);			
		skillRepository.saveAndFlush(skill);
		return skill;
	}

	public static StaffProjectAssignment insertAStaffProjectAssignment(Project project, Staff staff,  Assignment assignment, EntityManager entityManager) {
		StaffProjectAssignment staffProjectAssignment = new StaffProjectAssignment(project, staff, assignment);
		entityManager.persist(staffProjectAssignment);
		entityManager.flush();
		return staffProjectAssignment;
		
	}

	public static StaffProjectAssignment insertAStaffProjectAssignment(Project project, Staff staff,  Assignment assignment, StaffProjectAssignmentRepository staffProjectAssignmentRepo) {
		StaffProjectAssignment staffProjectAssignment = new StaffProjectAssignment(project, staff, assignment);		
		staffProjectAssignmentRepo.saveAndFlush(staffProjectAssignment);
		return staffProjectAssignment;
		
	}
	
	public static StaffSkill insertAStaffSkill(Staff staff, Skill skill, EntityManager entityManager) {
		StaffSkill staffSkill = new StaffSkill(skill, staff);
		entityManager.persist(staffSkill);
		entityManager.flush();
		return staffSkill;
		
	}

	public static StaffSkill insertAStaffSkill(Staff staff, Skill skill, StaffSkillRepository StaffSkillRepository) {
		StaffSkill staffSkill = new StaffSkill(skill, staff);		
		StaffSkillRepository.saveAndFlush(staffSkill);
		return staffSkill;
		
	}
	
	public static Staff insertAStaff(String firstName, String lastName, Date birthDate,  EntityManager entityManager) {
		Staff staff = new Staff();
		staff.setFirstName(firstName);
		staff.setLastName(lastName);
		staff.setBirthDate(birthDate);
		entityManager.persist(staff);
		entityManager.flush();
		return staff;
		
	}
	
	public static Staff insertAStaff(String firstName, String lastName, Date birthDate,  StaffRepository staffRepo) {
		Staff staff = new Staff();
		staff.setFirstName(firstName);
		staff.setLastName(lastName);
		staff.setBirthDate(birthDate);		
		staffRepo.saveAndFlush(staff);
		return staff;
		
	}

	public static Supplier insertASupplier(String name, EntityManager entityManager) {
		Supplier supplier = new Supplier();
		supplier.setName(name);
		entityManager.persist(supplier);
		entityManager.flush();
		return supplier;
	}
	
	public static Supplier insertASupplier(String name, SupplierRepository supplierRepo) {
		Supplier supplier = new Supplier();
		supplier.setName(name);		
		supplierRepo.saveAndFlush(supplier);
		return supplier;
	}

	public static SupplyContract insertASupplyContract(Supplier supplier, Contract contract, Staff staff, Date startDate, Date endDate, EntityManager entityManager){
		SupplyContract supplyContract = new SupplyContract(supplier, contract, staff);
		supplyContract.setStartDate(startDate);
		supplyContract.setEndDate(endDate);		
		entityManager.persist(supplyContract);
		entityManager.flush();
		return supplyContract;
		
	}
	
	public static SupplyContract insertASupplyContract(Supplier supplier, Contract contract, Staff staff, Date startDate, Date endDate, SupplyContractRepository supplyContractRepository){
		SupplyContract supplyContract = new SupplyContract(supplier, contract, staff);
		supplyContract.setStartDate(startDate);
		supplyContract.setEndDate(endDate);				
		supplyContractRepository.saveAndFlush(supplyContract);
		return supplyContract;
		
	}
	

}
