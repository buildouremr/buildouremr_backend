-- Master Data Seed Script for Render Database
-- Generated automatically for non-patient master data porting

-- ==============================================
-- Data for employee_profile_roles
-- ==============================================
INSERT INTO "employee_profile_roles" ("employee_profile_role_id", "employee_profile_role_isactive", "employee_profile_role_name") VALUES (1, TRUE, 'Doctor') ON CONFLICT ("employee_profile_role_id") DO NOTHING;
INSERT INTO "employee_profile_roles" ("employee_profile_role_id", "employee_profile_role_isactive", "employee_profile_role_name") VALUES (2, TRUE, 'Nurse') ON CONFLICT ("employee_profile_role_id") DO NOTHING;
INSERT INTO "employee_profile_roles" ("employee_profile_role_id", "employee_profile_role_isactive", "employee_profile_role_name") VALUES (3, TRUE, 'Receptionist') ON CONFLICT ("employee_profile_role_id") DO NOTHING;
SELECT setval('public.employee_profile_roles_employee_profile_role_id_seq', 3, true);

-- ==============================================
-- Data for employee_speciality
-- ==============================================
INSERT INTO "employee_speciality" ("employee_speciality_id", "employee_speciality_isactive", "employee_speciality_name") VALUES (1, TRUE, 'General') ON CONFLICT ("employee_speciality_id") DO NOTHING;
INSERT INTO "employee_speciality" ("employee_speciality_id", "employee_speciality_isactive", "employee_speciality_name") VALUES (2, TRUE, 'Diabetologist') ON CONFLICT ("employee_speciality_id") DO NOTHING;
INSERT INTO "employee_speciality" ("employee_speciality_id", "employee_speciality_isactive", "employee_speciality_name") VALUES (3, TRUE, 'Cardiologist') ON CONFLICT ("employee_speciality_id") DO NOTHING;
INSERT INTO "employee_speciality" ("employee_speciality_id", "employee_speciality_isactive", "employee_speciality_name") VALUES (4, TRUE, 'Pediatrician') ON CONFLICT ("employee_speciality_id") DO NOTHING;
INSERT INTO "employee_speciality" ("employee_speciality_id", "employee_speciality_isactive", "employee_speciality_name") VALUES (5, TRUE, 'Pulmonology') ON CONFLICT ("employee_speciality_id") DO NOTHING;
SELECT setval('public.employee_speciality_employee_speciality_id_seq', 5, true);

-- ==============================================
-- Data for employee_profile
-- ==============================================
INSERT INTO "employee_profile" ("employee_profile_id", "employee_profile_address", "employee_profile_blood_group", "employee_profile_city", "employee_profile_dob", "employee_profile_email_id", "employee_profile_firstname", "employee_profile_image", "employee_profile_isactive", "employee_profile_lastname", "employee_profile_middlename", "employee_profile_mobileno", "employee_profile_other_mobileno", "employee_profile_pincode", "employee_profile_role", "employee_profile_state", "employee_profile_role_id", "employee_profile_speciality") VALUES (1, 'Main St', 'O+', 'City', '1990-01-01', 'zacariya@dreamemr.shop', 'Zacariya', NULL, TRUE, '', NULL, '9876543210', NULL, '000000', 'Doctor', 'State', 1, 1) ON CONFLICT ("employee_profile_id") DO NOTHING;
INSERT INTO "employee_profile" ("employee_profile_id", "employee_profile_address", "employee_profile_blood_group", "employee_profile_city", "employee_profile_dob", "employee_profile_email_id", "employee_profile_firstname", "employee_profile_image", "employee_profile_isactive", "employee_profile_lastname", "employee_profile_middlename", "employee_profile_mobileno", "employee_profile_other_mobileno", "employee_profile_pincode", "employee_profile_role", "employee_profile_state", "employee_profile_role_id", "employee_profile_speciality") VALUES (2, 'Main St', 'A+', 'City', '1990-01-01', 'fahim@dreamemr.shop', 'Fahim', NULL, TRUE, '', NULL, '9876543211', NULL, '000000', 'Doctor', 'State', 1, 1) ON CONFLICT ("employee_profile_id") DO NOTHING;
SELECT setval('public.employee_profile_employee_profile_id_seq', 2, true);

