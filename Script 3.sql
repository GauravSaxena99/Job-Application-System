describe job_applications;

Insert Into job_applications Values(4,"Project Manager", "Responsibility of assigning project to suitable team", "Admin", "Open"); 

Alter table Application Drop ID;
Select * from Application;

Alter table Application change Resume_Path Resume LONGBLOB;

Select * from user;



#Demo Tables

#Create Table Demo_Users (ID INT Primary Key, Username varchar(20), Password varchar(20), Role varchar(20));

Create Table Demo_User
(ID INT Primary Key,
 Username varchar(20), 
 Password varchar(20),
 Role varchar(20));


#Create Demo_Application table
Create Table Demo_Application_Table(Job_ID Int,
 Application_ID INT auto_increment primary key, 
ID INT,
Foreign key (ID) References Demo_User(ID),
Name varchar(20), 
PhoneNumber varchar(20),
Email varchar(20), 
Address varchar(20), 
Position varchar(20)
);

#Default values inserted into Demo_User table.
#INSERT into Demo_Users Values(1,  "Admin" , 1234, "Admin"),
INSERT into Demo_User(Username , Password, Role)
Values
('Admin' , 1234, 'Admin'),
('HR', 4321, 'Human Resource'),
('Interview', 5678, 'Interviewer'),
('HR2', 4321, 'Human Resource 2'),
('Interviewer2', 5678, 'Interviewer 2');


INSERT INTO Demo_Application_Table (Job_ID, ID, Name, PhoneNumber, Email, Address, Position)
VALUES 
(101, 4, 'Alice Smith', '123-456-7890', 'alice@example.com', '123 Main St', 'Engineer'),
(102, 5, 'Bob Johnson', '234-567-8901', 'bob@example.com', '456 Oak Rd', 'Analyst'),
(103, 6, 'Carol Lee', '345-678-9012', 'carol@example.com', '789 Pine Ave', 'Manager'),
(104, 4, 'David Kim', '456-789-0123', 'david@example.com', '321 Cedar Blvd', 'Designer'),
(105, 6, 'Eve Clark', '567-890-1234', 'eve@example.com', '654 Elm St', 'Developer');







Select * FROM Demo_Application_Table;
select * from Demo_User;


Select Demo_Users.Role , Demo_Application_Table.Application_ID, Demo_Application_Table.Name, Demo_Application_Table.Position From Demo_Users
Join Demo_Application_Table ON Demo_Users.ID = Demo_Application_Table.ID;


SELECT CONSTRAINT_NAME 
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
WHERE TABLE_NAME = 'Demo_Application_Table' 
AND REFERENCED_TABLE_NAME = 'Demo_Users';

Drop Table Demo_Users;
Drop Table Demo_Application_Table;






