CREATE TABLE event_tag 
( 
	id UUID  PRIMARY KEY NOT NULL,
	event_id UUID  NOT NULL,
	tag_id UUID  NOT NULL,
);
