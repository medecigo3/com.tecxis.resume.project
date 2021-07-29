-- Create sequences section -------------------------------------------------

CREATE SEQUENCE IF NOT EXISTS "CITY_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS  "PROJECT_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS "CLIENT_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS "COUNTRY_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS "ASSIGNMENT_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS "SKILL_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS "STAFF_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS "SERVICE_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS "INTEREST_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS "SUPPLIER_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;


CREATE SEQUENCE IF NOT EXISTS "CONTRACT_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;
 
 
 CREATE SEQUENCE IF NOT EXISTS "COURSE_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;
 
 CREATE SEQUENCE IF NOT EXISTS "EMPLOYMENT_CONTRACT_SEQ"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20;

-- Create tables section -------------------------------------------------

-- Table PROJECT

CREATE TABLE IF NOT EXISTS "PROJECT"(
  "PROJECT_ID" Integer NOT NULL,
  "NAME" Varchar2(30 ) NOT NULL,
  "VERSION" Varchar2(30 ) NOT NULL,
  "CLIENT_ID" Integer NOT NULL,
  "DESC" Varchar2(400 )
);


-- Add keys for table PROJECT

ALTER TABLE "PROJECT" ADD CONSTRAINT IF NOT EXISTS "PK_PROJECT" PRIMARY KEY ("CLIENT_ID","PROJECT_ID");


ALTER TABLE "PROJECT" ADD CONSTRAINT IF NOT EXISTS "AK_PROJECT" UNIQUE ("NAME","VERSION");

-- Table CLIENT

CREATE TABLE IF NOT EXISTS "CLIENT"(
  "CLIENT_ID" Integer NOT NULL,
  "NAME" Varchar2(50 ) NOT NULL,
  "WEBSITE" Varchar2(100 )
);


-- Add keys for table CLIENT

ALTER TABLE "CLIENT" ADD CONSTRAINT IF NOT EXISTS "PK_CUSTOMER_ID" PRIMARY KEY ("CLIENT_ID");


ALTER TABLE "CLIENT" ADD CONSTRAINT IF NOT EXISTS "AK_CLIENT" UNIQUE ("NAME");

-- Table CONTRACT

CREATE TABLE IF NOT EXISTS "CONTRACT"(
  "CONTRACT_ID" Integer NOT NULL,
  "CLIENT_ID" Integer NOT NULL,
  "NAME" Varchar2(30 ) NOT NULL
);


-- Add keys for table CONTRACT

ALTER TABLE "CONTRACT" ADD CONSTRAINT IF NOT EXISTS "PK_CONTRACT" PRIMARY KEY ("CLIENT_ID","CONTRACT_ID");

ALTER TABLE "CONTRACT" ADD CONSTRAINT IF NOT EXISTS "AK_CONTRACT_NAME" UNIQUE ("NAME");

-- Table COUNTRY

CREATE TABLE IF NOT EXISTS "COUNTRY"(
  "COUNTRY_ID" Integer NOT NULL,
  "NAME" Varchar2(30 ) NOT NULL
);


-- Add keys for table COUNTRY

ALTER TABLE "COUNTRY" ADD CONSTRAINT IF NOT EXISTS "PK_COUNTRY_ID" PRIMARY KEY ("COUNTRY_ID");

ALTER TABLE "COUNTRY" ADD CONSTRAINT IF NOT EXISTS "AK_COUNTRY_NAME" UNIQUE ("NAME");

-- Table STAFF_PROJECT_ASSIGNMENT

CREATE TABLE IF NOT EXISTS "STAFF_PROJECT_ASSIGNMENT"(
  "ASSIGNMENT_ID" Integer NOT NULL,
  "PROJECT_ID" Integer NOT NULL,
  "CLIENT_ID" Integer NOT NULL,
  "STAFF_ID" Integer NOT NULL
);


-- Add keys for table STAFF_PROJECT_ASSIGNMENT

ALTER TABLE "STAFF_PROJECT_ASSIGNMENT" ADD CONSTRAINT IF NOT EXISTS "PK_ASSIGNMENT_ID" PRIMARY KEY ("ASSIGNMENT_ID","PROJECT_ID","STAFF_ID","CLIENT_ID");


