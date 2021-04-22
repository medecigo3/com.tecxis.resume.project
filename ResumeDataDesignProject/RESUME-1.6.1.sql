--<ScriptOptions statementTerminator=";"/>

ALTER TABLE "AGREEMENT" DROP CONSTRAINT "ENGAGES";

ALTER TABLE "AGREEMENT" DROP CONSTRAINT "PROVIDES";

ALTER TABLE "CITY" DROP CONSTRAINT "HAS A";

ALTER TABLE "CONTRACT" DROP CONSTRAINT "SIGNS";

ALTER TABLE "EMPLOYMENT_CONTRACT" DROP CONSTRAINT "EMPLOYS";

ALTER TABLE "EMPLOYMENT_CONTRACT" DROP CONSTRAINT "IS EMPLOYED";

ALTER TABLE "ENROLMENT" DROP CONSTRAINT "ENROLS";

ALTER TABLE "ENROLMENT" DROP CONSTRAINT "IN";

ALTER TABLE "INTEREST" DROP CONSTRAINT "HAS";

ALTER TABLE "LOCATION" DROP CONSTRAINT "ESTABLISHES A";

ALTER TABLE "LOCATION" DROP CONSTRAINT "IS BASED";

ALTER TABLE "PROJECT" DROP CONSTRAINT "CONTROLS";

ALTER TABLE "STAFF_ASSIGNMENTS" DROP CONSTRAINT "ASSIGNS";

ALTER TABLE "STAFF_ASSIGNMENTS" DROP CONSTRAINT "IS COMPOSED OF";

ALTER TABLE "STAFF_ASSIGNMENTS" DROP CONSTRAINT "WORKS ON";

ALTER TABLE "STAFF_SKILL" DROP CONSTRAINT "IS USED";

ALTER TABLE "STAFF_SKILL" DROP CONSTRAINT "USES";

ALTER TABLE "SUPPLY_CONTRACT" DROP CONSTRAINT "AGREES";

ALTER TABLE "SUPPLY_CONTRACT" DROP CONSTRAINT "COMMITS TO";

ALTER TABLE "SUPPLY_CONTRACT" DROP CONSTRAINT "WORKS IN";

ALTER TABLE "AGREEMENT" DROP CONSTRAINT "AGREEMENT_PK";

ALTER TABLE "ASSIGNMENT" DROP CONSTRAINT "ASSIGNMENT_AK";

ALTER TABLE "ASSIGNMENT" DROP CONSTRAINT "ASSIGNMENT_PK";

ALTER TABLE "CITY" DROP CONSTRAINT "CITY_AK";

ALTER TABLE "CITY" DROP CONSTRAINT "CITY_PK";

ALTER TABLE "CLIENT" DROP CONSTRAINT "CLIENT_AK";

ALTER TABLE "CLIENT" DROP CONSTRAINT "CLIENT_PK";

ALTER TABLE "CONTRACT" DROP CONSTRAINT "CONTRACT_AK";

ALTER TABLE "CONTRACT" DROP CONSTRAINT "CONTRACT_PK";

ALTER TABLE "COUNTRY" DROP CONSTRAINT "COUNTRY_AK";

ALTER TABLE "COUNTRY" DROP CONSTRAINT "COUNTRY_PK";

ALTER TABLE "COURSE" DROP CONSTRAINT "COURSE_AK";

ALTER TABLE "COURSE" DROP CONSTRAINT "COURSE_PK";

ALTER TABLE "EMPLOYMENT_CONTRACT" DROP CONSTRAINT "EMPLOYMENT_CONTRACT_PK";

ALTER TABLE "ENROLMENT" DROP CONSTRAINT "ENROLMENT_PK";

ALTER TABLE "INTEREST" DROP CONSTRAINT "INTEREST_PK";

ALTER TABLE "LOCATION" DROP CONSTRAINT "LOCATION_PK";

ALTER TABLE "PROJECT" DROP CONSTRAINT "PROJECT_AK";

ALTER TABLE "PROJECT" DROP CONSTRAINT "PROJECT_PK";

ALTER TABLE "SERVICE" DROP CONSTRAINT "SERVICE_AK";

ALTER TABLE "SERVICE" DROP CONSTRAINT "SERVICE_PK";

ALTER TABLE "SKILL" DROP CONSTRAINT "SKILL_AK";

ALTER TABLE "SKILL" DROP CONSTRAINT "SKILL_PK";

ALTER TABLE "STAFF" DROP CONSTRAINT "STAFF_AK";

