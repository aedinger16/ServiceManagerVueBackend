SET GLOBAL time_zone = '+1:00';

/* Insert Employees */
INSERT INTO employees (id, latitude, longitude, name) VALUES (1, '54.61012', '132.06464', 'Lexl');
INSERT INTO employees (id, latitude, longitude, name) VALUES (2, '-8.79552', '-36.97237', 'Maxl');

/* Insert Services */
INSERT INTO services (id, date, employee_id, latitude, longitude, name) VALUES (1, 'Thu, 23 Apr 2020 12:22:13 GMT', 1, '48.301685', '13.941405', 'Putzen');
INSERT INTO services (id, date, employee_id, latitude, longitude, name) VALUES (2, 'Thu, 23 Apr 2020 12:22:13 GMT', 2, '48.300180', '13.940533', 'Rasenmähen');
