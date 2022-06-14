INSERT INTO sample_entity (name, entity_code)
VALUES ('A', 'Code_A');

INSERT INTO sample_entity (name, entity_code)
VALUES ('B', 'Code_B');

-- this insert will cause error because of duplicate 'Code_B'
INSERT INTO sample_entity (name, entity_code)
VALUES ('ErrorB', 'Code_B');

INSERT INTO sample_entity (name, entity_code)
VALUES ('C', 'Code_C');

-- this insert will cause error because of duplicate 'Code_C'
INSERT INTO sample_entity (name, entity_code)
VALUES ('ErrorC', 'Code_C');