USE master;

GO

IF EXISTS (SELECT 1 FROM sys.databases WHERE name = 'Dictionary')
BEGIN
    DROP DATABASE Dictionary;
END

GO

CREATE DATABASE Dictionary;

GO

USE Dictionary;

GO

CREATE TABLE [dbo].[users] (
    [user_id] INT IDENTITY(10000,1),
    [username] VARCHAR(50) NOT NULL UNIQUE,
    [full_name] NVARCHAR(100) NOT NULL,
    [password] VARCHAR(100) NOT NULL,
    [email] VARCHAR(100) NOT NULL UNIQUE,
    [image_url] VARCHAR(255) NULL,
    [active_status] BIT NOT NULL DEFAULT 1,
    [created_at] DATETIME,
    PRIMARY KEY ([user_id]),
);

CREATE TABLE [dbo].[roles] (
    [role_id] INT IDENTITY(10000,1),
    [role_name] VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY ([role_id])
);

CREATE TABLE [dbo].[user_role] (
    [user_id] INT NOT NULL,
    [role_id] INT NOT NULL,
    PRIMARY KEY ([user_id], [role_id]),
    FOREIGN KEY ([user_id]) REFERENCES [users] ([user_id]),
    FOREIGN KEY ([role_id]) REFERENCES [roles] ([role_id])
);

CREATE TABLE [dbo].[languages] (
    [language_id] INT IDENTITY(10000,1),
    [language_name] VARCHAR(50) NOT NULL UNIQUE,
    [enable_status] BIT NOT NULL DEFAULT 1,
    PRIMARY KEY ([language_id])
);

CREATE TABLE [dbo].[words] (
    [word_id] INT IDENTITY(10000,1),
    [created_by] INT NOT NULL,
    [term] NVARCHAR(255) NOT NULL,
    [pronunciation] NVARCHAR(255) NULL,
    [language_id] INT NULL,
    [created_at] DATETIME,
    [modified_at] DATETIME,
    [editable] BIT NOT NULL DEFAULT 1,
    PRIMARY KEY ([word_id]),
    FOREIGN KEY ([created_by]) REFERENCES [users] ([user_id]),
    FOREIGN KEY ([language_id]) REFERENCES [languages] ([language_id])
);

CREATE TABLE [dbo].[types] (
    [type_id] INT IDENTITY(10000,1),
    [type_name] VARCHAR(20) NOT NULL UNIQUE,
    PRIMARY KEY ([type_id])
);

CREATE TABLE [dbo].[definitions] (
    [definition_id] INT IDENTITY(10000,1),
    [word_id] INT NOT NULL,
    [type_id] INT NULL,
    [definition] NVARCHAR(1000) NOT NULL,
    [example] NVARCHAR(1000),
    PRIMARY KEY (definition_id),
    FOREIGN KEY ([word_id]) REFERENCES [words] ([word_id]),
    FOREIGN KEY ([type_id]) REFERENCES [types] ([type_id])
);


-- CREATE INDEX idx_username ON [users] (username);

-- CREATE INDEX idx_email ON [users] (email);

-- CREATE INDEX idx_language_name ON [languages] (language_name);

-- CREATE INDEX idx_created_by ON [words] (created_by);

-- CREATE INDEX idx_language_id ON [words] (language_id);

-- CREATE INDEX idx_word_id ON [definitions] (word_id);


INSERT INTO [roles] ([role_name]) VALUES ('SUPER_ADMIN');
INSERT INTO [roles] ([role_name]) VALUES ('ADMIN');
INSERT INTO [roles] ([role_name]) VALUES ('USER');

GO

INSERT INTO [users] ([username], [full_name], [password], [email], [image_url], [active_status], [created_at])
VALUES ('ninggiangboy', 'Ha Duy Khanh', '$2a$10$dsEu/f/3rMd8Lz6hD/5bXu9at6dUKubYlJ2O7ykF.U/.Pn9wCVpI6', 'ninggiangboy@gmail.com', NULL, 1, '2022-01-01 00:00:00');

GO

INSERT INTO [user_role] ([user_id], [role_id]) 
VALUES (10000, 10000), (10000, 10001), (10000, 10002);

GO

