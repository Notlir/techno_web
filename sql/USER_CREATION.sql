CREATE TABLE user
(
      id INT PRIMARY KEY NOT NULL,
      login NVARCHAR(40) UNIQUE NOT NULL,
      salt CHAR(25),
      password varbinary(20)
);