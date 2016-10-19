CREATE TABLE user(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255),
	CONSTRAINT pk_user PRIMARY KEY (id));

CREATE TABLE todo(
	id_todo INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	due_date DATETIME NOT NULL, 
	done BOOLEAN NOT NULL,
	CONSTRAINT pk_todo PRIMARY KEY (id_todo));
	