INSERT INTO [types] ([type_name]) VALUES ('Noun');
INSERT INTO [types] ([type_name]) VALUES ('Verb');
INSERT INTO [types] ([type_name]) VALUES ('Adjective');
INSERT INTO [types] ([type_name]) VALUES ('Adverb');
INSERT INTO [types] ([type_name]) VALUES ('Pronoun');
INSERT INTO [types] ([type_name]) VALUES ('Preposition');
INSERT INTO [types] ([type_name]) VALUES ('Conjunction');
INSERT INTO [types] ([type_name]) VALUES ('Interjection');

GO

INSERT INTO [languages] ([language_name]) VALUES ('English');
INSERT INTO [languages] ([language_name]) VALUES ('Vietnamese');
INSERT INTO [languages] ([language_name]) VALUES ('Spanish');
INSERT INTO [languages] ([language_name]) VALUES ('French');
INSERT INTO [languages] ([language_name]) VALUES ('German');
INSERT INTO [languages] ([language_name]) VALUES ('Italian');
INSERT INTO [languages] ([language_name]) VALUES ('Portuguese');
INSERT INTO [languages] ([language_name]) VALUES ('Chinese');
INSERT INTO [languages] ([language_name]) VALUES ('Japanese');
INSERT INTO [languages] ([language_name]) VALUES ('Korean');
INSERT INTO [languages] ([language_name]) VALUES ('Arabic');
INSERT INTO [languages] ([language_name]) VALUES ('Russian');
INSERT INTO [languages] ([language_name]) VALUES ('Dutch');
INSERT INTO [languages] ([language_name]) VALUES ('Swedish');
INSERT INTO [languages] ([language_name]) VALUES ('Norwegian');
INSERT INTO [languages] ([language_name]) VALUES ('Danish');
INSERT INTO [languages] ([language_name]) VALUES ('Greek');
INSERT INTO [languages] ([language_name]) VALUES ('Turkish');
INSERT INTO [languages] ([language_name]) VALUES ('Hindi');
INSERT INTO [languages] ([language_name]) VALUES ('Bengali');
INSERT INTO [languages] ([language_name]) VALUES ('Thai');

GO



-- use Dictionary;

-- SELECT DISTINCT l.* FROM words w JOIN languages l ON w.language_id = l.language_id WHERE w.created_by = 10000;

-- select all user have role user and not is ADMIN or SUPER_ADMIN

-- SELECT u.*
-- FROM users u
-- JOIN user_role ur ON u.user_id = ur.user_id
-- JOIN roles r ON ur.role_id = r.role_id
-- WHERE u.user_id NOT IN (
--     SELECT ur.user_id
--     FROM user_role ur
--     JOIN roles r ON ur.role_id = r.role_id
--     WHERE r.role_name IN ('ADMIN', 'SUPER_ADMIN')
--   )
-- AND (u.username = 'admin1' OR u.email = '');

-- select all user is ADMIN or SUPER_ADMIN

-- SELECT u.*, r.role_name FROM users u JOIN user_role ur ON u.user_id = ur.user_id JOIN roles r ON ur.role_id = r.role_id 

-- select words order by modified time

-- SELECT w.*, l.language_name FROM words w JOIN languages l ON w.language_id = l.language_id ORDER BY w.modified_at DESC;



-- Delete rows from table 'languages'
-- DELETE FROM languages
-- WHERE language_id != 10000;


-- Assuming the word_id of the word you want to add is 10001 and the user_id is 10000.
-- Modify these values as needed.


GO

INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Hello', 'hello', 10000, 10000, '2022-03-08 10:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('World', 'world', 10000, 10000, '2022-04-15 12:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Computer', 'computer', 10000, 10000, '2022-05-22 14:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Phone', 'phone', 10000, 10000, '2022-06-29 16:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Table', 'table', 10000, 10000, '2019-01-05 18:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Chair', 'chair', 10000, 10000, '2019-02-12 20:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Window', 'window', 10000, 10000, '2019-03-19 22:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Door', 'door', 10000, 10000, '2019-04-26 21:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Cat', 'cat', 10000, 10000, '2019-05-31 00:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Dog', 'dog', 10000, 10000, '2019-06-16 02:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Fish', 'fish', 10000, 10000, '2019-07-31 04:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Bird', 'bird', 10000, 10000, '2019-08-16 06:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Tree', 'tree', 10000, 10000, '2019-09-30 08:00:00', NULL); 
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Car', 'car', 10000, 10000, '2019-10-16 10:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('House', 'house', 10000, 10000, '2019-11-30 12:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('School', 'school', 10000, 10000, '2019-12-16 14:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Hospital', 'hospital', 10000, 10000, '2018-01-30 16:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Store', 'store', 10000, 10000, '2018-02-16 18:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Bank', 'bank', 10000, 10000, '2018-03-30 20:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Sea', 'sea', 10000, 10000, '2018-05-30 00:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Mountain', 'mountain', 10000, 10000, '2018-06-16 02:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('River', 'river', 10000, 10000, '2018-07-30 04:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Sun', 'sun', 10000, 10000, '2018-08-16 06:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Moon', 'moon', 10000, 10000, '2018-09-30 08:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Star', 'star', 10000, 10000, '2018-10-16 10:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Rain', 'rain', 10000, 10000, '2018-11-30 12:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Snow', 'snow', 10000, 10000, '2018-12-16 14:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Wind', 'wind', 10000, 10000, '2017-01-30 16:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Fire', 'fire', 10000, 10000, '2017-02-16 18:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Food', 'food', 10000, 10000, '2017-03-30 20:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Drink', 'drink', 10000, 10000, '2017-04-16 22:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Color', 'color', 10000, 10000, '2017-05-30 00:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Number', 'number', 10000, 10000, '2017-06-16 02:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Animal', 'animal', 10000, 10000, '2017-07-30 04:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Plant', 'plant', 10000, 10000, '2017-08-16 06:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Country', 'country', 10000, 10000, '2017-09-30 08:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('City', 'city', 10000, 10000, '2017-10-16 10:00:00', NULL)
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Town', 'town', 10000, 10000, '2017-11-30 12:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Village', 'village', 10000, 10000, '2017-12-16 14:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Ocean', 'ocean', 10000, 10000, '2018-01-30 16:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Water', 'water', 10000, 10000, '2022-07-06 18:00:00', NULL);
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at]) VALUES ('Book', 'book', 10000, 10000, '2022-08-13 20:00:00', NULL);

GO

INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10000, 10000, '1. A greeting.', 'Hello, how are you?');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10000, 10000, '2. A greeting.', 'Hello, how are you?');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10000, 10000, '3. A greeting.', 'Hello, how are you?');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10000, 10000, '4. A greeting.', 'Hello, how are you?');


INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10001, 10000, 'The entire population of the earth.', 'The world is facing many challenges.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10002, 10000, 'A machine that can process information and perform calculations.', 'The computer is a powerful tool.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10003, 10000, 'A device that can be used to make and receive phone calls.', 'The phone is a necessity for staying connected.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10004, 10000, 'A piece of furniture with a flat top and one or more legs, used for eating, writing, or working at.', 'The table is a versatile piece of furniture.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10005, 10000, 'A piece of furniture with a back and four legs, used for sitting on.', 'The chair is a comfortable place to sit.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10006, 10000, 'A frame with a transparent material in it, used to allow light into a room.', 'The window lets in natural light.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10007, 10000, 'A structure that covers an entrance to a building.', 'The door keeps out the cold and heat.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10008, 10000, 'A domesticated animal that is often kept as a pet.', 'Cats are playful and affectionate animals.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10009, 10000, 'A domesticated animal that is often kept as a pet.', 'Dogs are loyal and protective animals.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10010, 10000, 'A cold-blooded vertebrate animal that lives in water and has gills.', 'Fish are an important part of the food chain.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10011, 10000, 'A bird that has feathers and wings and can fly.', 'Birds are found all over the world.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10012, 10000, 'A large, tall plant with a woody trunk and branches.', 'Trees provide shade and shelter.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10013, 10000, 'A vehicle with four wheels that is used for transportation.', 'Cars are a common form of transportation.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10014, 10000, 'A building where people live.', 'Houses come in all shapes and sizes.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10015, 10000, 'A place where children are educated.', 'Schools are important for learning.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10016, 10000, 'A place where people can go to get medical treatment.', 'Hospitals are staffed with doctors and nurses who can provide care for people who are sick or injured.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10017, 10000, 'A place where people can go to buy goods and services.', 'Stores are a convenient way to get the things we need.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10018, 10000, 'A place where money is kept and business is conducted.', 'Banks are an important part of the economy.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10019, 10000, 'A large body of water that is surrounded by land.', 'The ocean is a vast and mysterious place.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10020, 10000, 'A very high hill or mountain.', 'Mountains are a beautiful and challenging place to explore.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10021, 10000, 'A natural stream of water that flows through land.', 'Rivers are an important source of water for people and animals.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10022, 10000, 'The star that the Earth orbits around.', 'The sun is the center of our solar system.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10023, 10000, 'The natural satellite that orbits around the Earth.', 'The moon is the brightest object in the night sky.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10024, 10000, 'A luminous sphere of plasma held together by its own gravity.', 'Stars are the most common objects in the universe.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10025, 10000, 'Water that falls from the sky.', 'Rain is an important part of the water cycle.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10026, 10000, 'Precipitation in the form of flakes of crystalline water ice.', 'Snow is a common sight in the winter.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10027, 10000, 'The flow of gases on a large scale.', 'Wind is a major source of renewable energy.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10028, 10000, 'The rapid oxidation of a material in the exothermic chemical process of combustion.', 'Fire is a dangerous force of nature.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10029, 10000, 'Any substance consumed to provide nutritional support for an organism.', 'Food is necessary for life.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10030, 10000, 'A liquid intended for human consumption.', 'Water is the most common drink.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10031, 10000, 'The visual perceptual property corresponding in humans to the categories called red, green, blue, and others.', 'The color of the sky is blue.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10032, 10000, 'A mathematical object used to count, measure, and label.', 'The number of planets in our solar system is eight.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10033, 10000, 'A multicellular eukaryotic organism.', 'Humans are animals.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10034, 10000, 'A multicellular eukaryote that includes chloroplasts.', 'Plants are the primary producers of food.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10035, 10000, 'A region identified as a distinct entity in political geography.', 'The United States is a country.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10036, 10000, 'A large human settlement.', 'New York City is a city.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10037, 10000, 'A human settlement larger than a village but smaller than a city.', 'My hometown is a town.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10038, 10000, 'A clustered human settlement or community.', 'My grandparents live in a village.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10039, 10000, 'A body of saline water that composes much of a planet hydrosphere.', 'The Pacific Ocean is the largest ocean on Earth.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10040, 10000, 'A transparent, odorless, tasteless liquid that forms the seas, lakes, rivers, and rain and is the basis of the fluids of living organisms.', 'We need to drink water to stay hydrated.');
INSERT INTO [definitions] ([word_id], [type_id], [definition], [example]) VALUES (10041, 10000, 'A written or printed work consisting of pages glued or sewn together along one side and bound in covers.', 'I love reading books in my free time.');
-- update modified_at of all word to equal created_at
UPDATE [words] SET [modified_at] = [created_at];


INSERT INTO [users] ([username], [full_name], [password], [email], [image_url], [active_status], [created_at])
VALUES ('admin1', 'admin1', '$2a$10$dsEu/f/3rMd8Lz6hD/5bXu9at6dUKubYlJ2O7ykF.U/.Pn9wCVpI6', 'mail1@gmail.com', NULL, 1, '2022-02-02 00:00:00');

INSERT INTO [users] ([username], [full_name], [password], [email], [image_url], [active_status], [created_at])
VALUES ('user1', 'user1', '$2a$10$dsEu/f/3rMd8Lz6hD/5bXu9at6dUKubYlJ2O7ykF.U/.Pn9wCVpI6', 'mail2@gmail.com', NULL, 1, '2022-03-03 00:00:00');

INSERT INTO [user_role] ([user_id], [role_id]) 
VALUES (10001, 10001), (10001, 10002);

INSERT INTO [user_role] ([user_id], [role_id]) 
VALUES (10002, 10002);


GO

CREATE TRIGGER trg_after_user_insert
ON [dbo].[users]
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    -- Insert the predefined words into the [words] table for the newly inserted user
    INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at], [editable]) 
SELECT 'Music', 'music', [user_id], 10000, GETDATE(), GETDATE(), 0 FROM INSERTED;
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at], [editable]) 
SELECT 'Friend', 'friend', [user_id], 10000, GETDATE(), GETDATE(), 0 FROM INSERTED;
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at], [editable]) 
SELECT 'Love', 'love', [user_id], 10000, GETDATE(), GETDATE(), 0 FROM INSERTED;
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at], [editable]) 
SELECT 'Time', 'time', [user_id], 10000, GETDATE(), GETDATE(), 0 FROM INSERTED;
INSERT INTO [words] ([term], [pronunciation], [created_by], [language_id], [created_at], [modified_at], [editable]) 
SELECT 'Money', 'money', [user_id], 10000, GETDATE(), GETDATE(), 0 FROM INSERTED;

END;