-- Table SKILL

CREATE TABLE IF NOT EXISTS "SKILL"(
  "SKILL_ID" Integer NOT NULL,
  "NAME" Varchar2(30 ) NOT NULL
);


-- Add keys for table SKILL

ALTER TABLE "SKILL" ADD CONSTRAINT IF NOT EXISTS "PK_SKILL_ID" PRIMARY KEY ("SKILL_ID");

ALTER TABLE "SKILL" ADD CONSTRAINT IF NOT EXISTS "AK_SKILL_NAME" UNIQUE ("NAME");

-- Table STAFF

CREATE TABLE IF NOT EXISTS "STAFF"(
  "STAFF_ID" Integer NOT NULL,
  "FIRSTNAME" Varchar2(30 ) NOT NULL,
  "LASTNAME" Varchar2(30 ) NOT NULL,
  "BIRTH_DATE" Date NOT NULL
);


-- Add keys for table STAFF

ALTER TABLE "STAFF" ADD CONSTRAINT IF NOT EXISTS "PK_STAFF_ID" PRIMARY KEY ("STAFF_ID");

ALTER TABLE "STAFF" ADD CONSTRAINT IF NOT EXISTS "AK_STAFF_NAME" UNIQUE ("FIRSTNAME","LASTNAME","BIRTH_DATE");

-- Table SERVICE

CREATE TABLE IF NOT EXISTS "SERVICE"(
  "SERVICE_ID" Integer NOT NULL,
  "NAME" Varchar2(50 ) NOT NULL,
  "DESC" Varchar2(400 )
);


-- Add keys for table SERVICE

ALTER TABLE "SERVICE" ADD CONSTRAINT IF NOT EXISTS "PK_SERVICE_ID" PRIMARY KEY ("SERVICE_ID");

ALTER TABLE "SERVICE" ADD CONSTRAINT IF NOT EXISTS "AK_SERVICE" UNIQUE ("NAME");

-- Table INTEREST

CREATE TABLE IF NOT EXISTS "INTEREST"(
  "INTEREST_ID" Integer NOT NULL,
  "STAFF_ID" Integer,
  "DESC" Varchar2(500 )
);

-- Add keys for table INTEREST

ALTER TABLE "INTEREST" ADD CONSTRAINT IF NOT EXISTS "PK_INTEREST_ID" PRIMARY KEY ("INTEREST_ID");


-- Table CITY

CREATE TABLE IF NOT EXISTS "CITY"(
  "CITY_ID" Integer NOT NULL,
  "COUNTRY_ID" Integer NOT NULL,
  "NAME" Varchar2(30 ) NOT NULL
);


-- Add keys for table CITY

ALTER TABLE "CITY" ADD CONSTRAINT IF NOT EXISTS "PK_CITY" PRIMARY KEY ("CITY_ID","COUNTRY_ID");

ALTER TABLE "CITY" ADD CONSTRAINT IF NOT EXISTS "AK_CITY_NAME" UNIQUE ("NAME");

-- Table STAFF_SKILL

CREATE TABLE IF NOT EXISTS "STAFF_SKILL"(
  "STAFF_ID" Integer NOT NULL,
  "SKILL_ID" Integer NOT NULL,
  "YEARS_OF_EXPERIENCE" Integer,
  "ENDORSEMENT" Integer
);


-- Add keys for table STAFF_SKILL

ALTER TABLE "STAFF_SKILL" ADD CONSTRAINT IF NOT EXISTS "PK_SKILL_USED" PRIMARY KEY ("STAFF_ID","SKILL_ID");


-- Table COURSE

CREATE TABLE IF NOT EXISTS "COURSE"(
  "COURSE_ID" Integer NOT NULL,
  "TITLE" Varchar2(100 ) NOT NULL,
  "CREDITS" Integer
);


-- Add keys for table COURSE

ALTER TABLE "COURSE" ADD CONSTRAINT IF NOT EXISTS "PK_COURSE_ID" PRIMARY KEY ("COURSE_ID");

ALTER TABLE "COURSE" ADD CONSTRAINT IF NOT EXISTS "AK_COURSE" UNIQUE ("TITLE");

-- Table ENROLMENT