ALTER TABLE "STAFF" DROP CONSTRAINT "STAFF_PK";

ALTER TABLE "STAFF_ASSIGNMENTS" DROP CONSTRAINT "STAFF_ASSIGNMENTS_PK";

ALTER TABLE "STAFF_SKILL" DROP CONSTRAINT "STAFF SKILL_PK";

ALTER TABLE "SUPPLIER" DROP CONSTRAINT "SUPPLIER_AK";

ALTER TABLE "SUPPLIER" DROP CONSTRAINT "SUPPLIER_PK";

ALTER TABLE "SUPPLY_CONTRACT" DROP CONSTRAINT "SUPPLY_CONTRACT_PK";

DROP VIEW "v_Experience";

DROP VIEW "v_ProjectTask";

DROP VIEW "v_Resume";

DROP TABLE "AGREEMENT";

DROP TABLE "ASSIGNMENT";

DROP TABLE "CITY";

DROP TABLE "CLIENT";

DROP TABLE "CONTRACT";

DROP TABLE "COUNTRY";

DROP TABLE "COURSE";

DROP TABLE "EMPLOYMENT_CONTRACT";

DROP TABLE "ENROLMENT";

DROP TABLE "INTEREST";

DROP TABLE "LOCATION";

DROP TABLE "PROJECT";

DROP TABLE "SERVICE";

DROP TABLE "SKILL";

DROP TABLE "STAFF";

DROP TABLE "STAFF_ASSIGNMENTS";

DROP TABLE "STAFF_SKILL";

DROP TABLE "SUPPLIER";

DROP TABLE "SUPPLY_CONTRACT";

DROP SEQUENCE "ASSIGNMENT_SEQ";

DROP SEQUENCE "CITY_SEQ";

DROP SEQUENCE "CLIENT_SEQ";

DROP SEQUENCE "CONTRACT_SEQ";

DROP SEQUENCE "COUNTRY_SEQ";

DROP SEQUENCE "COURSE_SEQ";

DROP SEQUENCE "EMPLOYMENT_CONTRACT_SEQ";

DROP SEQUENCE "INTEREST_SEQ";

DROP SEQUENCE "PROJECT_SEQ";

DROP SEQUENCE "SERVICE_SEQ";

DROP SEQUENCE "SKILL_SEQ";

DROP SEQUENCE "STAFF_SEQ";

DROP SEQUENCE "SUPPLIER_SEQ";

CREATE SEQUENCE "ASSIGNMENT_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "CITY_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "CLIENT_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "CONTRACT_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "COUNTRY_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "COURSE_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "EMPLOYMENT_CONTRACT_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "INTEREST_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "PROJECT_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "SERVICE_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "SKILL_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "STAFF_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE SEQUENCE "SUPPLIER_SEQ"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9999999999999999999999999999
	START WITH 1;

