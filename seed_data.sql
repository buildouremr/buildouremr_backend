INSERT INTO scheduler_appointment_status (scheduler_appointment_status_name, scheduler_appointment_isactive) VALUES
('Scheduled', true),
('Arrived', true),
('In Progress', true),
('Completed', true),
('Cancelled', true),
('No Show', true);

INSERT INTO scheduler_appointment_types (scheduler_appointment_type_name, scheduler_appointment_isactive) VALUES
('Follow-up', true),
('Consultation', true),
('Routine Checkup', true),
('Emergency', true);

INSERT INTO employee_profile_roles (employee_profile_role_name, employee_profile_role_isactive) VALUES
('Doctor', true),
('Nurse', true),
('Admin', true);

INSERT INTO employee_speciality (employee_speciality_name, employee_speciality_isactive) VALUES
('General Physician', true),
('Cardiology', true),
('Neurology', true);

INSERT INTO employee_profile (
    employee_profile_firstname, 
    employee_profile_email_id, 
    employee_profile_role,
    employee_profile_isactive,
    employee_profile_role_id,
    employee_profile_speciality
) VALUES (
    'Fahim',
    'fahim@dreamemr.shop',
    'Doctor',
    true,
    (SELECT employee_profile_role_id FROM employee_profile_roles WHERE employee_profile_role_name = 'Doctor' LIMIT 1),
    (SELECT employee_speciality_id FROM employee_speciality WHERE employee_speciality_name = 'General Physician' LIMIT 1)
);