CREATE TABLE IF NOT EXISTS "ENROLMENT"(
  "COURSE_ID" Integer NOT NULL,
  "STAFF_ID" Integer NOT NULL,
  "GRADE" Varchar2(30 ),
  "START_DATE" Date,
  "END_DATE" Date,
  "DURATION" Integer
);


-- Add keys for table ENROLMENT

ALTER TABLE "ENROLMENT" ADD CONSTRAINT IF NOT EXISTS "PK_ENROLLMENT" PRIMARY KEY ("COURSE_ID","STAFF_ID");


-- Table SUPPLIER

CREATE TABLE IF NOT EXISTS "SUPPLIER"(
  "SUPPLIER_ID" Integer NOT NULL,
  "NAME" Varchar2(30 ) NOT NULL
);


-- Add keys for table SUPPLIER

ALTER TABLE "SUPPLIER" ADD CONSTRAINT IF NOT EXISTS "SUPPLIER_PK" PRIMARY KEY ("SUPPLIER_ID");

ALTER TABLE "SUPPLIER" ADD CONSTRAINT IF NOT EXISTS "AK_SUPPLIER_NAME" UNIQUE ("NAME");

-- Table LOCATION

CREATE TABLE IF NOT EXISTS "LOCATION"(
  "PROJECT_ID" Integer NOT NULL,
  "CLIENT_ID" Integer NOT NULL,
  "CITY_ID" Integer NOT NULL,
  "COUNTRY_ID" Integer NOT NULL
);


-- Add keys for table LOCATION

ALTER TABLE "LOCATION" ADD CONSTRAINT IF NOT EXISTS "PK_LOCATION" PRIMARY KEY ("CLIENT_ID","CITY_ID","COUNTRY_ID","PROJECT_ID");

-- Table ASSIGNMENT

CREATE TABLE IF NOT EXISTS "ASSIGNMENT"(
  "ASSIGNMENT_ID" Integer NOT NULL,
  "DESC" Varchar2(255 ) NOT NULL,
  "PRIORITY" Integer
);

-- Add keys for table ASSIGNMENT

ALTER TABLE "ASSIGNMENT" ADD CONSTRAINT IF NOT EXISTS "PK_ASSIGNMENT" PRIMARY KEY ("ASSIGNMENT_ID");


ALTER TABLE "ASSIGNMENT" ADD CONSTRAINT IF NOT EXISTS "AK_ASSIGNMENT" UNIQUE ("DESC");

-- Table CONTRACT_SERVICE_AGREEMENT

CREATE TABLE IF NOT EXISTS "CONTRACT_SERVICE_AGREEMENT"(
  "CLIENT_ID" Integer NOT NULL,
  "CONTRACT_ID" Integer NOT NULL,
  "SERVICE_ID" Integer NOT NULL
);

-- Add keys for table CONTRACT_SERVICE_AGREEMENT

ALTER TABLE "CONTRACT_SERVICE_AGREEMENT" ADD CONSTRAINT IF NOT EXISTS "Key1" PRIMARY KEY ("CLIENT_ID","CONTRACT_ID","SERVICE_ID");

-- Table EMPLOYMENT_CONTRACT

CREATE TABLE IF NOT EXISTS "EMPLOYMENT_CONTRACT"(
  "EMPLOYMENT_CONTRACT_ID" Integer NOT NULL,
  "STAFF_ID" Integer NOT NULL,
  "SUPPLIER_ID" Integer NOT NULL,
  "START_DATE" Date NOT NULL,
  "END_DATE" Date
);

-- Add keys for table EMPLOYMENT_CONTRACT

ALTER TABLE "EMPLOYMENT_CONTRACT" ADD CONSTRAINT IF NOT EXISTS "PK_EMPLOYMENT_CONTRACT" PRIMARY KEY ("EMPLOYMENT_CONTRACT_ID","STAFF_ID","SUPPLIER_ID");

-- Table SUPPLY_CONTRACT

CREATE TABLE IF NOT EXISTS "SUPPLY_CONTRACT"(
  "SUPPLIER_ID" Integer NOT NULL,
  "CLIENT_ID" Integer NOT NULL,
  "CONTRACT_ID" Integer NOT NULL,
  "STAFF_ID" Integer NOT NULL,
  "START_DATE" Date NOT NULL,
  "END_DATE" Date
)
;

