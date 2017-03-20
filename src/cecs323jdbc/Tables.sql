
CREATE TABLE WritingGroups(
    groupName   VARCHAR(50) NOT NULL,
    headWriter  VARCHAR(50) NOT NULL,
    yearFormed  INT         NOT NULL,
    subject     VARCHAR(50) NOT NULL,

    CONSTRAINT  writingGroup_pk PRIMARY KEY (groupName));
INSERT INTO WritingGroups(groupName, headWriter, yearFormed, subject) VALUES
    ('Team Mac', 'Matt K', 1999, 'Sci-Fi'),
    ('DB&M','Man Rat',1978,'Mystery'),
    ('Z-Fighters','Son Goku',1995,'Anime'),
    ('Last Hope','Gohan',1998,'Anime');

CREATE TABLE Publishers(
    publisherName       VARCHAR(50) NOT NULL,
    publisherAddress    VARCHAR(50) NOT NULL,
    publisherPhone      VARCHAR(50) NOT NULL,
    publisherEmail      VARCHAR(50) NOT NULL,

    CONSTRAINT          publisher_pk PRIMARY KEY (publisherName));
INSERT INTO Publishers(publisherName, publisherAddress, publisherPhone, publisherEmail) VALUES
    ('Simon & Schuster','1230 Avenue of the Americas, 10th F, New York, NY','1-800-223-2336','Simonschuster@SHP.com'),
    ('Harper Collins','195 BroadwayNew York, NY 10007','1-212-207-7000','hello@harpercollins.com'),
    ('Penguin Random House','1745 Broadway New York, NY 10019','1-212-782-9000','customerservice@penguinrandomhouse.com'), 
    ('Hachette Livre','1290 Avenue of the Americas New York, NY 10104','1-800-759-0190','grandcentralpublishing@hbgusa.com'),
    ('Randy Newman Publishing', '123 Randy Street, Los Angeles, 900210','1-800-456-8352', 'RandyNewman@LAlovers.gov');
CREATE TABLE Books(
    bookTitle       VARCHAR(50) NOT NULL,
    yearPublished           INT NOT NULL,
    numberPages             INT NOT NULL,
    groupName       VARCHAR(50) NOT NULL,
    publisherName   VARCHAR(50) NOT NULL,

    CONSTRAINT      book_pk PRIMARY KEY (groupName, bookTitle),
    CONSTRAINT      book_fk1 FOREIGN KEY (groupName)
                REFERENCES WritingGroups(groupName),
    CONSTRAINT      book_fk2 FOREIGN KEY (publisherName)
                REFERENCES Publishers(publisherName));
INSERT INTO Books(bookTitle, yearpublished, numberPages, groupName, publisherName)VALUES
    ('How to Be Sassy 101', 2003, 234,'Team Mac', 'Harper Collins'),
    ('Do You Love LA?',1985, 156, 'Last Hope','Randy Newman Publishing'),
    ('The Downfall of Peter Pan',1994 ,340 ,'DB&M','Penguin Random House'),
    ('When the Shoe Attacks', 1965, 285,'Z-Fighters','Simon & Schuster'),
    ('The Antique Chair', 1976, 400, 'Team Mac', 'Hachette Livre');
