CREATE TABLE user_series_right 
( 
	id INT PRIMARY KEY NOT NULL,
	user_id INT NOT NULL,
	serie_id INT NOT NULL,
	right BOOLEAN,
);