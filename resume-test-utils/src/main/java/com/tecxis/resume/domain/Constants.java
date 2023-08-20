package com.tecxis.resume.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Constants {

	/**TASK*/	
	public static final String TASK1 = "Contributing to the build, deployment and software configuration lifecycle phases of a three tier Java banking system.";
	public static final String TASK2 = "Working on with Rational multi-site Clear Case tools to control and manage software code evolution.";
	public static final String TASK3 = "Acting on as a conduit between offshore and onshore development teams.";
	public static final String TASK4 = "Implementation of a new deployment tool significantly reducing deployment time costs.";
	public static final String TASK5 = "Mentoring and guiding of two new team joiners.";
	public static final String TASK6 = "Assisting in the integration of enterprise modules by complying with Enterprise Integration standard patterns.";
	public static final String TASK7 = "Assisting in the configuration of maven pom modules for unit testing and building of releases.";
	public static final String TASK8 = "Scripting to help in the build and deployment tasks.";
	public static final String TASK9 = "Coding and configuring a web based portal based on Liferay technology to make Accenture's software assets available for all collaborators across the UKDC delivery center.";
	public static final String TASK10 = "Contributing to develop a product for an important company in the French energy sector, to monitor and manage energy consumption for the household market.";
	public static final String TASK11 = "Coding an authentication security system for the main Java enterprise service.";
	public static final String TASK12 = "Coding a JMX technology based system to monitor across the main Java enterprise services.";
	public static final String TASK13 = "Working with UML to code generation tools to conceive and develop a three tier Java architecture enterprise service.";
	public static final String TASK14 = "Provide technical expertise and assistance to migrate an older version of the Mule ESB code in production to later version 2.5.";
	public static final String TASK15 = "Configuration of multiple routes to direct the traffic in the ESB against multiple endpoints.";
	public static final String TASK16 = "Migration of XSLT style sheets from version 1.0 to 2.0 to comply with the new Saxon 2.0 XSLT processor embedded in the Mule ESB.";
	public static final String TASK17 = "Coding of XPath expressions to route SOAP traffic across different endpoints";
	public static final String TASK18 = "Coding of Java custom components adapted to the client business functional requirements.";
	public static final String TASK19 = "Involvement in the configuration of web service endpoints to comply with client security policies.";
	public static final String TASK20 = "Coding of a RGVML to RGV data transformation module to be integrated into a system based on the Calypso trading platform.";
	public static final String TASK21 = "Setup of TIBCO web services to expose data transformation services.";
	public static final String TASK22 = "Coding Business Works processes to retrieve, transform and integrate XML investment funds into a corporate database.";
	public static final String TASK23 = "Running unit tests to validate code against functional specifications.";
	public static final String TASK24 = "Coding of Business Works processes to automate the extraction, transformation, and transfer of client data.";
	public static final String TASK25 = "Coding Java business components to adapt Business Works code to business needs.";
	public static final String TASK26 = "Coding of XSLT style sheets for transformation of raw XML data into a canonical format.";
	public static final String TASK27 = "Setup of TIBCO web services to expose financial business data services.";
	public static final String TASK28 = "Automation of reporting tasks through the deployment of Business Works processing jobs.";
	public static final String TASK29 = "Deployment and configuration of Business Works components in production environment.";
	public static final String TASK30 = "Acting as a conduit between the business and developers.";
	public static final String TASK31 = "Writing of functional and technical documentation.";
	public static final String TASK32 = "Setup of TIBCO web service interfaces to integrate an ESB with other business components in a frontend-backend architecture.";
	public static final String TASK33 = "Analysis and estimation of code change requests through Quality Center";
	public static final String TASK34 = "Coding of WSDL and XSD schema files.";
	public static final String TASK37 = "Coding of XSLT style sheets for transformation of XML raw data to canonical data.";
	public static final String TASK39 = "Advising on TIBCO Business Works coding patterns and best practice support for Paris region and abroad.";
	public static final String TASK40 = "Coding of a Java API to simplify the CORBA abstraction layer for enterprise client and server applications.";
	public static final String TASK41 = "Architecting and designing the new CORBA client/server architecture for Paris region and abroad.";
	public static final String TASK42 = "Acting as a conduit between application managers and operations.";
	public static final String TASK43 = "Liaising with software editors (TIBCO and Microfocus) and onshore and offshore development teams.";
	public static final String TASK44 = "Design, development and testing of LO1, 2 and 3 of a rebranded car leasing website lying in architecture with a J2EE system as backend, Tibco as middleware and liferay and AngularJS as the frontend.";
	public static final String TASK45 = "Development and testing of LOT1 purchase order to delivery for the UK.";
	public static final String TASK46 = "Development and testing of LOT2 salary exchange for the UK.";
	public static final String TASK47 = "Development and testing of LOT3 incident management and booking maintenance service for Italy.";
	public static final String TASK48 = "Deployment of packages in DEV/UAT and goLive support assistance.";
	public static final String TASK49 = "Workshops at Swindon and Stevenage UK with the business to define Tibco technical specifications for LOT3.";
	public static final String TASK50 = "Development and testing of input and output half flows for the update and handling of product retrieval, supplier receipts and spare parts into MS Dynamics.";
	public static final String TASK51 = "Development of flows in batch mode to send new stockroom and facilities data into MS Dynamics.";
	public static final String TASK52 = "Deployment of packages in DEV, TST and PRD.";
	public static final String TASK53 = "Technical designs of flows including architecture diagram, sequence diagram, and container diagram.";
	public static final String TASK54 = "Development of flows using a publish event pattern";
	public static final String TASK55 = "Development of flows using TIBCO Active Spaces technology to retrieve and store transco values in the cache.";
	public static final String TASK56 = "Development of flows involving, proposal, policies quotes, claims documents.";
	public static final String TASK57 = "Improvements in the TIBCO maven framework to generate code with new standards and improvement of unit testing tool.";
	public static final String DEV_TASK_WILDCARD = "Development%";
	public static long TASK22_ID = 22L;
	public static long TASK23_ID = 23L;
	public static long TASK24_ID = 24L;
	public static long TASK25_ID = 25L;
	public static long TASK26_ID = 26L;
	public static long TASK27_ID = 27L;
	public static long TASK28_ID = 28L;
	public static long TASK29_ID = 29L;
	public static long TASK30_ID = 30L;
	public static long TASK31_ID = 31L;
	public static long TASK1_ID = 1L;//RES-14
	public static long TASK2_ID = 2L;//RES-14
	public static long TASK3_ID = 3L;//RES-14
	public static long TASK4_ID = 4L;//RES-14
	public static long TASK5_ID = 5L;//RES-14
	public static long TASK6_ID = 6L;//RES-14
	public static long TASK7_ID = 7L;//RES-14
	public static long TASK8_ID = 8L;//RES-14
	public static long TASK9_ID = 9L;//RES-14
	public static long TASK10_ID = 10L;//RES-14
	public static long TASK11_ID = 11L;//RES-14
	public static long TASK12_ID = 12L;//RES-14
	public static long TASK13_ID = 13L;//RES-14
	public static long TASK14_ID = 14L;//RES-14
	public static long TASK15_ID = 15L;//RES-14
	public static long TASK16_ID = 16L;//RES-14
	public static long TASK17_ID = 17L;//RES-14
	public static long TASK18_ID = 18L;//RES-14
	public static long TASK19_ID = 19L;//RES-14
	public static long TASK20_ID = 20L;//RES-14
	public static long TASK21_ID = 21L;//RES-14
	public static long TASK32_ID = 32L;//RES-14
	public static long TASK33_ID = 33L;//RES-14
	public static long TASK34_ID = 34L;//RES-14
	public static long TASK35_ID = 35L;//RES-14
	public static long TASK36_ID = 36L;//RES-14
	public static long TASK37_ID = 37L;//RES-14
	public static long TASK38_ID = 38L;//RES-14
	public static long TASK39_ID = 39L;//RES-14
	public static long TASK40_ID = 40L;//RES-14
	public static long TASK41_ID = 41L;//RES-14
	public static long TASK42_ID = 42L;//RES-14
	public static long TASK43_ID = 43L;//RES-14
	public static long TASK44_ID = 44L;//RES-14
	public static long TASK45_ID = 45L;//RES-14
	public static long TASK46_ID = 46L;//RES-14
	public static long TASK47_ID = 47L;//RES-14
	public static long TASK48_ID = 48L;//RES-14
	public static long TASK49_ID = 49L;//RES-14
	public static long TASK50_ID = 50L;//RES-14
	public static long TASK51_ID = 51L;//RES-14
	public static long TASK52_ID = 52L;//RES-14
	public static long TASK53_ID = 53L;//RES-14
	public static long TASK54_ID = 54L;//RES-14
	/**END TASK*/
	/**CITY*/	
	public static final String BRUSSELS = "Brussels";
	public static final String PARIS = "Paris";	
	public static final String LONDON = "London";
	public static final String MANCHESTER = "Manchester";
	public static final String SWINDON = "Swindon";
	public static final String BORDEAUX = "Bordeaux";
	public static final String LYON = "Lyon";
	public static final long PARIS_ID = 4L;
	public static final long LONDON_ID = 1L;
	public static final long MANCHESTER_ID = 1L;
	public static final long BORDEAUX_ID = 6L;
	public static final long LYON_ID = 7L;
	/**END CITY*/
	/**COUNTRY*/		
	public static final String BELGIUM = "Belgium";
	public static final String FRANCE = "France";
	public static final String UNITED_KINGDOM = "United Kingdom";
	public static final long FRANCE_ID = 1L;
	public static final long UNITED_KINGDOM_ID = 2L;
	/**END COUNTRY*/	
	/**CLIENT*/		
	public static final String BARCLAYS = "Barclays";
	public static final String AGEAS = "Ageas (Formerly Fortis)";
	public static final String AGEAS_SHORT = "Ageas%";
	public static final String ACCENTURE_CLIENT = "Accenture";
	public static final String SAGEMCOM = "Sagemcom";
	public static final String MICROPOLE = "Micropole";
	public static final String BELFIUS = "Belfius Insurance";
	public static final String AXELTIS = "Axeltis (Natixis group)";
	public static final String EULER_HERMES = "Euler Hermes";
	public static final String ARVAL = "Arval";
	public static final String LA_BANQUE_POSTALE = "La Banque Postale";
	public static final String SG = "Societe Generale Investment Banking";
	public static final String SG_WEBSITE = "www.sg.fr";
	public static final long CLIENT_BARCLAYS_ID = 1L;
	public static final long CLIENT_SAGEMCOM_ID = 4L;	
	public static final long CLIENT_MICROPOLE_ID = 5L;
	public static final long CLIENT_LA_BANQUE_POSTALE_ID = 6L;
	public static final long CLIENT_AXELTIS_ID = 7L;
	public static final long CLIENT_EULER_HERMES_ID = 8L;
	public static final long CLIENT_SG_ID = 9L;
	public static final long CLIENT_ARVAL_ID = 10L;
	public static final long CLIENT_HERMES_ID = 11L;
	public static final long CLIENT_BELFIUS_ID = 12L;
	public static final long CLIENT_AGEAS_ID = 2L;//RES-61
	public static final long CLIENT_ACCENTURE_ID = 3L;//RES-52
	/**END CLIENT*/		
	public static final String BW_6_COURSE = "BW618: TIBCO ActiveMatrix BusinessWorks 6.x Developer Boot Camp";
	public static final String SHORT_BW_6_COURSE = "BW618%";
	public static final String JAVA_WS = "Java Web Services";
	/**END COURSE*/		
	public static Date  AMT_ACCENTURE_EMPLOYMENT_STARTDATE;
	public static Date  AMT_ACCENTURE_EMPLOYMENT_ENDDATE;
	public static Date  AMT_AMESYS_EMPLOYMENT_STARTDATE;
	public static Date  AMT_AMESYS_EMPLOYMENT_ENDDATE;
	public static Date  AMT_FASTCONNECT_EMPLOYMENT_STARTDATE;
	public static Date  AMT_FASTCONNECT_EMPLOYMENT_ENDDATE;
	public static Date  AMT_ALTERNA_EMPLOYMENT_STARTDATE;
	public static Date  AMT_ALTERNA_EMPLOYMENT_ENDDATE;
	public static Date  AMT_ALPHATRESS_EMPLOYMENT_STARTDATE;
	public static Date  AMT_ALPHATRESS_EMPLOYMENT_ENDDATE = null;
	public static Date  JOHN_ALPHATRESS_EMPLOYMENT_STARTDATE;
	public static Date  JOHN_ALPHATRESS_EMPLOYMENT_ENDDATE;
	/**END EMPLOYMENT_CONTRACT*/	
	public static final String HOBBY = "Apart from being an integration consultant, I enjoy practicing endurance sports. I am an avid short distance runner but after a recent knee injury I've found a new passion in road bike riding.";
	public static final String RUNNING = "Running";
	public static final String SWIMMING = "Swimming";
	public static final String JOHN_INTEREST = "Football soccer and running";
	public static final String INTEREST_DESC = "interest desc.";
	/**END INTEREST*/		
	/**PROJECT*/	
	public static final String ADIR = "ADIR";
	public static final String FORTIS = "FORTIS";
	public static final String DCSC = "DCSC";
	public static final String TED = "TED";
	public static final String PARCOURS = "PARCOURS";
	public static final String EOLIS = "EOLIS";
	public static final String AOS = "AOS";
	public static final String SHERPA = "SHERPA";
	public static final String SELENIUM = "SELENIUM";
	public static final String MORNINGSTAR = "MORNINGSTAR";
	public static final String CENTRE_DES_COMPETENCES = "CENTRE_DES_COMPETENCES";
	public static final String EUROCLEAR_VERS_CALYPSO = "EUROCLEAR_VERS_CALYPSO";
	public static final String VERSION_1 = "1.0";
	public static final String VERSION_2 = "2.0";
	public static final String VERSION_3 = "3.0";
	public static final String PROJECT_DESC = "project desc.";
	public static long PROJECT_ADIR_V1_ID = 1L; 
	public static long PROJECT_TED_V1_ID = 4L;
	public static long PROJECT_PARCOURS_V1_ID = 5L;
	public static long PROJECT_EUROCLEAR_VERS_CALYPSO_V1_ID = 6L;
	public static long PROJECT_MORNINGSTAR_V1_ID = 7L;
	public static long PROJECT_EOLIS_V1_ID = 8L;
	public static long PROJECT_MORNINGSTAR_V2_ID = 9L;
	public static long PROJECT_CENTRE_DES_COMPETENCES_V1_ID = 10L;
	public static long PROJECT_AOS_V1_ID =11L;
	public static long PROJECT_SELENIUM_V1_ID = 12L;
	public static long PROJECT_SHERPA_V1_ID = 13L;
	/**END PROJECT*/
	/**SERVICE*/	
	public static final String SCM_ASSOCIATE_DEVELOPPER = "Associate Software Configuration Management";
	public static final String JAVA_INTEGRATION_DEVELOPPER = "Java Integration Developer";
	public static final String LIFERAY_DEVELOPPER = "Liferay Developer";
	public static final String J2EE_DEVELOPPER = "J2EE Developer";
	public static final String MULE_ESB_CONSULTANT = "Mule ESB Consultant";
	public static final String TIBCO_BW_CONSULTANT = "TIBCO Business Works Developer";
	public static final String DEVELOPER_WILDCARD = "%Developer";
	public static final String BUSINESS_WORKS_WILDCARD = "%Business Works%";
	public static final String TEST_DESCRIPTION = "test description";
	/**END SERVICE*/
	/**SKILL*/	
	public static final String TIBCO = "TIBCO";
	public static final String ORACLE = "Oracle";
	public static final String JAVA = "Java";
	public static final String SPRING = "Spring";
	public static final String UNIX = "Unix";
	public static final String GIT = "Git";
	public static final String DUMMY_SKILL = "Dummy skill";
	/**END SKILL*/
	/**STAFF*/	
	public static final String AMT_NAME = "Arturo";
	public static final String AMT_LASTNAME = "Medecigo Tress";
	public static final String JOHN_NAME = "John";
	public static final String JOHN_LASTNAME = "Smith";
	public static final Date BIRTHDATE = new GregorianCalendar(1982, 10, 06).getTime();
	public static long STAFF_AMT_ID = 1L;
	public static long STAFF_JOHN_ID = 2L;//RES-13
	/**STAFF*/
	/**SUPPLY_CONTRACT*/	
	public static  Date CONTRACT1_STARTDATE;
	public static  Date CONTRACT1_ENDDATE;
	public static  Date CONTRACT2_STARTDATE;
	public static  Date CONTRACT2_ENDDATE;
	public static  Date CONTRACT3_STARTDATE;
	public static  Date CONTRACT3_ENDDATE;
	public static  Date CONTRACT4_STARTDATE;
	public static  Date CONTRACT4_ENDDATE;
	public static  Date CONTRACT5_STARTDATE;
	public static  Date CONTRACT5_ENDDATE;
	public static  Date CONTRACT6_STARTDATE;
	public static  Date CONTRACT6_ENDDATE;
	public static  Date CONTRACT7_STARTDATE;
	public static  Date CONTRACT7_ENDDATE;
	public static  Date CONTRACT8_STARTDATE;
	public static  Date CONTRACT8_ENDDATE;
	public static  Date CONTRACT9_STARTDATE;
	public static  Date CONTRACT9_ENDDATE;
	public static  Date CONTRACT10_STARTDATE;
	public static  Date CONTRACT10_ENDDATE;
	public static  Date CONTRACT11_STARTDATE;
	public static  Date CONTRACT11_ENDDATE;
	public static  Date CONTRACT12_STARTDATE;
	public static  Date CONTRACT12_ENDDATE;
	public static  Date CONTRACT13_STARTDATE;
	public static  Date CONTRACT13_ENDDATE = null;
	public static  Date CONTRACT14_STARTDATE;
	public static  Date CONTRACT14_ENDDATE;
	public static  Date CURRENT_DATE = new Date();
	/**END SUPPLY_CONTRACT*/
	
	/**CONTRACT*/	
	public static final String CONTRACT1_NAME = "BarclaysContract";
	public static final String CONTRACT2_NAME = "AgeasContract";
	public static final String CONTRACT3_NAME = "AccentureContract";
	public static final String CONTRACT4_NAME = "SagemcomContract";
	public static final String CONTRACT5_NAME = "MicropoleContract";
	public static final String CONTRACT6_NAME = "LbpContract";
	public static final String CONTRACT7_NAME = "AxeltisContract1";
	public static final String CONTRACT8_NAME = "EhContract";
	public static final String CONTRACT9_NAME = "AxeltisContract2";
	public static final String CONTRACT10_NAME = "SGContract";
	public static final String CONTRACT11_NAME = "ArvalContract";
	public static final String CONTRACT12_NAME = "HermesContract";
	public static final String CONTRACT13_NAME = "BelfiusContract";
	public static final long CONTRACT13_ID = 13L; //TODO rename contract ID with naming standards
	public static final long CONTRACT2_ID = 2L; //TODO rename contract ID with naming standards
	public static final long CONTRACT_BARCLAYS_ID = 1L;//RES-10
	public static final long CONTRACT_ARVAL_ID = 11L;//RES-10
	public static final long CONTRACT_AXELTIS_ID1 = 7L;//RES-10
	public static final long CONTRACT_AXELTIS_ID2 = 9L;//RES-10
	public static final long CONTRACT_AGEAS_ID = 2L;//RES-10
	public static final long CONTRACT_EH_ID = 8L;//RES-10
	public static final long CONTRACT_FOO_ID = 14L;//RES-42
	public static final long CONTRACT_ACCENTURE_ID = 3L;//RES-52

	/**END CONTRACT*/
	
	/**SUPPLIER*/	
	public static final String ACCENTURE_SUPPLIER = "ACCENTURE";
	public static final String AMESYS = "AMESYS";
	public static final String FASTCONNECT = "FASTCONNECT";
	public static final String ALTERNA = "ALTERNA";
	public static final String ALPHATRESS = "ALPHATRESS";
	public static final long SUPPLIER_ACCENTURE_ID = 1L;//RES-56
	public static final long SUPPLIER_AMESYS_ID = 2L;//RES-56
	public static final long SUPPLIER_FASTCONNECT_ID = 3L;//RES-56
	public static final long SUPPLIER_ALTERNA_ID = 4L;//RES-56
	public static final long SUPPLIER_ALPHATRESS_ID = 5L;//RES-56
	/**END SUPPLIER*/
	
	/**LOCATION*/
	public static final long CITY_PARIS_TOTAL_LOCATIONS = 9;
	public static final long CITY_PARIS_MORNINGSTAR_V1_AXELTIS_TOTAL_LOCATIONS = 1;//RES-61
	/**END LOCATION*/
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat ddMMMyyyy = new   SimpleDateFormat("dd-MMM-yy");
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
			CONTRACT14_STARTDATE = sdf.parse("01/03/2017");
			CONTRACT14_ENDDATE = sdf.parse("31/12/2019");
			
			AMT_ACCENTURE_EMPLOYMENT_STARTDATE = ddMMMyyyy.parse("01-JAN-07");
			AMT_ACCENTURE_EMPLOYMENT_ENDDATE = ddMMMyyyy.parse("01-JUL-08");
			AMT_AMESYS_EMPLOYMENT_STARTDATE = ddMMMyyyy.parse("01-OCT-08");
			AMT_AMESYS_EMPLOYMENT_ENDDATE = ddMMMyyyy.parse("01-JUL-10");
			AMT_FASTCONNECT_EMPLOYMENT_STARTDATE = ddMMMyyyy.parse("01-JUL-10");
			AMT_FASTCONNECT_EMPLOYMENT_ENDDATE = ddMMMyyyy.parse("01-JUN-15");
			AMT_ALTERNA_EMPLOYMENT_STARTDATE = ddMMMyyyy.parse("01-JUN-15");
			AMT_ALTERNA_EMPLOYMENT_ENDDATE = ddMMMyyyy.parse("01-AUG-16");
			AMT_ALPHATRESS_EMPLOYMENT_STARTDATE = ddMMMyyyy.parse("01-AUG-16");			
			JOHN_ALPHATRESS_EMPLOYMENT_STARTDATE = ddMMMyyyy.parse("01-MAR-17");
			JOHN_ALPHATRESS_EMPLOYMENT_ENDDATE = ddMMMyyyy.parse("31-DEC-19");
		}
		
		
		catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}	

			
	}

	/**EMPLOYMENT_CONTRACT*/
	public static final long AMT_ALTERNA_EMPLOYMENT_CONTRACT_ID = 4L;//RES-58
	public static final long AMT_ACCENTURE_EMPLOYMENT_CONTRACTID = 1L;//RES-58
	
	private Constants() {
	}
	

}
