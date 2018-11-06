-- Create sequences section -------------------------------------------------

CREATE SEQUENCE "CITY_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "PROJECT_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "CLIENT_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "COUNTRY_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "ASSIGNMENT_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "SKILL_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "STAFF_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "SERVICE_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "INTEREST_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "SUPPLIER_SEQ"
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE "CONTRACT_SEQ"
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;
 
 
 CREATE SEQUENCE "COURSE_SEQ"
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;

-- Create tables section -------------------------------------------------

-- Table PROJECT

CREATE TABLE "PROJECT"(
  "PROJECT_ID" Integer NOT NULL,
  "NAME" Varchar2(30 ) NOT NULL,
  "VERSION" Varchar2(30 ) NOT NULL,
  "CLIENT_ID" Integer NOT NULL,
  "DESC" Varchar2(400 )
);


-- Add keys for table PROJECT

ALTER TABLE "PROJECT" ADD CONSTRAINT "PK_PROJECT" PRIMARY KEY ("CLIENT_ID","PROJECT_ID");


ALTER TABLE "PROJECT" ADD CONSTRAINT "AK_PROJECT" UNIQUE ("NAME","VERSION");

-- Table CLIENT

CREATE TABLE "CLIENT"(
  "CLIENT_ID" Integer NOT NULL,
  "NAME" Varchar2(50 ),
  "WEBSITE" Varchar2(100 )
);


-- Add keys for table CLIENT

ALTER TABLE "CLIENT" ADD CONSTRAINT "PK_CUSTOMER_ID" PRIMARY KEY ("CLIENT_ID");


-- Table CONTRACT

CREATE TABLE "CONTRACT"(
  "CONTRACT_ID" Integer NOT NULL,
  "CLIENT_ID" Integer NOT NULL,
  "SUPPLIER_ID" Integer NOT NULL,
  "SERVICE_ID" Integer NOT NULL,
  "STAFF_ID" Integer NOT NULL,
  "START_DATE" Date NOT NULL,
  "END_DATE" Date
);


-- Add keys for table CONTRACT

ALTER TABLE "CONTRACT" ADD CONSTRAINT "PK_CONTRACT_ID" PRIMARY KEY ("CLIENT_ID","SUPPLIER_ID","SERVICE_ID","CONTRACT_ID","STAFF_ID");


-- Table COUNTRY

CREATE TABLE "COUNTRY"(
  "COUNTRY_ID" Integer NOT NULL,
  "NAME" Varchar2(30 )
);


-- Add keys for table COUNTRY

ALTER TABLE "COUNTRY" ADD CONSTRAINT "PK_COUNTRY_ID" PRIMARY KEY ("COUNTRY_ID");

-- Table STAFF_ASSIGNMENT

CREATE TABLE "STAFF_ASSIGNMENT"(
  "ASSIGNMENT_ID" Integer NOT NULL,
  "PROJECT_ID" Integer NOT NULL,
  "CLIENT_ID" Integer NOT NULL,
  "STAFF_ID" Integer NOT NULL
);


-- Add keys for table STAFF_ASSIGNMENT

ALTER TABLE "STAFF_ASSIGNMENT" ADD CONSTRAINT "PK_ASSIGNMENT_ID" PRIMARY KEY ("ASSIGNMENT_ID","PROJECT_ID","STAFF_ID","CLIENT_ID");


-- Table SKILL

CREATE TABLE "SKILL"(
  "SKILL_ID" Integer NOT NULL,
  "NAME" Varchar2(30 )
);


-- Add keys for table SKILL

ALTER TABLE "SKILL" ADD CONSTRAINT "PK_SKILL_ID" PRIMARY KEY ("SKILL_ID");


-- Table STAFF

CREATE TABLE "STAFF"(
  "STAFF_ID" Integer NOT NULL,
  "NAME" Varchar2(30 ),
  "LASTNAME" Varchar2(30 ),
  "BIRTH_DATE" Date
);


-- Add keys for table STAFF

ALTER TABLE "STAFF" ADD CONSTRAINT "PK_STAFF_ID" PRIMARY KEY ("STAFF_ID");


-- Table SERVICE

CREATE TABLE "SERVICE"(
  "SERVICE_ID" Integer NOT NULL,
  "NAME" Varchar2(50 ),
  "DESC" Varchar2(400 )
);


-- Add keys for table SERVICE

ALTER TABLE "SERVICE" ADD CONSTRAINT "PK_SERVICE_ID" PRIMARY KEY ("SERVICE_ID");


-- Table INTEREST

CREATE TABLE "INTEREST"(
  "INTEREST_ID" Integer NOT NULL,
  "STAFF_ID" Integer,
  "DESC" Varchar2(500 )
);

-- Add keys for table INTEREST

