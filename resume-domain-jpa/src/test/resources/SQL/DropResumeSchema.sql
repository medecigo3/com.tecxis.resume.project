-- Drop relationships section COMMENTED OUT FOR UNIT TESTS WITH H2 DB-------------------------------------------------

--ALTER TABLE "SUPPLY_CONTRACT" DROP CONSTRAINT "WORKS IN";
--ALTER TABLE "SUPPLY_CONTRACT" DROP CONSTRAINT "COMMITS TO";
--ALTER TABLE "SUPPLY_CONTRACT" DROP CONSTRAINT "AGREES";
--ALTER TABLE "EMPLOYMENT_CONTRACT" DROP CONSTRAINT "EMPLOYS";
--ALTER TABLE "EMPLOYMENT_CONTRACT" DROP CONSTRAINT "IS EMPLOYED";
--ALTER TABLE "CONTRACT_SERVICE_AGREEMENT" DROP CONSTRAINT "PROVIDES";
--ALTER TABLE "CONTRACT_SERVICE_AGREEMENT" DROP CONSTRAINT "ENGAGES";
--ALTER TABLE "STAFF_PROJECT_ASSIGNMENT" DROP CONSTRAINT "ASSIGNS";
--ALTER TABLE "STAFF_PROJECT_ASSIGNMENT" DROP CONSTRAINT "WORKS ON";
--ALTER TABLE "STAFF_PROJECT_ASSIGNMENT" DROP CONSTRAINT "IS COMPOSED OF";
--ALTER TABLE "LOCATION" DROP CONSTRAINT "ESTABLISHES A";
--ALTER TABLE "LOCATION" DROP CONSTRAINT "IS BASED";
--ALTER TABLE "PROJECT" DROP CONSTRAINT "CONTROLS";
--ALTER TABLE "ENROLMENT" DROP CONSTRAINT "ENROLS";
--ALTER TABLE "ENROLMENT" DROP CONSTRAINT "IN";
--ALTER TABLE "STAFF_SKILL" DROP CONSTRAINT "IS USED";
--ALTER TABLE "STAFF_SKILL" DROP CONSTRAINT "USES";
--ALTER TABLE "INTEREST" DROP CONSTRAINT "HAS";
--ALTER TABLE "CITY" DROP CONSTRAINT "HAS A";
--ALTER TABLE "CONTRACT" DROP CONSTRAINT "SIGNS";


-- Drop views section --------------------------------------------------- 

DROP VIEW IF EXISTS "v_Resume";
DROP VIEW IF EXISTS "v_Experience";
DROP VIEW IF EXISTS "v_ProjectTask";


-- DROP TABLE IF EXISTSs section ---------------------------------------------------

DROP TABLE IF EXISTS "SUPPLY_CONTRACT";
DROP TABLE IF EXISTS "EMPLOYMENT_CONTRACT";
DROP TABLE IF EXISTS "CONTRACT_SERVICE_AGREEMENT";
DROP TABLE IF EXISTS "ASSIGNMENT";
DROP TABLE IF EXISTS "LOCATION";
DROP TABLE IF EXISTS "SUPPLIER";
DROP TABLE IF EXISTS "ENROLMENT";
DROP TABLE IF EXISTS "COURSE";
DROP TABLE IF EXISTS "STAFF_SKILL";
DROP TABLE IF EXISTS "CITY";
DROP TABLE IF EXISTS "INTEREST";
DROP TABLE IF EXISTS "SERVICE";
DROP TABLE IF EXISTS "STAFF";
DROP TABLE IF EXISTS "SKILL";
DROP TABLE IF EXISTS "STAFF_PROJECT_ASSIGNMENT";
DROP TABLE IF EXISTS "COUNTRY";
DROP TABLE IF EXISTS "CONTRACT";
DROP TABLE IF EXISTS "CLIENT";
DROP TABLE IF EXISTS "PROJECT";


-- DROP SEQUENCE IF EXISTSs section --------------------------------------------------- 

DROP SEQUENCE IF EXISTS "EMPLOYMENT_CONTRACT_SEQ";
DROP SEQUENCE IF EXISTS "COURSE_SEQ";
DROP SEQUENCE IF EXISTS "CONTRACT_SEQ";
DROP SEQUENCE IF EXISTS "SUPPLIER_SEQ";
DROP SEQUENCE IF EXISTS "INTEREST_SEQ";
DROP SEQUENCE IF EXISTS "SERVICE_SEQ";
DROP SEQUENCE IF EXISTS "STAFF_SEQ";
DROP SEQUENCE IF EXISTS "SKILL_SEQ";
DROP SEQUENCE IF EXISTS "ASSIGNMENT_SEQ";
DROP SEQUENCE IF EXISTS "COUNTRY_SEQ";
DROP SEQUENCE IF EXISTS "CLIENT_SEQ";
DROP SEQUENCE IF EXISTS "PROJECT_SEQ";
DROP SEQUENCE IF EXISTS "CITY_SEQ";

COMMIT;