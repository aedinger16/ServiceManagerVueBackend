SET GLOBAL time_zone = '+1:00';

/* Insert Employees */
INSERT INTO employees (id, latitude, longitude, name) VALUES (1, '54.61012', '132.06464', 'Lexl');
INSERT INTO employees (id, latitude, longitude, name) VALUES (2, '-8.79552', '-36.97237', 'Maxl');

/* Insert Services */
INSERT INTO services (id, date, employee_id, latitude, longitude, name) VALUES (1, '29.03.2020 13:24', 1, '54.61012', '132.06464', 'S1');
INSERT INTO services (id, date, employee_id, latitude, longitude, name) VALUES (2, '29.03.2020 20:20', 2, '-8.79552', '-36.97237', 'S2');
