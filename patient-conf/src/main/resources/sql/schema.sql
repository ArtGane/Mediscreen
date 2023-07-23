CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    lastname VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    age INT,
    gender VARCHAR(10) NOT NULL,
    address VARCHAR(255) DEFAULT '',
    phoneNumber VARCHAR(20) DEFAULT ''
);