-- ==============================================
-- Data for scheduler_appointment_status
-- ==============================================
INSERT INTO "scheduler_appointment_status" ("scheduler_appointment_status_id", "scheduler_appointment_isactive", "scheduler_appointment_status_name") VALUES (1, TRUE, 'Scheduled') ON CONFLICT ("scheduler_appointment_status_id") DO NOTHING;
INSERT INTO "scheduler_appointment_status" ("scheduler_appointment_status_id", "scheduler_appointment_isactive", "scheduler_appointment_status_name") VALUES (2, TRUE, 'Waiting') ON CONFLICT ("scheduler_appointment_status_id") DO NOTHING;
INSERT INTO "scheduler_appointment_status" ("scheduler_appointment_status_id", "scheduler_appointment_isactive", "scheduler_appointment_status_name") VALUES (3, TRUE, 'In Consultation') ON CONFLICT ("scheduler_appointment_status_id") DO NOTHING;
INSERT INTO "scheduler_appointment_status" ("scheduler_appointment_status_id", "scheduler_appointment_isactive", "scheduler_appointment_status_name") VALUES (4, TRUE, 'Completed') ON CONFLICT ("scheduler_appointment_status_id") DO NOTHING;
INSERT INTO "scheduler_appointment_status" ("scheduler_appointment_status_id", "scheduler_appointment_isactive", "scheduler_appointment_status_name") VALUES (5, TRUE, 'Cancelled') ON CONFLICT ("scheduler_appointment_status_id") DO NOTHING;
INSERT INTO "scheduler_appointment_status" ("scheduler_appointment_status_id", "scheduler_appointment_isactive", "scheduler_appointment_status_name") VALUES (6, TRUE, 'Pending') ON CONFLICT ("scheduler_appointment_status_id") DO NOTHING;
INSERT INTO "scheduler_appointment_status" ("scheduler_appointment_status_id", "scheduler_appointment_isactive", "scheduler_appointment_status_name") VALUES (7, TRUE, 'No Show') ON CONFLICT ("scheduler_appointment_status_id") DO NOTHING;
SELECT setval('public.scheduler_appointment_status_scheduler_appointment_status_i_seq', 7, true);

-- ==============================================
-- Data for scheduler_appointment_types
-- ==============================================
INSERT INTO "scheduler_appointment_types" ("scheduler_appointment_type_id", "scheduler_appointment_isactive", "scheduler_appointment_type_name") VALUES (1, TRUE, 'Follow up') ON CONFLICT ("scheduler_appointment_type_id") DO NOTHING;
INSERT INTO "scheduler_appointment_types" ("scheduler_appointment_type_id", "scheduler_appointment_isactive", "scheduler_appointment_type_name") VALUES (2, TRUE, 'Consultation') ON CONFLICT ("scheduler_appointment_type_id") DO NOTHING;
INSERT INTO "scheduler_appointment_types" ("scheduler_appointment_type_id", "scheduler_appointment_isactive", "scheduler_appointment_type_name") VALUES (3, TRUE, 'Check up') ON CONFLICT ("scheduler_appointment_type_id") DO NOTHING;
INSERT INTO "scheduler_appointment_types" ("scheduler_appointment_type_id", "scheduler_appointment_isactive", "scheduler_appointment_type_name") VALUES (4, TRUE, 'Emergency') ON CONFLICT ("scheduler_appointment_type_id") DO NOTHING;
SELECT setval('public.scheduler_appointment_types_scheduler_appointment_type_id_seq', 4, true);

