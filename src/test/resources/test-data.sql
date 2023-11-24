INSERT INTO Person (Id, First_Name, Last_Name, Date_Of_Birth)
VALUES
(2, 'Stewart', 'Rowney', '1983-09-02 10:43:23'),
(3, 'Ayush', 'Pandey', '2020-01-17 10:43:23'),
(4, 'Thomas', 'Rands', '1956-03-05 10:43:23'),
(5, 'Abhijeet', 'Kale', '2010-10-26 10:43:23');

INSERT INTO Message(Id, Content, Sender_Id)
VALUES
(2, 'This is a message', (SELECT Id FROM Person WHERE Id = 2)),
(3, 'hello', (SELECT Id FROM Person WHERE Id = 2)),
(4, 'everyone', (SELECT Id FROM Person WHERE Id = 2)),
(5, 'message', (SELECT Id FROM Person WHERE Id = 4));
