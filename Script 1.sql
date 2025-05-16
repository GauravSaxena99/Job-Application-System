Create Database ApplicationSystem;

use ApplicationSystem;

CREATE TABLE User (
    ID INT PRIMARY KEY,
    Username VARCHAR(20),
    Password VARCHAR(20),
    Role VARCHAR(20)
);

#Renaming ID to User_ID in User table;
Alter table User change ID User_ID INT;

#adding new column into User table
Alter table User Add Name Varchar(20);


SELECT * FROM user;


CREATE TABLE Job_applications (
    ID INT PRIMARY KEY,
    Job_Title VARCHAR(30),
    Job_Description VARCHAR(255),
    Created_by VARCHAR(20),
    Status VARCHAR(10)
);

Select * from Job_applications;

CREATE TABLE Application (
    Job_ID INT,
    Application_ID INT PRIMARY KEY
);
#Job_ID, Application_ID, Name, PhoneNumber, Email, Address, Position

ALTER TABLE Application MODIFY COLUMN PhoneNumber varchar(15);

#Adding NOT NULL and Auto_Increment to Application_ID
ALTER TABLE Application
MODIFY COLUMN Application_ID INT NOT NULL AUTO_INCREMENT;


#Adding another column in Application Table
Alter table Application Add InterviewerID Int;

#Appying foreign key 
Alter Table Application ADD Foreign key(InterviewerID) References User(User_ID);

SELECT * FROM Application;


CREATE TABLE Feedback (
    ID INT,
    Application_ID INT,
    Interviewer_ID INT,
    Comment VARCHAR(255),
    Rating INT
);

Show Tables;

#Default values inserted into User table.
INSERT into User Values(1,  "Admin" , 1234, "Admin"),
(2, "HR", 4321, "Human Resource"),
(3, "Interview", 5678, "Interviewer");


 
 
#Inserting values into Job_Application table
Insert Into job_applications Values(1,"Front-end Developer", "Develop the frontend part using Tech-Stack : HTML/CSS, React, Angular", "HR", "Open"),
(2,"Back-end Developer", "Develop the backend part using Tech-Stack : Node.js, Javascript, Java, Php", "HR", "Open"),
(3,"Full-stack Developer", "Develop the frontend as well as backend part using Tech-Stack : HTML/CSS, React, Angular, React,Node.js, Java", "HR", "Open"),
(4,"Project Manager", "Responsibility of assigning project to suitable team", "Admin", "Open"),
(5,"Business Analyst", "Analyis of business data.", "Admin", "Open"),
(6,"Desktop Support", "Supoort clients query over call or emails.", "HR", "Open"),
(7,"Network Engineer", "Maitain the network reliabilty, security.", "Admin", "Open"),
(8,"CyberSecurity Expert", "Managing the scurity part of company.", "Admin", "Open"),
(9,"Java Trainer", "Must have 5 years experience in java", "Admin", "Open"),
(10,"Front-end developer Intern", "Help and collab with teams for creating visual appealing websites.", "HR", "Open");


SELECT * FROM Job_applications;


Show Tables from ApplicationSystem;

Show Grants for root;

CREATE TABLE Permissions (
    Role VARCHAR(20),
    View BOOLEAN NOT NULL DEFAULT FALSE,
    Edit BOOLEAN NOT NULL DEFAULT FALSE,
    Del BOOLEAN NOT NULL DEFAULT FALSE,
    Can_Update BOOLEAN NOT NULL DEFAULT FALSE,
    Can_Create BOOLEAN NOT NULL DEFAULT FALSE
);

Alter Table Permissions Add Column Feedback Boolean NOT NULL Default False;

Insert into Permissions (Role, View, Edit, Del, Can_Update, Can_Create, Feedback)
Values('HR', True, False, True, False, True, False),
('Interviewer', True, False, False, False, False, True);


Alter Table Permissions Change Feedback Can_Feedback Boolean;
ALTER TABLE Permissions
ADD COLUMN Last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

SELECT * FROM Permissions;

Describe Permissions;