-- Add keys for table SUPPLY_CONTRACT

ALTER TABLE "SUPPLY_CONTRACT" ADD CONSTRAINT IF NOT EXISTS "SUPPLY_CONTRACT_PK" PRIMARY KEY ("SUPPLIER_ID","CLIENT_ID","CONTRACT_ID","STAFF_ID");

-- Create views section -------------------------------------------------

CREATE VIEW IF NOT EXISTS "v_ProjectTask" AS
SELECT "PROJECT"."PROJECT_ID", "VERSION", "FIRSTNAME", "LASTNAME", "NAME" AS "PROJECT", "VERSION" AS "PROJECT_VERSION", "ASSIGNMENT"."DESC" AS "TASK"
FROM "STAFF_PROJECT_ASSIGNMENT", "PROJECT", "STAFF", "ASSIGNMENT"
WHERE PROJECT.PROJECT_ID = STAFF_PROJECT_ASSIGNMENT.PROJECT_ID AND
PROJECT.CLIENT_ID = STAFF_PROJECT_ASSIGNMENT.CLIENT_ID AND
STAFF_PROJECT_ASSIGNMENT.ASSIGNMENT_ID = ASSIGNMENT.ASSIGNMENT_ID AND
STAFF.STAFF_ID = STAFF_PROJECT_ASSIGNMENT.STAFF_ID
ORDER BY PROJECT.NAME;


CREATE VIEW IF NOT EXISTS "v_Experience" AS
SELECT "CONTRACT"."CONTRACT_ID", "FIRSTNAME", "LASTNAME", "SERVICE"."NAME" AS "ROLE", "CLIENT"."NAME" AS "CUSTOMER", "CITY"."NAME" AS "CITY", "COUNTRY"."NAME" AS "COUNTRY"
FROM "SERVICE", "CLIENT", "CONTRACT", "STAFF", "PROJECT", "COUNTRY", "CITY", "LOCATION", "STAFF_PROJECT_ASSIGNMENT", "SUPPLIER", "CONTRACT_SERVICE_AGREEMENT", "EMPLOYMENT_CONTRACT", "SUPPLY_CONTRACT"
WHERE CITY.COUNTRY_ID = COUNTRY.COUNTRY_ID AND
LOCATION.CITY_ID = CITY.CITY_ID AND 
LOCATION.COUNTRY_ID = CITY.COUNTRY_ID AND 
LOCATION.PROJECT_ID = PROJECT.PROJECT_ID AND
PROJECT.CLIENT_ID = CLIENT.CLIENT_ID AND
CLIENT.CLIENT_ID = CONTRACT.CLIENT_ID AND
PROJECT.PROJECT_ID = STAFF_PROJECT_ASSIGNMENT.PROJECT_ID AND
PROJECT.CLIENT_ID = STAFF_PROJECT_ASSIGNMENT.CLIENT_ID AND
STAFF_PROJECT_ASSIGNMENT.STAFF_ID = STAFF.STAFF_ID AND
CONTRACT.CONTRACT_ID = CONTRACT_SERVICE_AGREEMENT.CONTRACT_ID AND
CONTRACT.CLIENT_ID  = CONTRACT_SERVICE_AGREEMENT.CLIENT_ID AND
CONTRACT_SERVICE_AGREEMENT.SERVICE_ID = SERVICE.SERVICE_ID  AND
CONTRACT.CONTRACT_ID = SUPPLY_CONTRACT.CONTRACT_ID AND
CONTRACT.CLIENT_ID = SUPPLY_CONTRACT.CLIENT_ID AND
SUPPLIER.SUPPLIER_ID = SUPPLY_CONTRACT.SUPPLIER_ID AND
SUPPLIER.SUPPLIER_ID = EMPLOYMENT_CONTRACT.SUPPLIER_ID AND
EMPLOYMENT_CONTRACT.STAFF_ID = STAFF.STAFF_ID
GROUP BY "CONTRACT"."CONTRACT_ID", "FIRSTNAME", "LASTNAME", "SERVICE"."NAME", "CLIENT"."NAME", "CITY"."NAME", "COUNTRY"."NAME";