ALTER TABLE "INTEREST" ADD CONSTRAINT "PK_INTEREST_ID" PRIMARY KEY ("INTEREST_ID");


-- Table CITY

CREATE TABLE "CITY"(
  "CITY_ID" Integer NOT NULL,
  "COUNTRY_ID" Integer NOT NULL,
  "NAME" Varchar2(30 )
);


-- Add keys for table CITY

ALTER TABLE "CITY" ADD CONSTRAINT "PK_CITY" PRIMARY KEY ("CITY_ID","COUNTRY_ID");


-- Table STAFF_SKILL

CREATE TABLE "STAFF_SKILL"(
  "STAFF_ID" Integer NOT NULL,
  "SKILL_ID" Integer NOT NULL,
  "YEARS_OF_EXPERIENCE" Integer,
  "ENDORSEMENT" Integer
);


-- Add keys for table STAFF_SKILL

ALTER TABLE "STAFF_SKILL" ADD CONSTRAINT "PK_SKILL_USED" PRIMARY KEY ("STAFF_ID","SKILL_ID");


-- Table COURSE

CREATE TABLE "COURSE"(
  "COURSE_ID" Integer NOT NULL,
  "TITLE" Varchar2(100 ) NOT NULL,
  "CREDITS" Integer
);


-- Add keys for table COURSE

ALTER TABLE "COURSE" ADD CONSTRAINT "PK_COURSE_ID" PRIMARY KEY ("COURSE_ID");

ALTER TABLE "COURSE" ADD CONSTRAINT "AK_COURSE" UNIQUE ("TITLE");

-- Table ENROLMENT

CREATE TABLE "ENROLMENT"(
  "COURSE_ID" Integer NOT NULL,
  "STAFF_ID" Integer NOT NULL,
  "GRADE" Varchar2(30 ),
  "START_DATE" Date,
  "END_DATE" Date,
  "DURATION" Integer
);


-- Add keys for table ENROLMENT

ALTER TABLE "ENROLMENT" ADD CONSTRAINT "PK_ENROLLMENT" PRIMARY KEY ("COURSE_ID","STAFF_ID");


-- Table SUPPLIER

CREATE TABLE "SUPPLIER"(
  "SUPPLIER_ID" Integer NOT NULL,
  "STAFF_ID" Integer NOT NULL,
  "NAME" Varchar2(30 )
);


-- Add keys for table SUPPLIER

ALTER TABLE "SUPPLIER" ADD CONSTRAINT "SUPPLIER_PK" PRIMARY KEY ("SUPPLIER_ID","STAFF_ID");


-- Table LOCATION

CREATE TABLE "LOCATION"(
  "PROJECT_ID" Integer NOT NULL,
  "CLIENT_ID" Integer NOT NULL,
  "CITY_ID" Integer NOT NULL,
  "COUNTRY_ID" Integer NOT NULL
);


-- Add keys for table LOCATION

ALTER TABLE "LOCATION" ADD CONSTRAINT "PK_LOCATION" PRIMARY KEY ("CLIENT_ID","CITY_ID","COUNTRY_ID","PROJECT_ID");

-- Table ASSIGNMENT

CREATE TABLE "ASSIGNMENT"(
  "ASSIGNMENT_ID" Integer NOT NULL,
  "DESC" Varchar2(255 ) NOT NULL,
  "PRIORITY" Integer
);

-- Add keys for table ASSIGNMENT

ALTER TABLE "ASSIGNMENT" ADD CONSTRAINT "PK_ASSIGNMENT" PRIMARY KEY ("ASSIGNMENT_ID");


ALTER TABLE "ASSIGNMENT" ADD CONSTRAINT "AK_ASSIGNMENT" UNIQUE ("DESC");

-- Create views section -------------------------------------------------

CREATE VIEW "v_ProjectTask" AS
SELECT "STAFF"."NAME", "LASTNAME", "PROJECT"."NAME" AS "PROJECT", "VERSION" AS "PROJECT_VERSION", "ASSIGNMENT"."DESC" AS "TASK"
FROM "STAFF_ASSIGNMENT", "PROJECT", "STAFF", "ASSIGNMENT"
WHERE PROJECT.PROJECT_ID = STAFF_ASSIGNMENT.PROJECT_ID AND
PROJECT.CLIENT_ID = STAFF_ASSIGNMENT.CLIENT_ID AND
STAFF_ASSIGNMENT.ASSIGNMENT_ID = ASSIGNMENT.ASSIGNMENT_ID AND
STAFF.STAFF_ID = STAFF_ASSIGNMENT.STAFF_ID
ORDER BY PROJECT.NAME;


