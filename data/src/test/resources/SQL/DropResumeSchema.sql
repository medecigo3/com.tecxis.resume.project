-- Drop relationships section COMMENTED OUT FOR UNIT TESTS WITH H2 DB-------------------------------------------------

--ALTER TABLE "LOCATION" DROP CONSTRAINT IF EXISTS "ESTABLISHES A";
--ALTER TABLE "LOCATION" DROP CONSTRAINT IF EXISTS "IS BASED";
--ALTER TABLE "ASSIGNMENT" DROP CONSTRAINT IF EXISTS "IS COMPOSED OF";
--ALTER TABLE "PROJECT" DROP CONSTRAINT IF EXISTS "WORKS ON";
--ALTER TABLE "SUPPLIER" DROP CONSTRAINT IF EXISTS "WORKS FOR";
--ALTER TABLE "CONTRACT" DROP CONSTRAINT IF EXISTS "ENGAGES";
--ALTER TABLE "PROJECT" DROP CONSTRAINT IF EXISTS "CONTROLS";
--ALTER TABLE "ENROLMENT" DROP CONSTRAINT IF EXISTS "ENROLS";
--ALTER TABLE "CONTRACT" DROP CONSTRAINT IF EXISTS "HOLDS";
--ALTER TABLE "ENROLMENT" DROP CONSTRAINT IF EXISTS "IN";
--ALTER TABLE "STAFF_SKILL" DROP CONSTRAINT IF EXISTS "IS USED";
--ALTER TABLE "STAFF_SKILL" DROP CONSTRAINT IF EXISTS "USES";
--ALTER TABLE "INTEREST" DROP CONSTRAINT IF EXISTS "HAS";
--ALTER TABLE "CITY" DROP CONSTRAINT IF EXISTS "HAS A";
--ALTER TABLE "CONTRACT" DROP CONSTRAINT IF EXISTS "SIGNS";


-- Drop views section --------------------------------------------------- 

DROP VIEW IF EXISTS "v_Experience";
DROP VIEW IF EXISTS "v_ProjectTask";


-- DROP TABLE IF EXISTSs section ---------------------------------------------------

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
DROP TABLE IF EXISTS "ASSIGNING";
DROP TABLE IF EXISTS "COUNTRY";
DROP TABLE IF EXISTS "CONTRACT";
DROP TABLE IF EXISTS "CLIENT";
DROP TABLE IF EXISTS "PROJECT";


-- DROP SEQUENCE IF EXISTSs section --------------------------------------------------- 

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
