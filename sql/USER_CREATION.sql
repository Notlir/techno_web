CREATE TABLE user
(
      id UUID  PRIMARY KEY NOT NULL,
      login NVARCHAR(40) UNIQUE NOT NULL,
      salt CHAR(25) NOT NULL,
      password VARCHAR2(256 CHAR) NOT NULL,
      token UUID,
      token_creation TIMESTAMP
);
