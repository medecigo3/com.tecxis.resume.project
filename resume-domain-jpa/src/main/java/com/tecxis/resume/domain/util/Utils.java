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

	public static City insertACity(String name, Country country, EntityManager entityManager) {
		City city = new City();
		city.setName(name);				
		city.setCountry(country);
		entityManager.persist(city);
		entityManager.flush();	
		return city;
		
	}

	public static Client insertAClient(String name, EntityManager entityManager) {
		Client client = new Client();
		client.setName(name);	
		entityManager.persist(client);		
		entityManager.flush();
		return client;
		
	}

	public static ContractServiceAgreement insertAContractServiceAgreement(Contract contract, Service service, EntityManager entityManager) {
		ContractServiceAgreement contractServiceAgreement = new ContractServiceAgreement(contract, service);		
		entityManager.persist(contractServiceAgreement);
		entityManager.flush();
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

	public static Country insertACountry(String name, EntityManager entityManager) {
		Country country = new Country();
		country.setName(name);
		entityManager.persist(country);		
		entityManager.flush();
		return country;
	}

	public static Course insertACourse(String title,  EntityManager entityManager) {
		Course course = new Course();
		course.setTitle(title);
		entityManager.persist(course);
		entityManager.flush();
		return course;
	}

	public static EmploymentContract insertEmploymentContract(Supplier supplier, Staff staff, EntityManager entityManager){
		EmploymentContract employmentContract = new EmploymentContract(staff, supplier);
		employmentContract.setStartDate(new Date());
		entityManager.persist(employmentContract);
		entityManager.flush();
		return employmentContract;
		
	}

	public static Interest insertAnInterest(String desc, EntityManager entityManager) {
		Interest interest = new Interest();
		interest.setDesc(desc);		
		entityManager.persist(interest);
		entityManager.flush();
		return interest;
	}

	public static Location insertLocation(City city, Project project, EntityManager entityManager) {		
		Location location = new Location(city, project);
		entityManager.persist(location);
		entityManager.flush();
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

	public static Service insertAService(String name, EntityManager entityManager) {
		Service service = new Service();
		service.setName(name);			
		entityManager.persist(service);
		entityManager.flush();
		return service;
	}

	public static Skill insertASkill(String name, EntityManager entityManager) {
		Skill skill = new Skill();
		skill.setName(name);
		entityManager.persist(skill);		
		entityManager.flush();
		return skill;
	}

	public static StaffProjectAssignment insertAStaffProjectAssignment(Project project, Staff staff,  Assignment assignment, EntityManager entityManager) {
		StaffProjectAssignment staffProjectAssignment = new StaffProjectAssignment(project, staff, assignment);
		entityManager.persist(staffProjectAssignment);
		entityManager.flush();
		return staffProjectAssignment;
		
	}

	public static StaffSkill insertAStaffSkill(Staff staff, Skill skill, EntityManager entityManager) {
		StaffSkill staffSkill = new StaffSkill(skill, staff);
		entityManager.persist(staffSkill);
		entityManager.flush();
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

	public static Supplier insertASupplier(String name, EntityManager entityManager) {
		Supplier supplier = new Supplier();
		supplier.setName(name);
		entityManager.persist(supplier);
		entityManager.flush();
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
	

}