CREATE VIEW IF NOT EXISTS "v_Resume" AS
SELECT "CONTRACT"."CONTRACT_ID", "CONTRACT"."NAME" AS "CONTRACT", "FIRSTNAME", "LASTNAME", "SERVICE"."NAME" AS "ROLE", "CLIENT"."NAME" AS "CUSTOMER", "SUPPLIER"."NAME" AS "SUPPLIER", "CITY"."NAME" AS "CITY", "COUNTRY"."NAME" AS "COUNTRY", "PROJECT"."NAME" AS "PROJECT", "ASSIGNMENT"."DESC" AS "RESPONSIBILITY", "START_DATE", "END_DATE"
FROM "CLIENT", "COUNTRY", "PROJECT", "CONTRACT", "STAFF_PROJECT_ASSIGNMENT", "SERVICE", "STAFF", "CITY", "SUPPLIER", "LOCATION", "ASSIGNMENT", "CONTRACT_SERVICE_AGREEMENT", "SUPPLY_CONTRACT"
WHERE CITY.COUNTRY_ID                         = COUNTRY.COUNTRY_ID AND
LOCATION.CITY_ID                        = CITY.CITY_ID AND 
LOCATION.COUNTRY_ID                     = CITY.COUNTRY_ID AND 
LOCATION.PROJECT_ID                     = PROJECT.PROJECT_ID AND
PROJECT.CLIENT_ID                       = CLIENT.CLIENT_ID AND
CLIENT.CLIENT_ID                        = CONTRACT.CLIENT_ID AND
PROJECT.PROJECT_ID                      = STAFF_PROJECT_ASSIGNMENT.PROJECT_ID AND
PROJECT.CLIENT_ID                       = STAFF_PROJECT_ASSIGNMENT.CLIENT_ID AND
STAFF_PROJECT_ASSIGNMENT.STAFF_ID       = STAFF.STAFF_ID AND
CONTRACT.CONTRACT_ID                    = CONTRACT_SERVICE_AGREEMENT.CONTRACT_ID AND
CONTRACT.CLIENT_ID                      = CONTRACT_SERVICE_AGREEMENT.CLIENT_ID AND
CONTRACT_SERVICE_AGREEMENT.SERVICE_ID   = SERVICE.SERVICE_ID  AND
CONTRACT.CONTRACT_ID                    = SUPPLY_CONTRACT.CONTRACT_ID AND
CONTRACT.CLIENT_ID                      = SUPPLY_CONTRACT.CLIENT_ID AND
SUPPLIER.SUPPLIER_ID                    = SUPPLY_CONTRACT.SUPPLIER_ID AND
STAFF.STAFF_ID                          = SUPPLY_CONTRACT.STAFF_ID AND
ASSIGNMENT.ASSIGNMENT_ID                = STAFF_PROJECT_ASSIGNMENT.ASSIGNMENT_ID
GROUP BY "CONTRACT"."CONTRACT_ID", "CONTRACT"."NAME", "FIRSTNAME", "LASTNAME", "SERVICE"."NAME", "CLIENT"."NAME", "CITY"."NAME", "COUNTRY"."NAME", "PROJECT"."NAME", "ASSIGNMENT"."DESC", "SUPPLIER"."NAME", "START_DATE", "END_DATE";

