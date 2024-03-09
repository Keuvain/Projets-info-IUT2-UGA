/*Table role
Contrainte: content en majuscule
*/
CREATE TABLE Role (
    id serial PRIMARY KEY,
    content VARCHAR(5) CHECK (content = UPPER(content))
);

/*Table Person:
Info: Password est le hash du mot de passe, jamais le mot de passe
    - creation date est la date courante si pas donnée
*/
CREATE TABLE Utilisateur (
    id serial PRIMARY KEY,
    roleID int REFERENCES Role(id) NOT NULL,
    Nom varchar(30),
    prenom varchar(30),
    login varchar(30),
    password varchar(64),
    email varchar(50),
    creationDate DATE DEFAULT CURRENT_DATE
);

/*Table Class:
Info: creation date est la date courante si pas donnée
*/
CREATE TABLE Class (
    id serial PRIMARY KEY,
    name varchar(30),
    creationDate DATE DEFAULT CURRENT_DATE,
    code varchar(5)
);

/*Table StudentClass:*/
CREATE TABLE Etudiants (
    studentId INT PRIMARY KEY REFERENCES Utilisateur(id) NOT NULL,
    classId INT REFERENCES Class(id)
);


/*Table ClassTeacher:*/
CREATE TABLE Profs (
    classId INT PRIMARY KEY REFERENCES Class(id) NOT NULL,
    teacherId INT REFERENCES Utilisateur(id)
);

CREATE Table Score (
    id int REFERENCES Utilisateur(id) NOT NULL,
    temps time
);

CREATE Table Classement (
    id int REFERENCES Utilisateur(id) NOT NULL,
    place int
);