CREATE VIEW "v_Experience" AS
SELECT "STAFF"."NAME", "LASTNAME", "SERVICE"."NAME" AS "POSITION", "CLIENT"."NAME" AS "CUSTOMER", "START_DATE", "END_DATE", "CITY"."NAME" AS "CITY", "COUNTRY"."NAME" AS "COUNTRY", "SERVICE"."DESC" AS "JOB_DESC"
FROM "SERVICE", "CLIENT", "CONTRACT", "STAFF", "PROJECT", "COUNTRY", "CITY", "LOCATION", "STAFF_ASSIGNMENT"
WHERE CITY.COUNTRY_ID = COUNTRY.COUNTRY_ID AND
LOCATION.CITY_ID = CITY.CITY_ID AND 
LOCATION.COUNTRY_ID = CITY.COUNTRY_ID AND 
LOCATION.PROJECT_ID = PROJECT.PROJECT_ID AND
PROJECT.CLIENT_ID = CLIENT.CLIENT_ID AND
CLIENT.CLIENT_ID = CONTRACT.CLIENT_ID AND
PROJECT.PROJECT_ID = STAFF_ASSIGNMENT.PROJECT_ID AND
PROJECT.CLIENT_ID = STAFF_ASSIGNMENT.CLIENT_ID AND
STAFF_ASSIGNMENT.STAFF_ID = STAFF.STAFF_ID AND
CONTRACT.SERVICE_ID = SERVICE.SERVICE_ID 
GROUP BY "STAFF"."NAME", "LASTNAME", "SERVICE"."NAME", "CLIENT"."NAME", "START_DATE", "END_DATE", "CITY"."NAME", "COUNTRY"."NAME", "SERVICE"."DESC"
ORDER BY CONTRACT.START_DATE;


-- Create relationships section ------------------------------------------------- 

ALTER TABLE "CONTRACT" ADD CONSTRAINT "SIGNS" FOREIGN KEY ("CLIENT_ID") REFERENCES "CLIENT" ("CLIENT_ID");


ALTER TABLE "CITY" ADD CONSTRAINT "HAS A" FOREIGN KEY ("COUNTRY_ID") REFERENCES "COUNTRY" ("COUNTRY_ID");


ALTER TABLE "INTEREST" ADD CONSTRAINT "HAS" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID");


ALTER TABLE "STAFF_SKILL" ADD CONSTRAINT "USES" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID");


ALTER TABLE "STAFF_SKILL" ADD CONSTRAINT "IS USED" FOREIGN KEY ("SKILL_ID") REFERENCES "SKILL" ("SKILL_ID");


ALTER TABLE "ENROLMENT" ADD CONSTRAINT "IN" FOREIGN KEY ("COURSE_ID") REFERENCES "COURSE" ("COURSE_ID");


ALTER TABLE "CONTRACT" ADD CONSTRAINT "HOLDS" FOREIGN KEY ("SUPPLIER_ID", "STAFF_ID") REFERENCES "SUPPLIER" ("SUPPLIER_ID", "STAFF_ID");


ALTER TABLE "ENROLMENT" ADD CONSTRAINT "ENROLS" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID");


ALTER TABLE "PROJECT" ADD CONSTRAINT "CONTROLS" FOREIGN KEY ("CLIENT_ID") REFERENCES "CLIENT" ("CLIENT_ID");


ALTER TABLE "CONTRACT" ADD CONSTRAINT "ENGAGES" FOREIGN KEY ("SERVICE_ID") REFERENCES "SERVICE" ("SERVICE_ID");


ALTER TABLE "SUPPLIER" ADD CONSTRAINT "WORKS FOR" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID");


ALTER TABLE "LOCATION" ADD CONSTRAINT "IS BASED" FOREIGN KEY ("CLIENT_ID", "PROJECT_ID") REFERENCES "PROJECT" ("CLIENT_ID", "PROJECT_ID");


ALTER TABLE "LOCATION" ADD CONSTRAINT "ESTABLISHES A" FOREIGN KEY ("CITY_ID", "COUNTRY_ID") REFERENCES "CITY" ("CITY_ID", "COUNTRY_ID");


ALTER TABLE "STAFF_ASSIGNMENT" ADD CONSTRAINT "IS COMPOSED OF" FOREIGN KEY ("CLIENT_ID", "PROJECT_ID") REFERENCES "PROJECT" ("CLIENT_ID", "PROJECT_ID");


ALTER TABLE "STAFF_ASSIGNMENT" ADD CONSTRAINT "WORKS ON" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID");


ALTER TABLE "STAFF_ASSIGNMENT" ADD CONSTRAINT "ASSIGNS" FOREIGN KEY ("ASSIGNMENT_ID") REFERENCES "ASSIGNMENT" ("ASSIGNMENT_ID");

COMMIT;