-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE "CONTRACT" ADD CONSTRAINT IF NOT EXISTS "SIGNS" FOREIGN KEY ("CLIENT_ID") REFERENCES "CLIENT" ("CLIENT_ID");
ALTER TABLE "CITY" ADD CONSTRAINT IF NOT EXISTS "HAS A" FOREIGN KEY ("COUNTRY_ID") REFERENCES "COUNTRY" ("COUNTRY_ID");
ALTER TABLE "INTEREST" ADD CONSTRAINT IF NOT EXISTS "HAS" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID") ON DELETE CASCADE;
ALTER TABLE "STAFF_SKILL" ADD CONSTRAINT IF NOT EXISTS "USES" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID") ON DELETE CASCADE;
ALTER TABLE "STAFF_SKILL" ADD CONSTRAINT IF NOT EXISTS "IS USED" FOREIGN KEY ("SKILL_ID") REFERENCES "SKILL" ("SKILL_ID") ON DELETE CASCADE;
ALTER TABLE "ENROLMENT" ADD CONSTRAINT IF NOT EXISTS "IN" FOREIGN KEY ("COURSE_ID") REFERENCES "COURSE" ("COURSE_ID") ON DELETE CASCADE;
ALTER TABLE "ENROLMENT" ADD CONSTRAINT IF NOT EXISTS "ENROLS" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID") ON DELETE CASCADE;
ALTER TABLE "PROJECT" ADD CONSTRAINT IF NOT EXISTS "CONTROLS" FOREIGN KEY ("CLIENT_ID") REFERENCES "CLIENT" ("CLIENT_ID");
ALTER TABLE "LOCATION" ADD CONSTRAINT IF NOT EXISTS "IS BASED" FOREIGN KEY ("CLIENT_ID", "PROJECT_ID") REFERENCES "PROJECT" ("CLIENT_ID", "PROJECT_ID") ON DELETE CASCADE;
ALTER TABLE "LOCATION" ADD CONSTRAINT IF NOT EXISTS "ESTABLISHES A" FOREIGN KEY ("CITY_ID", "COUNTRY_ID") REFERENCES "CITY" ("CITY_ID", "COUNTRY_ID") ON DELETE CASCADE;
ALTER TABLE "STAFF_PROJECT_ASSIGNMENT" ADD CONSTRAINT IF NOT EXISTS "IS COMPOSED OF" FOREIGN KEY ("CLIENT_ID", "PROJECT_ID") REFERENCES "PROJECT" ("CLIENT_ID", "PROJECT_ID") ON DELETE CASCADE;
ALTER TABLE "STAFF_PROJECT_ASSIGNMENT" ADD CONSTRAINT IF NOT EXISTS "WORKS ON" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID") ON DELETE CASCADE;
ALTER TABLE "STAFF_PROJECT_ASSIGNMENT" ADD CONSTRAINT IF NOT EXISTS "ASSIGNS" FOREIGN KEY ("ASSIGNMENT_ID") REFERENCES "ASSIGNMENT" ("ASSIGNMENT_ID") ON DELETE CASCADE;
ALTER TABLE "CONTRACT_SERVICE_AGREEMENT" ADD CONSTRAINT IF NOT EXISTS "ENGAGES" FOREIGN KEY ("CLIENT_ID", "CONTRACT_ID") REFERENCES "CONTRACT" ("CLIENT_ID", "CONTRACT_ID") ON DELETE CASCADE;
ALTER TABLE "CONTRACT_SERVICE_AGREEMENT" ADD CONSTRAINT IF NOT EXISTS "PROVIDES" FOREIGN KEY ("SERVICE_ID") REFERENCES "SERVICE" ("SERVICE_ID") ON DELETE CASCADE;
ALTER TABLE "EMPLOYMENT_CONTRACT" ADD CONSTRAINT IF NOT EXISTS "IS EMPLOYED" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID") ON DELETE CASCADE;
ALTER TABLE "EMPLOYMENT_CONTRACT" ADD CONSTRAINT IF NOT EXISTS "EMPLOYS" FOREIGN KEY ("SUPPLIER_ID") REFERENCES "SUPPLIER" ("SUPPLIER_ID") ON DELETE CASCADE;
ALTER TABLE "SUPPLY_CONTRACT" ADD CONSTRAINT IF NOT EXISTS "AGREES" FOREIGN KEY ("SUPPLIER_ID") REFERENCES "SUPPLIER" ("SUPPLIER_ID") ON DELETE CASCADE;
ALTER TABLE "SUPPLY_CONTRACT" ADD CONSTRAINT IF NOT EXISTS "COMMITS TO" FOREIGN KEY ("CLIENT_ID", "CONTRACT_ID") REFERENCES "CONTRACT" ("CLIENT_ID", "CONTRACT_ID") ON DELETE CASCADE;
ALTER TABLE "SUPPLY_CONTRACT" ADD CONSTRAINT IF NOT EXISTS "WORKS IN" FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("STAFF_ID") ON DELETE CASCADE;
COMMIT;