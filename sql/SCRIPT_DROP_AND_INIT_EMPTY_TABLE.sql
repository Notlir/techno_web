DROP table EVENT, TIME_SERIES, USER, USER_SERIES_RIGHT IF EXISTS;
CREATE TABLE time_series
(
    id UUID  PRIMARY KEY NOT NULL,
	title VARCHAR2(100) NOT NULL,
	comments VARCHAR2(500 CHAR),
	creation_date TIMESTAMP NOT NULL,
);
CREATE TABLE user_series_right 
( 
	id UUID  PRIMARY KEY NOT NULL,
	user_id UUID  NOT NULL,
	time_series_id UUID  NOT NULL,
	right BOOLEAN,
);
CREATE TABLE user
(
      id UUID  PRIMARY KEY NOT NULL,
      login NVARCHAR(40) UNIQUE NOT NULL,
      salt CHAR(25) NOT NULL,
      password VARBINARY(20) NOT NULL
);
CREATE TABLE event(
	id UUID  PRIMARY KEY NOT NULL,
	event_date TIMESTAMP NOT NULL,
	value  FLOAT NOT NULL,
	tag VARCHAR2(100 CHAR),
	comments VARCHAR2(500 CHAR),
	time_series_id UUID  NOT NULL
);