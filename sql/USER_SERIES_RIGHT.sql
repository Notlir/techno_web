CREATE TABLE user_series_right 
( 
	id UUID  PRIMARY KEY NOT NULL,
	user_id UUID  NOT NULL,
	time_series_id UUID  NOT NULL,
	right BOOLEAN,
);