CREATE TABLE "AGREEMENT" (
		"CLIENT_ID" NUMBER(38 , 0) NOT NULL,
		"CONTRACT_ID" NUMBER(38 , 0) NOT NULL,
		"SERVICE_ID" NUMBER(38 , 0) NOT NULL
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "ASSIGNMENT" (
		"ASSIGNMENT_ID" NUMBER(38 , 0) NOT NULL,
		"DESC" VARCHAR2(255) NOT NULL,
		"PRIORITY" NUMBER(38 , 0)
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "CITY" (
		"CITY_ID" NUMBER(38 , 0) NOT NULL,
		"COUNTRY_ID" NUMBER(38 , 0) NOT NULL,
		"NAME" VARCHAR2(30) NOT NULL
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "CLIENT" (
		"CLIENT_ID" NUMBER(38 , 0) NOT NULL,
		"NAME" VARCHAR2(50) NOT NULL,
		"WEBSITE" VARCHAR2(100)
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "CONTRACT" (
		"CONTRACT_ID" NUMBER(38 , 0) NOT NULL,
		"CLIENT_ID" NUMBER(38 , 0) NOT NULL,
		"NAME" VARCHAR2(30) NOT NULL
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "COUNTRY" (
		"COUNTRY_ID" NUMBER(38 , 0) NOT NULL,
		"NAME" VARCHAR2(30) NOT NULL
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "COURSE" (
		"COURSE_ID" NUMBER(38 , 0) NOT NULL,
		"TITLE" VARCHAR2(100) NOT NULL,
		"CREDITS" NUMBER(38 , 0)
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "EMPLOYMENT_CONTRACT" (
		"EMPLOYMENT_CONTRACT_ID" NUMBER(38 , 0) NOT NULL,
		"STAFF_ID" NUMBER(38 , 0) NOT NULL,
		"SUPPLIER_ID" NUMBER(38 , 0) NOT NULL,
		"START_DATE" DATE NOT NULL,
		"END_DATE" DATE
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "ENROLMENT" (
		"COURSE_ID" NUMBER(38 , 0) NOT NULL,
		"STAFF_ID" NUMBER(38 , 0) NOT NULL,
		"GRADE" VARCHAR2(30),
		"START_DATE" DATE,
		"END_DATE" DATE,
		"DURATION" NUMBER(38 , 0)
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "INTEREST" (
		"INTEREST_ID" NUMBER(38 , 0) NOT NULL,
		"STAFF_ID" NUMBER(38 , 0),
		"DESC" VARCHAR2(500)
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "LOCATION" (
		"PROJECT_ID" NUMBER(38 , 0) NOT NULL,
		"CLIENT_ID" NUMBER(38 , 0) NOT NULL,
		"CITY_ID" NUMBER(38 , 0) NOT NULL,
		"COUNTRY_ID" NUMBER(38 , 0) NOT NULL
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "PROJECT" (
		"PROJECT_ID" NUMBER(38 , 0) NOT NULL,
		"NAME" VARCHAR2(30) NOT NULL,
		"VERSION" VARCHAR2(30) NOT NULL,
		"CLIENT_ID" NUMBER(38 , 0) NOT NULL,
		"DESC" VARCHAR2(400)
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "SERVICE" (
		"SERVICE_ID" NUMBER(38 , 0) NOT NULL,
		"NAME" VARCHAR2(50) NOT NULL,
		"DESC" VARCHAR2(400)
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "SKILL" (
		"SKILL_ID" NUMBER(38 , 0) NOT NULL,
		"NAME" VARCHAR2(30) NOT NULL
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "STAFF" (
		"STAFF_ID" NUMBER(38 , 0) NOT NULL,
		"FIRSTNAME" VARCHAR2(30) NOT NULL,
		"LASTNAME" VARCHAR2(30) NOT NULL,
		"BIRTH_DATE" DATE NOT NULL
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "STAFF_ASSIGNMENTS" (
		"ASSIGNMENT_ID" NUMBER(38 , 0) NOT NULL,
		"PROJECT_ID" NUMBER(38 , 0) NOT NULL,
		"CLIENT_ID" NUMBER(38 , 0) NOT NULL,
		"STAFF_ID" NUMBER(38 , 0) NOT NULL
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "STAFF_SKILL" (
		"STAFF_ID" NUMBER(38 , 0) NOT NULL,
		"SKILL_ID" NUMBER(38 , 0) NOT NULL,
		"YEARS_OF_EXPERIENCE" NUMBER(38 , 0),
		"ENDORSEMENT" NUMBER(38 , 0)
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "SUPPLIER" (
		"SUPPLIER_ID" NUMBER(38 , 0) NOT NULL,
		"NAME" VARCHAR2(30) NOT NULL
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE TABLE "SUPPLY_CONTRACT" (
		"SUPPLIER_ID" NUMBER(38 , 0) NOT NULL,
		"CLIENT_ID" NUMBER(38 , 0) NOT NULL,
		"CONTRACT_ID" NUMBER(38 , 0) NOT NULL,
		"STAFF_ID" NUMBER(38 , 0) NOT NULL,
		"START_DATE" DATE NOT NULL,
		"END_DATE" DATE
	)
	PCTFREE 0
	PCTUSED 0
	LOGGING
	NOCOMPRESS;

CREATE INDEX "AGREEMENT_PK_IDX"
	ON "AGREEMENT" ("CLIENT_ID","CONTRACT_ID","SERVICE_ID");

CREATE INDEX "ASSIGNMENT_AK_IDX"
	ON "ASSIGNMENT" ("DESC");

CREATE INDEX "ASSIGNMENT_PK_IDX"
	ON "ASSIGNMENT" ("ASSIGNMENT_ID");

CREATE INDEX "CITY_AK_IDX"
	ON "CITY" ("NAME");

CREATE INDEX "CITY_PK_IDX"
	ON "CITY" ("CITY_ID","COUNTRY_ID");

CREATE INDEX "CLIENT_AK_IDX"
	ON "CLIENT" ("NAME");

CREATE INDEX "CLIENT_PK_IDX"
	ON "CLIENT" ("CLIENT_ID");

CREATE INDEX "CONTRACT_AK_IDX"
	ON "CONTRACT" ("NAME");

CREATE INDEX "CONTRACT_PK_IDX"
	ON "CONTRACT" ("CLIENT_ID","CONTRACT_ID");

CREATE INDEX "COUNTRY_AK_IDX"
	ON "COUNTRY" ("NAME");

CREATE INDEX "COUNTRY_PK_IDX"
	ON "COUNTRY" ("COUNTRY_ID");

CREATE INDEX "COURSE_AK_IDX"
	ON "COURSE" ("TITLE");

CREATE INDEX "COURSE_PK_IDX"
	ON "COURSE" ("COURSE_ID");

CREATE INDEX "EMPLOYMENT_CONTRACT_PK_IDX"
	ON "EMPLOYMENT_CONTRACT" ("EMPLOYMENT_CONTRACT_ID","STAFF_ID","SUPPLIER_ID");

CREATE INDEX "ENROLMENT_PK_IDX"
	ON "ENROLMENT" ("COURSE_ID","STAFF_ID");

CREATE INDEX "INTEREST_PK_IDX"
	ON "INTEREST" ("INTEREST_ID");

CREATE INDEX "LOCATION_PK_IDX"
	ON "LOCATION" ("CLIENT_ID","CITY_ID","COUNTRY_ID","PROJECT_ID");

CREATE INDEX "PROJECT_AK_IDX"
	ON "PROJECT" ("NAME","VERSION");

CREATE INDEX "PROJECT_PK_IDX"
	ON "PROJECT" ("CLIENT_ID","PROJECT_ID");

CREATE INDEX "SERVICE_AK_IDX"
	ON "SERVICE" ("NAME");

CREATE INDEX "SERVICE_PK_IDX"
	ON "SERVICE" ("SERVICE_ID");

CREATE INDEX "SKILL_AK_IDX"
	ON "SKILL" ("NAME");

CREATE INDEX "SKILL_PK_IDX"
	ON "SKILL" ("SKILL_ID");

CREATE INDEX "STAFF SKILL_PK_IDX"
	ON "STAFF_SKILL" ("STAFF_ID","SKILL_ID");

CREATE INDEX "STAFF_AK_IDX"
	ON "STAFF" ("FIRSTNAME","LASTNAME","BIRTH_DATE");

CREATE INDEX "STAFF_ASSIGNMENTS_PK_IDX"
	ON "STAFF_ASSIGNMENTS" ("ASSIGNMENT_ID","PROJECT_ID","STAFF_ID","CLIENT_ID");

CREATE INDEX "STAFF_PK_IDX"
	ON "STAFF" ("STAFF_ID");

CREATE INDEX "SUPPLIER_AK_IDX"
	ON "SUPPLIER" ("NAME");

CREATE INDEX "SUPPLIER_PK_IDX"
	ON "SUPPLIER" ("SUPPLIER_ID");

CREATE INDEX "SUPPLY_CONTRACT_PK_IDX"
	ON "SUPPLY_CONTRACT" ("SUPPLIER_ID","CLIENT_ID","CONTRACT_ID","STAFF_ID");

ALTER TABLE "AGREEMENT" ADD CONSTRAINT "AGREEMENT_PK" PRIMARY KEY
	("CLIENT_ID",
	 "CONTRACT_ID",
	 "SERVICE_ID") USING INDEX "AGREEMENT_PK_IDX";

ALTER TABLE "ASSIGNMENT" ADD CONSTRAINT "ASSIGNMENT_AK" UNIQUE
	("DESC") USING INDEX "ASSIGNMENT_AK_IDX";

ALTER TABLE "ASSIGNMENT" ADD CONSTRAINT "ASSIGNMENT_PK" PRIMARY KEY
	("ASSIGNMENT_ID") USING INDEX "ASSIGNMENT_PK_IDX";

ALTER TABLE "CITY" ADD CONSTRAINT "CITY_AK" UNIQUE
	("NAME") USING INDEX "CITY_AK_IDX";

ALTER TABLE "CITY" ADD CONSTRAINT "CITY_PK" PRIMARY KEY
	("CITY_ID",
	 "COUNTRY_ID") USING INDEX "CITY_PK_IDX";

ALTER TABLE "CLIENT" ADD CONSTRAINT "CLIENT_AK" UNIQUE
	("NAME") USING INDEX "CLIENT_AK_IDX";

ALTER TABLE "CLIENT" ADD CONSTRAINT "CLIENT_PK" PRIMARY KEY
	("CLIENT_ID") USING INDEX "CLIENT_PK_IDX";

ALTER TABLE "CONTRACT" ADD CONSTRAINT "CONTRACT_AK" UNIQUE
	("NAME") USING INDEX "CONTRACT_AK_IDX";

ALTER TABLE "CONTRACT" ADD CONSTRAINT "CONTRACT_PK" PRIMARY KEY
	("CLIENT_ID",
	 "CONTRACT_ID") USING INDEX "CONTRACT_PK_IDX";

ALTER TABLE "COUNTRY" ADD CONSTRAINT "COUNTRY_AK" UNIQUE
	("NAME") USING INDEX "COUNTRY_AK_IDX";

ALTER TABLE "COUNTRY" ADD CONSTRAINT "COUNTRY_PK" PRIMARY KEY
	("COUNTRY_ID") USING INDEX "COUNTRY_PK_IDX";

ALTER TABLE "COURSE" ADD CONSTRAINT "COURSE_AK" UNIQUE
	("TITLE") USING INDEX "COURSE_AK_IDX";

ALTER TABLE "COURSE" ADD CONSTRAINT "COURSE_PK" PRIMARY KEY
	("COURSE_ID") USING INDEX "COURSE_PK_IDX";

ALTER TABLE "EMPLOYMENT_CONTRACT" ADD CONSTRAINT "EMPLOYMENT_CONTRACT_PK" PRIMARY KEY
	("EMPLOYMENT_CONTRACT_ID",
	 "STAFF_ID",
	 "SUPPLIER_ID") USING INDEX "EMPLOYMENT_CONTRACT_PK_IDX";

ALTER TABLE "ENROLMENT" ADD CONSTRAINT "ENROLMENT_PK" PRIMARY KEY
	("COURSE_ID",
	 "STAFF_ID") USING INDEX "ENROLMENT_PK_IDX";

ALTER TABLE "INTEREST" ADD CONSTRAINT "INTEREST_PK" PRIMARY KEY
	("INTEREST_ID") USING INDEX "INTEREST_PK_IDX";

ALTER TABLE "LOCATION" ADD CONSTRAINT "LOCATION_PK" PRIMARY KEY
	("CLIENT_ID",
	 "CITY_ID",
	 "COUNTRY_ID",
	 "PROJECT_ID") USING INDEX "LOCATION_PK_IDX";

ALTER TABLE "PROJECT" ADD CONSTRAINT "PROJECT_AK" UNIQUE
	("NAME",
	 "VERSION") USING INDEX "PROJECT_AK_IDX";

ALTER TABLE "PROJECT" ADD CONSTRAINT "PROJECT_PK" PRIMARY KEY
	("CLIENT_ID",
	 "PROJECT_ID") USING INDEX "PROJECT_PK_IDX";

ALTER TABLE "SERVICE" ADD CONSTRAINT "SERVICE_AK" UNIQUE
	("NAME") USING INDEX "SERVICE_AK_IDX";

ALTER TABLE "SERVICE" ADD CONSTRAINT "SERVICE_PK" PRIMARY KEY
	("SERVICE_ID") USING INDEX "SERVICE_PK_IDX";

ALTER TABLE "SKILL" ADD CONSTRAINT "SKILL_AK" UNIQUE
	("NAME") USING INDEX "SKILL_AK_IDX";

ALTER TABLE "SKILL" ADD CONSTRAINT "SKILL_PK" PRIMARY KEY
	("SKILL_ID") USING INDEX "SKILL_PK_IDX";

ALTER TABLE "STAFF" ADD CONSTRAINT "STAFF_AK" UNIQUE
	("FIRSTNAME",
	 "LASTNAME",
	 "BIRTH_DATE") USING INDEX "STAFF_AK_IDX";

ALTER TABLE "STAFF" ADD CONSTRAINT "STAFF_PK" PRIMARY KEY
	("STAFF_ID") USING INDEX "STAFF_PK_IDX";

ALTER TABLE "STAFF_ASSIGNMENTS" ADD CONSTRAINT "STAFF_ASSIGNMENTS_PK" PRIMARY KEY
	("ASSIGNMENT_ID",
	 "PROJECT_ID",
	 "STAFF_ID",
	 "CLIENT_ID") USING INDEX "STAFF_ASSIGNMENTS_PK_IDX";

ALTER TABLE "STAFF_SKILL" ADD CONSTRAINT "STAFF SKILL_PK" PRIMARY KEY
	("STAFF_ID",
	 "SKILL_ID") USING INDEX "STAFF SKILL_PK_IDX";

ALTER TABLE "SUPPLIER" ADD CONSTRAINT "SUPPLIER_AK" UNIQUE
	("NAME") USING INDEX "SUPPLIER_AK_IDX";

ALTER TABLE "SUPPLIER" ADD CONSTRAINT "SUPPLIER_PK" PRIMARY KEY
	("SUPPLIER_ID") USING INDEX "SUPPLIER_PK_IDX";

ALTER TABLE "SUPPLY_CONTRACT" ADD CONSTRAINT "SUPPLY_CONTRACT_PK" PRIMARY KEY
	("SUPPLIER_ID",
	 "CLIENT_ID",
	 "CONTRACT_ID",
	 "STAFF_ID") USING INDEX "SUPPLY_CONTRACT_PK_IDX";

ALTER TABLE "AGREEMENT" ADD CONSTRAINT "ENGAGES" FOREIGN KEY
	("CLIENT_ID",
	 "CONTRACT_ID")
	REFERENCES "CONTRACT"
	("CLIENT_ID",
	 "CONTRACT_ID")
	ON DELETE CASCADE;

ALTER TABLE "AGREEMENT" ADD CONSTRAINT "PROVIDES" FOREIGN KEY
	("SERVICE_ID")
	REFERENCES "SERVICE"
	("SERVICE_ID")
	ON DELETE CASCADE;

ALTER TABLE "CITY" ADD CONSTRAINT "HAS A" FOREIGN KEY
	("COUNTRY_ID")
	REFERENCES "COUNTRY"
	("COUNTRY_ID");

ALTER TABLE "CONTRACT" ADD CONSTRAINT "SIGNS" FOREIGN KEY
	("CLIENT_ID")
	REFERENCES "CLIENT"
	("CLIENT_ID");

ALTER TABLE "EMPLOYMENT_CONTRACT" ADD CONSTRAINT "EMPLOYS" FOREIGN KEY
	("SUPPLIER_ID")
	REFERENCES "SUPPLIER"
	("SUPPLIER_ID")
	ON DELETE CASCADE;

ALTER TABLE "EMPLOYMENT_CONTRACT" ADD CONSTRAINT "IS EMPLOYED" FOREIGN KEY
	("STAFF_ID")
	REFERENCES "STAFF"
	("STAFF_ID")
	ON DELETE CASCADE;

ALTER TABLE "ENROLMENT" ADD CONSTRAINT "ENROLS" FOREIGN KEY
	("STAFF_ID")
	REFERENCES "STAFF"
	("STAFF_ID")
	ON DELETE CASCADE;

ALTER TABLE "ENROLMENT" ADD CONSTRAINT "IN" FOREIGN KEY
	("COURSE_ID")
	REFERENCES "COURSE"
	("COURSE_ID")
	ON DELETE CASCADE;

ALTER TABLE "INTEREST" ADD CONSTRAINT "HAS" FOREIGN KEY
	("STAFF_ID")
	REFERENCES "STAFF"
	("STAFF_ID")
	ON DELETE CASCADE;

ALTER TABLE "LOCATION" ADD CONSTRAINT "ESTABLISHES A" FOREIGN KEY
	("CITY_ID",
	 "COUNTRY_ID")
	REFERENCES "CITY"
	("CITY_ID",
	 "COUNTRY_ID")
	ON DELETE CASCADE;

ALTER TABLE "LOCATION" ADD CONSTRAINT "IS BASED" FOREIGN KEY
	("CLIENT_ID",
	 "PROJECT_ID")
	REFERENCES "PROJECT"
	("CLIENT_ID",
	 "PROJECT_ID")
	ON DELETE CASCADE;

ALTER TABLE "PROJECT" ADD CONSTRAINT "CONTROLS" FOREIGN KEY
	("CLIENT_ID")
	REFERENCES "CLIENT"
	("CLIENT_ID");

ALTER TABLE "STAFF_ASSIGNMENTS" ADD CONSTRAINT "ASSIGNS" FOREIGN KEY
	("ASSIGNMENT_ID")
	REFERENCES "ASSIGNMENT"
	("ASSIGNMENT_ID")
	ON DELETE CASCADE;

ALTER TABLE "STAFF_ASSIGNMENTS" ADD CONSTRAINT "IS COMPOSED OF" FOREIGN KEY
	("CLIENT_ID",
	 "PROJECT_ID")
	REFERENCES "PROJECT"
	("CLIENT_ID",
	 "PROJECT_ID")
	ON DELETE CASCADE;

ALTER TABLE "STAFF_ASSIGNMENTS" ADD CONSTRAINT "WORKS ON" FOREIGN KEY
	("STAFF_ID")
	REFERENCES "STAFF"
	("STAFF_ID")
	ON DELETE CASCADE;

ALTER TABLE "STAFF_SKILL" ADD CONSTRAINT "IS USED" FOREIGN KEY
	("SKILL_ID")
	REFERENCES "SKILL"
	("SKILL_ID")
	ON DELETE CASCADE;

ALTER TABLE "STAFF_SKILL" ADD CONSTRAINT "USES" FOREIGN KEY
	("STAFF_ID")
	REFERENCES "STAFF"
	("STAFF_ID")
	ON DELETE CASCADE;

ALTER TABLE "SUPPLY_CONTRACT" ADD CONSTRAINT "AGREES" FOREIGN KEY
	("SUPPLIER_ID")
	REFERENCES "SUPPLIER"
	("SUPPLIER_ID")
	ON DELETE CASCADE;

ALTER TABLE "SUPPLY_CONTRACT" ADD CONSTRAINT "COMMITS TO" FOREIGN KEY
	("CLIENT_ID",
	 "CONTRACT_ID")
	REFERENCES "CONTRACT"
	("CLIENT_ID",
	 "CONTRACT_ID")
	ON DELETE CASCADE;

ALTER TABLE "SUPPLY_CONTRACT" ADD CONSTRAINT "WORKS IN" FOREIGN KEY
	("STAFF_ID")
	REFERENCES "STAFF"
	("STAFF_ID")
	ON DELETE CASCADE;

CREATE OR REPLACE VIEW "v_Experience" ("CONTRACT_ID", "FIRSTNAME", "LASTNAME", "ROLE", "CUSTOMER", "CITY", "COUNTRY") AS
SELECT "CONTRACT"."CONTRACT_ID", "FIRSTNAME", "LASTNAME", "SERVICE"."NAME" AS "ROLE", "CLIENT"."NAME" AS "CUSTOMER", "CITY"."NAME" AS "CITY", "COUNTRY"."NAME" AS "COUNTRY"
FROM "SERVICE", "CLIENT", "CONTRACT", "STAFF", "PROJECT", "COUNTRY", "CITY", "LOCATION", "STAFF_ASSIGNMENTS", "SUPPLIER", "AGREEMENT", "EMPLOYMENT_CONTRACT", "SUPPLY_CONTRACT"
WHERE CITY.COUNTRY_ID = COUNTRY.COUNTRY_ID AND
LOCATION.CITY_ID = CITY.CITY_ID AND 
LOCATION.COUNTRY_ID = CITY.COUNTRY_ID AND 
LOCATION.PROJECT_ID = PROJECT.PROJECT_ID AND
PROJECT.CLIENT_ID = CLIENT.CLIENT_ID AND
CLIENT.CLIENT_ID = CONTRACT.CLIENT_ID AND
PROJECT.PROJECT_ID = STAFF_ASSIGNMENTS.PROJECT_ID AND
PROJECT.CLIENT_ID = STAFF_ASSIGNMENTS.CLIENT_ID AND
STAFF_ASSIGNMENTS.STAFF_ID = STAFF.STAFF_ID AND
CONTRACT.CONTRACT_ID = AGREEMENT.CONTRACT_ID AND
CONTRACT.CLIENT_ID  = AGREEMENT.CLIENT_ID AND
AGREEMENT.SERVICE_ID = SERVICE.SERVICE_ID  AND
CONTRACT.CONTRACT_ID = SUPPLY_CONTRACT.CONTRACT_ID AND
CONTRACT.CLIENT_ID = SUPPLY_CONTRACT.CLIENT_ID AND
SUPPLIER.SUPPLIER_ID = SUPPLY_CONTRACT.SUPPLIER_ID AND
SUPPLIER.SUPPLIER_ID = EMPLOYMENT_CONTRACT.SUPPLIER_ID AND
EMPLOYMENT_CONTRACT.STAFF_ID = STAFF.STAFF_ID
GROUP BY "CONTRACT"."CONTRACT_ID", "FIRSTNAME", "LASTNAME", "SERVICE"."NAME", "CLIENT"."NAME", "CITY"."NAME", "COUNTRY"."NAME";

CREATE OR REPLACE VIEW "v_ProjectTask" ("PROJECT_ID", "VERSION", "FIRSTNAME", "LASTNAME", "PROJECT", "PROJECT_VERSION", "TASK") AS
SELECT "PROJECT"."PROJECT_ID", "VERSION", "FIRSTNAME", "LASTNAME", "NAME" AS "PROJECT", "VERSION" AS "PROJECT_VERSION", "ASSIGNMENT"."DESC" AS "TASK"
FROM "STAFF_ASSIGNMENTS", "PROJECT", "STAFF", "ASSIGNMENT"
WHERE PROJECT.PROJECT_ID = STAFF_ASSIGNMENTS.PROJECT_ID AND
PROJECT.CLIENT_ID = STAFF_ASSIGNMENTS.CLIENT_ID AND
STAFF_ASSIGNMENTS.ASSIGNMENT_ID = ASSIGNMENT.ASSIGNMENT_ID AND
STAFF.STAFF_ID = STAFF_ASSIGNMENTS.STAFF_ID
ORDER BY PROJECT.NAME;

CREATE OR REPLACE VIEW "v_Resume" ("CONTRACT_ID", "CONTRACT", "FIRSTNAME", "LASTNAME", "ROLE", "CUSTOMER", "SUPPLIER", "CITY", "COUNTRY", "PROJECT", "RESPONSIBILITY", "START_DATE", "END_DATE") AS
SELECT "CONTRACT"."CONTRACT_ID", "CONTRACT"."NAME" AS "CONTRACT", "FIRSTNAME", "LASTNAME", "SERVICE"."NAME" AS "ROLE", "CLIENT"."NAME" AS "CUSTOMER", "SUPPLIER"."NAME" AS "SUPPLIER", "CITY"."NAME" AS "CITY", "COUNTRY"."NAME" AS "COUNTRY", "PROJECT"."NAME" AS "PROJECT", "ASSIGNMENT"."DESC" AS "RESPONSIBILITY", "START_DATE", "END_DATE"
FROM "CLIENT", "COUNTRY", "PROJECT", "CONTRACT", "STAFF_ASSIGNMENTS", "SERVICE", "STAFF", "CITY", "SUPPLIER", "LOCATION", "ASSIGNMENT", "AGREEMENT", "SUPPLY_CONTRACT"
WHERE CITY.COUNTRY_ID                         = COUNTRY.COUNTRY_ID AND
LOCATION.CITY_ID                        = CITY.CITY_ID AND 
LOCATION.COUNTRY_ID                     = CITY.COUNTRY_ID AND 
LOCATION.PROJECT_ID                     = PROJECT.PROJECT_ID AND
PROJECT.CLIENT_ID                       = CLIENT.CLIENT_ID AND
CLIENT.CLIENT_ID                        = CONTRACT.CLIENT_ID AND
PROJECT.PROJECT_ID                      = STAFF_ASSIGNMENTS.PROJECT_ID AND
PROJECT.CLIENT_ID                       = STAFF_ASSIGNMENTS.CLIENT_ID AND
STAFF_ASSIGNMENTS.STAFF_ID       = STAFF.STAFF_ID AND
CONTRACT.CONTRACT_ID                    = AGREEMENT.CONTRACT_ID AND
CONTRACT.CLIENT_ID                      = AGREEMENT.CLIENT_ID AND
AGREEMENT.SERVICE_ID   = SERVICE.SERVICE_ID  AND
CONTRACT.CONTRACT_ID                    = SUPPLY_CONTRACT.CONTRACT_ID AND
CONTRACT.CLIENT_ID                      = SUPPLY_CONTRACT.CLIENT_ID AND
SUPPLIER.SUPPLIER_ID                    = SUPPLY_CONTRACT.SUPPLIER_ID AND
STAFF.STAFF_ID                          = SUPPLY_CONTRACT.STAFF_ID AND
ASSIGNMENT.ASSIGNMENT_ID                = STAFF_ASSIGNMENTS.ASSIGNMENT_ID
GROUP BY "CONTRACT"."CONTRACT_ID", "CONTRACT"."NAME", "FIRSTNAME", "LASTNAME", "SERVICE"."NAME", "CLIENT"."NAME", "CITY"."NAME", "COUNTRY"."NAME", "PROJECT"."NAME", "ASSIGNMENT"."DESC", "SUPPLIER"."NAME", "START_DATE", "END_DATE";