-- ==============================================
-- Data for assessment_master
-- ==============================================
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (1, 'Essential (primary) hypertension', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (2, 'Type 2 diabetes mellitus without complications', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (3, 'Asthma, unspecified, uncomplicated', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (4, 'Acute upper respiratory infection, unspecified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (5, 'Low back pain', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (6, 'Hyperlipidemia, unspecified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (7, 'Gastroesophageal reflux disease without esophagitis', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (8, 'Hypothyroidism, unspecified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (9, 'Anxiety disorder, unspecified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (10, 'Major depressive disorder, single episode, unspecified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (11, 'Urinary tract infection, site not specified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (12, 'Allergic rhinitis, unspecified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (13, 'Acute pharyngitis, unspecified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (14, 'Headache, unspecified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "assessment_master" ("id", "name", "created_on") VALUES (15, 'Vitamin D deficiency, unspecified', '2026-08-30T19:19:05.562086') ON CONFLICT ("id") DO NOTHING;
SELECT setval('public.assessment_master_id_seq', 15, true);

-- ==============================================
-- Data for chronic_disease
-- ==============================================
INSERT INTO "chronic_disease" ("chronic_disease_id", "chronic_disease_iaactive", "chronic_disease_name") VALUES (1, 'true', 'Hypertension') ON CONFLICT ("chronic_disease_id") DO NOTHING;
INSERT INTO "chronic_disease" ("chronic_disease_id", "chronic_disease_iaactive", "chronic_disease_name") VALUES (2, 'true', 'Type II Diabetes') ON CONFLICT ("chronic_disease_id") DO NOTHING;
INSERT INTO "chronic_disease" ("chronic_disease_id", "chronic_disease_iaactive", "chronic_disease_name") VALUES (3, 'true', 'Asthma') ON CONFLICT ("chronic_disease_id") DO NOTHING;
INSERT INTO "chronic_disease" ("chronic_disease_id", "chronic_disease_iaactive", "chronic_disease_name") VALUES (4, 'true', 'Thyroid') ON CONFLICT ("chronic_disease_id") DO NOTHING;
SELECT setval('public.chronic_disease_chronic_disease_id_seq', 4, true);

-- ==============================================
-- Data for medication
-- ==============================================
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (1, NULL, '2026-08-12T23:07:45.513317', 'Injection', 'amoxicillin', NULL, 'Amoxicillin', NULL, NULL, '100mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (2, NULL, '2026-08-12T23:07:45.513317', 'Ointment', 'lisinopril', NULL, 'Lisinopril', NULL, NULL, '10mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (3, NULL, '2026-08-12T23:07:45.513317', 'Syrup', 'atorvastatin', NULL, 'Atorvastatin', NULL, NULL, '250mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (4, NULL, '2026-08-12T23:07:45.513317', 'Capsule', 'levothyroxine', NULL, 'Levothyroxine', NULL, NULL, '20mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (5, NULL, '2026-08-12T23:07:45.513317', 'Syrup', 'metformin', NULL, 'Metformin', NULL, NULL, '100mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (6, NULL, '2026-08-12T23:07:45.513317', 'Capsule', 'amlodipine', NULL, 'Amlodipine', NULL, NULL, '100mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (7, NULL, '2026-08-12T23:07:45.513317', 'Syrup', 'metoprolol', NULL, 'Metoprolol', NULL, NULL, '50mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (8, NULL, '2026-08-12T23:07:45.513317', 'Injection', 'albuterol', NULL, 'Albuterol', NULL, NULL, '20mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (9, NULL, '2026-08-12T23:07:45.513317', 'Tablet', 'omeprazole', NULL, 'Omeprazole', NULL, NULL, '50mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (10, NULL, '2026-08-12T23:07:45.513317', 'Tablet', 'losartan', NULL, 'Losartan', NULL, NULL, '20mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (11, NULL, '2026-08-12T23:07:45.513317', 'Capsule', 'gabapentin', NULL, 'Gabapentin', NULL, NULL, '5mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (12, NULL, '2026-08-12T23:07:45.513317', 'Capsule', 'hydrochlorothiazide', NULL, 'Hydrochlorothiazide', NULL, NULL, '5mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (13, NULL, '2026-08-12T23:07:45.513317', 'Tablet', 'sertraline', NULL, 'Sertraline', NULL, NULL, '500mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (14, NULL, '2026-08-12T23:07:45.513317', 'Injection', 'simvastatin', NULL, 'Simvastatin', NULL, NULL, '10mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (15, NULL, '2026-08-12T23:07:45.513317', 'Capsule', 'montelukast', NULL, 'Montelukast', NULL, NULL, '50mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (16, NULL, '2026-08-12T23:07:45.513317', 'Injection', 'escitalopram', NULL, 'Escitalopram', NULL, NULL, '5mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (17, NULL, '2026-08-12T23:07:45.513317', 'Capsule', 'acetaminophen', NULL, 'Acetaminophen', NULL, NULL, '50mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (18, NULL, '2026-08-12T23:07:45.513317', 'Capsule', 'rosuvastatin', NULL, 'Rosuvastatin', NULL, NULL, '10mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (19, NULL, '2026-08-12T23:07:45.513317', 'Syrup', 'bupropion', NULL, 'Bupropion', NULL, NULL, '500mg') ON CONFLICT ("id") DO NOTHING;
INSERT INTO "medication" ("id", "brand_name", "created_on", "form", "generic_name", "manufacturer", "name", "ndc_code", "route", "strength") VALUES (20, NULL, '2026-08-12T23:07:45.513317', 'Tablet', 'trazodone', NULL, 'Trazodone', NULL, NULL, '50mg') ON CONFLICT ("id") DO NOTHING;
SELECT setval('public.medication_id_seq', 20, true);
