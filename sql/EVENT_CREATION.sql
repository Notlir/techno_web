CREATE TABLE event(
	id UUID  PRIMARY KEY NOT NULL,
	event_date TIMESTAMP NOT NULL,
	value  FLOAT NOT NULL,
	comments VARCHAR2(500 CHAR),
	time_series_id UUID  NOT NULL
);
