Use ApplicationSystem;
DESCRIBE Application;
DESCRIBE User;

UPDATE User
SET Name = "Gaurav"
Where User_ID = 1;

UPDATE User
SET Name = "Yuvraaj"
Where User_ID = 2;

UPDATE USER 
SET Name = "Aditya"
Where User_ID = 3;


UPDATE Application
SET InterviewerID = 2
Where Application_ID = 1;


UPDATE Application
SET InterviewerID = 3
Where Application_ID = 2;


UPDATE Application
SET InterviewerID = 1
WHERE Application_ID = 3;

UPDATE Application
Set InterviewerID = 2
WHERE Application_ID = 5;

UPDATE Application
Set InterviewerID = 2
WHERE Application_ID = 6;

update Application
Set InterviewerID = 1
WHERE Application_ID = 7;

update Application
Set InterviewerID = 3
WHERE Application_ID = 8;



SELECT Application.Name, PhoneNumber, Email, Address, Position, User.Name as Interviewer FROM Application
Left JOIN User ON User.User_ID = Application.InterviewerID;

SELECT * FROM Application;

