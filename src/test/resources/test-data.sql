INSERT INTO Person (Id, First_Name, Last_Name, Date_Of_Birth)
VALUES
(1, 'Stewart', 'Rowney', '1983-09-02 10:43:23'),
(2, 'Ayush', 'Pandey', '2020-01-17 10:43:23'),
(3, 'Thomas', 'Rands', '1956-03-05 10:43:23'),
(4, 'Abhijeet', 'Kale', '2010-10-26 10:43:23');

INSERT INTO Message(Id, Content, Sender_Id)
VALUES
(5, 'This is a message', (SELECT Id FROM Person WHERE Id = 1)),
(2, 'hello', (SELECT Id FROM Person WHERE Id = 1)),
(3, 'everyone', (SELECT Id FROM Person WHERE Id = 1)),
(4, 'message', (SELECT Id FROM Person WHERE Id = 2));
