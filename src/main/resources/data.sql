-- ============================================================
-- EMR Dashboard Sample Data
-- Run against: smartemr database
-- ============================================================

-- ── 1. Lookup Tables ─────────────────────────────────────────

-- Employee Roles
INSERT INTO employee_profile_roles (employee_profile_role_name, employee_profile_role_isactive) VALUES
  ('Doctor', true),
  ('Nurse', true),
  ('Receptionist', true);

-- Employee Specialities
INSERT INTO employee_speciality (employee_speciality_name, employee_speciality_isactive) VALUES
  ('Diabetologist', true),
  ('Cardiologist', true),
  ('Pediatrician', true),
  ('Pulmonology', true);

-- Appointment Statuses
INSERT INTO scheduler_appointment_status (scheduler_appointment_status_name, scheduler_appointment_isactive) VALUES
  ('Scheduled', true),
  ('Waiting', true),
  ('In Consultation', true),
  ('Completed', true),
  ('Cancelled', true),
  ('Pending', true),
  ('No Show', true);

-- Appointment Types
INSERT INTO scheduler_appointment_types (scheduler_appointment_type_name, scheduler_appointment_isactive) VALUES
  ('Follow up', true),
  ('Consultation', true),
  ('Check up', true),
  ('Emergency', true);

-- ── 2. Employees (3 Doctors) ─────────────────────────────────

INSERT INTO employee_profile (
  employee_profile_firstname, employee_profile_lastname, employee_profile_dob,
  employee_profile_mobileno, employee_profile_email_id, employee_profile_address,
  employee_profile_state, employee_profile_city, employee_profile_pincode,
  employee_profile_role, employee_profile_isactive, employee_profile_blood_group,
  employee_profile_role_id, employee_profile_speciality
) VALUES
  ('Ashok', 'Ranjith', '1980-05-12', '9876543210', 'ashok.ranjith@dreamemr.com',
   '45 MG Road', 'Tamil Nadu', 'Chennai', '600001',
   'Doctor', true, 'O+', 1, 1),
  ('Priya', 'Mohan', '1985-09-25', '9876543211', 'priya.mohan@dreamemr.com',
   '12 Residency Road', 'Karnataka', 'Bangalore', '560001',
   'Doctor', true, 'A+', 1, 2),
  ('Palani', 'Muthu', '1978-03-08', '9876543212', 'palani.muthu@dreamemr.com',
   '78 Anna Salai', 'Tamil Nadu', 'Coimbatore', '641001',
   'Doctor', true, 'B+', 1, 3);

-- ── 3. Patients (10) ─────────────────────────────────────────

INSERT INTO patient_registration (
  patient_registration_firstname, patient_registration_lastname, patient_registration_dob,
  patient_registration_sex, patient_registration_mobile_no, patient_registration_email_id,
  patient_registration_address, patient_registration_state, patient_registration_city,
  patient_registration_pincode, patient_registration_blood_group, patient_registration_active,
  patient_registration_principal_doctor
) VALUES
  ('James', 'Carter', '1985-03-15', 'Male', '9811001001', 'james.carter@email.com',
   '23 Lake View Street', 'Tamil Nadu', 'Chennai', '600002', 'O+', true, 1),
  ('Emily', 'Davis', '1992-07-22', 'Female', '9811001002', 'emily.davis@email.com',
   '56 Park Avenue', 'Karnataka', 'Bangalore', '560002', 'A+', true, 1),
  ('Michael', 'Johnson', '1978-11-08', 'Male', '9811001003', 'michael.johnson@email.com',
   '89 Hill Road', 'Maharashtra', 'Mumbai', '400001', 'B+', true, 1),
  ('Olivia', 'Miller', '2001-01-30', 'Female', '9811001004', 'olivia.miller@email.com',
   '12 Jubilee Hills', 'Telangana', 'Hyderabad', '500033', 'AB+', true, 2),
  ('David', 'Smith', '1965-09-14', 'Male', '9811001005', 'david.smith@email.com',
   '34 FC Road', 'Maharashtra', 'Pune', '411005', 'O-', true, 2),
  ('Sophia', 'Williams', '1998-04-19', 'Female', '9811001006', 'sophia.williams@email.com',
   '67 Connaught Place', 'Delhi', 'New Delhi', '110001', 'A-', true, 2),
  ('Ethan', 'Brown', '1990-12-03', 'Male', '9811001007', 'ethan.brown@email.com',
   '90 Race Course Road', 'Tamil Nadu', 'Coimbatore', '641018', 'B-', true, 3),
  ('Ava', 'Wilson', '1988-06-25', 'Female', '9811001008', 'ava.wilson@email.com',
   '45 Salt Lake', 'West Bengal', 'Kolkata', '700091', 'AB-', true, 3),
  ('Liam', 'Taylor', '1975-08-17', 'Male', '9811001009', 'liam.taylor@email.com',
   '23 MI Road', 'Rajasthan', 'Jaipur', '302001', 'O+', true, 1),
  ('Isabella', 'Anderson', '2003-02-11', 'Female', '9811001010', 'isabella.anderson@email.com',
   '78 Hazratganj', 'Uttar Pradesh', 'Lucknow', '226001', 'A+', true, 3);

