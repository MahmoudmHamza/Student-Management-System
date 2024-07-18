TRUNCATE registrations;

ALTER TABLE registrations ADD version NUMBER(10) NOT NULL;
ALTER TABLE registrations DROP COLUMN status;

INSERT INTO registrations (user_id, course_id, registration_date, version)
VALUES (1, 1, SYSTIMESTAMP, 0);
INSERT INTO registrations (user_id, course_id, registration_date, version)
VALUES (2, 1, SYSTIMESTAMP, 0);
INSERT INTO registrations (user_id, course_id, registration_date, version)
VALUES (1, 2, SYSTIMESTAMP, 0);