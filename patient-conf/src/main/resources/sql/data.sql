-- Patients féminins avec plus de 30 ans
INSERT INTO patients (lastname, firstname, birthday, age, gender)
VALUES
    ('Dupont', 'Marie', '1980-05-15', 41, 'F'),
    ('Martin', 'Sophie', '1982-11-28', 38, 'F'),
    ('Lefevre', 'Laura', '1979-08-10', 42, 'F'),
    ('Bernard', 'Alice', '1978-02-03', 44, 'F'),
    ('Robert', 'Julie', '1985-07-22', 36, 'F');

-- Patients féminins avec moins de 30 ans
INSERT INTO patients (lastname, firstname, birthday, age, gender)
VALUES
    ('Petit', 'Emma', '1992-03-08', 29, 'F'),
    ('Dubois', 'Camille', '1998-09-17', 24, 'F'),
    ('Moreau', 'Léa', '1995-11-30', 27, 'F'),
    ('Roux', 'Chloé', '1999-06-14', 24, 'F'),
    ('Girard', 'Manon', '1993-12-01', 28, 'F');

-- Patients masculins avec plus de 30 ans
INSERT INTO patients (lastname, firstname, birthday, age, gender)
VALUES
    ('Lecomte', 'Jean', '1976-04-20', 47, 'M'),
    ('Garcia', 'Pierre', '1988-12-12', 32, 'M'),
    ('Michel', 'Thomas', '1975-09-05', 46, 'M'),
    ('Rodriguez', 'Nicolas', '1983-07-18', 38, 'M'),
    ('Leroy', 'Paul', '1981-02-25', 40, 'M');

-- Patients masculins avec moins de 30 ans
INSERT INTO patients (lastname, firstname, birthday, age, gender)
VALUES
    ('Bertrand', 'Lucas', '1990-10-03', 31, 'M'),
    ('Guerin', 'Hugo', '1997-11-02', 25, 'M'),
    ('Dumont', 'Maxime', '1994-06-29', 29, 'M'),
    ('Dupuis', 'Antoine', '1998-08-11', 24, 'M'),
    ('Marchand', 'Alexis', '1993-09-06', 28, 'M');