-- ── 4. Appointments (~15) ────────────────────────────────────
-- Using today's date and nearby dates for realistic dashboard display
-- Status IDs: 1=Scheduled, 2=Waiting, 3=In Consultation, 4=Completed, 5=Cancelled, 6=Pending, 7=No Show
-- Type IDs: 1=Follow up, 2=Consultation, 3=Check up, 4=Emergency
-- Provider IDs: 1=Dr. Ashok Ranjith, 2=Dr. Priya Mohan, 3=Dr. Palani Muthu

INSERT INTO scheduler_appointment (
  scheduler_appointment_patientid, scheduler_appointment_patientname,
  scheduler_appointment_appt_date, scheduler_appointment_reason,
  scheduler_appointment_providerid, scheduler_appointment_start_time,
  scheduler_appointment_end_time, scheduler_appointment_status, scheduler_appointment_type
) VALUES
  -- Today's appointments (use CURRENT_DATE)
  (1, 'James Carter', CURRENT_DATE, 'Head Ace, Feeling Dizzy', 1, '09:00', '10:00', 2, 1),
  (2, 'Emily Davis', CURRENT_DATE, 'Severe Stomach Pain', 1, '10:30', '11:30', 2, 2),
  (3, 'Michael Johnson', CURRENT_DATE, 'Blood Vomiting since yesterday', 1, '13:15', '14:15', 2, 1),
  (4, 'Olivia Miller', CURRENT_DATE, 'Pain in the left part of the chest', 2, '11:30', '12:30', 2, 2),
  (5, 'David Smith', CURRENT_DATE, 'Cold, Cough, Fever for past 3 days', 2, '12:20', '13:20', 2, 2),
  (6, 'Sophia Williams', CURRENT_DATE, 'Regular diabetes checkup', 1, '14:00', '15:00', 6, 3),
  (7, 'Ethan Brown', CURRENT_DATE, 'Breathing difficulty', 3, '09:30', '10:30', 6, 2),
  (8, 'Ava Wilson', CURRENT_DATE, 'Follow up after surgery', 3, '11:00', '12:00', 4, 1),
  (9, 'Liam Taylor', CURRENT_DATE, 'Knee pain and swelling', 1, '15:00', '16:00', 1, 2),
  (10, 'Isabella Anderson', CURRENT_DATE, 'Vaccination follow up', 3, '14:30', '15:30', 5, 1),

  -- Yesterday's appointments
  (1, 'James Carter', CURRENT_DATE - INTERVAL '1 day', 'Blood pressure monitoring', 1, '10:00', '11:00', 4, 3),
  (3, 'Michael Johnson', CURRENT_DATE - INTERVAL '1 day', 'Post-treatment review', 1, '11:00', '12:00', 4, 1),
  (5, 'David Smith', CURRENT_DATE - INTERVAL '1 day', 'ECG and cardiac evaluation', 2, '14:00', '15:00', 4, 2),

  -- Two days ago
  (2, 'Emily Davis', CURRENT_DATE - INTERVAL '2 days', 'Lab results review', 1, '09:00', '10:00', 4, 1),
  (4, 'Olivia Miller', CURRENT_DATE - INTERVAL '2 days', 'Chest pain follow up', 2, '10:00', '11:00', 7, 1);
