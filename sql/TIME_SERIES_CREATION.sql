CREATE TABLE time_series
(
    id INT PRIMARY KEY NOT NULL,
	title VARCHAR2(100) NOT NULL,
	comments VARCHAR2(500 CHAR),
	creation_date TIMESTAMP NOT NULL,
);