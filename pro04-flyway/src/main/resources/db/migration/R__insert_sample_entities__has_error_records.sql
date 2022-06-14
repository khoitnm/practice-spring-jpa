INSERT INTO sample_entity (name, entity_code)
VALUES ('A', 'Code_A') ON DUPLICATE KEY
UPDATE name = 'A';

INSERT INTO sample_entity (name, entity_code)
VALUES ('B', 'Code_B') ON DUPLICATE KEY
UPDATE name = 'B';

-- Duplicate entity_code: no error, it will be override the existing value.
INSERT INTO sample_entity (name, entity_code)
VALUES ('ErrorB', 'Code_B') ON DUPLICATE KEY
UPDATE name = 'ErrorB';

INSERT INTO sample_entity (name, entity_code)
VALUES ('C', 'Code_C') ON DUPLICATE KEY
UPDATE name = 'C';

-- Duplicate entity_code: no error, it will be override the existing value.
INSERT INTO sample_entity (name, entity_code)
VALUES ('ErrorC', 'Code_C') ON DUPLICATE KEY
UPDATE name = 'ErrorC';

-- Error, name is NULL
INSERT INTO sample_entity (name, entity_code)
VALUES (NULL, 'Code_D') ON DUPLICATE KEY
UPDATE name = NULL;

-- Error, name is NULL
INSERT INTO sample_entity (name, entity_code)
VALUES (NULL, 'Code_E') ON DUPLICATE KEY
UPDATE name = NULL;

