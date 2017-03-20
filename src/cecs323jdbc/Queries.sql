-- List all writing groups
SELECT groupName FROM WritingGroups;

-- List all the data for a group specified by the user
SELECT * FROM WritingGroups
WHERE groupName = 'Team Mac';

-- List all publishers
SELECT publisherName FROM Publishers;

-- List all the data for a publisher specified by the user
SELECT * FROM Publishers
WHERE publisherName = 'Harper Collins';

-- List all book titles
SELECT bookTitle FROM Books;

-- List all the data for a book specified by the user
SELECT * FROM Books
WHERE bookTitle = 'How to Be Sassy 101';

-- Insert a new book
INSERT INTO Books(bookTitle, yearPublished, numberPages, groupName, publisherName)
    VALUES ('bookTitle', 2000, 200, 'groupName', 'publisherName');

-- Insert a new publisher and update all book published by one publisher to be
-- published by the new publisher.
INSERT INTO Publishers(publisherName, publisherAddress, publisherPhone, publisherEmail)
    VALUES ('publisherName', 'publisherAddress', 'publisherPhone', 'publisherEmail');
UPDATE Books SET publisherName = 'newPublisher'
WHERE publisherName = 'oldPublisher';
DELETE FROM Publishers
WHERE publisherName = 'oldPublisher';

-- Remove a book specified by the user
DELETE FROM Books
WHERE bookTitle = 'bookTitle';
