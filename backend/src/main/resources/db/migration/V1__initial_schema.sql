CREATE TABLE filter_user
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(255) NOT NULL DEFAULT 'USER',
    created_at DATE         NOT NULL
);

CREATE TABLE filter
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT          NOT NULL,
    name       VARCHAR(255) NOT NULL,
    created_at DATE         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES filter_user (id)
);

CREATE TABLE criteria_type
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL,
    data_type VARCHAR(255) NOT NULL
);

CREATE TABLE comparison_condition
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    criteria_type_id INT          NOT NULL,
    name             VARCHAR(255) NOT NULL,
    FOREIGN KEY (criteria_type_id) REFERENCES criteria_type (id)
);

CREATE TABLE filter_criteria
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    filter_id        INT          NOT NULL,
    criteria_type_id INT          NOT NULL,
    condition_id     INT          NOT NULL,
    user_id          INT          NOT NULL,
    criteria_value   VARCHAR(255) NOT NULL,
    FOREIGN KEY (filter_id) REFERENCES filter (id),
    FOREIGN KEY (criteria_type_id) REFERENCES criteria_type (id),
    FOREIGN KEY (condition_id) REFERENCES comparison_condition (id),
    FOREIGN KEY (user_id) REFERENCES filter_user (id)
);

INSERT INTO filter_user(username, password, role, created_at)
VALUES ('admin', '$2a$10$6Rn1ZqOGVNLRk9QWH97AqeJ.PAqWT8CjGGgBDK2YsnbWzHZ7sHV.S', 'ADMIN', NOW()),
       ('random', '$2a$10$4OWDEIv.GtluZw5jSKQAYefet1yAXW6Q16mk7ouqrcEelY5.1L8N6', 'USER', NOW());

INSERT INTO criteria_type (type_name, data_type)
VALUES ('Amount', 'NUMBER'),
       ('Title', 'STRING'),
       ('Date', 'DATE');

INSERT INTO comparison_condition (criteria_type_id, name)
VALUES ((SELECT id FROM criteria_type WHERE type_name = 'Amount'), 'More'),
       ((SELECT id FROM criteria_type WHERE type_name = 'Amount'), 'Less'),
       ((SELECT id FROM criteria_type WHERE type_name = 'Amount'), 'Equal'),

       ((SELECT id FROM criteria_type WHERE type_name = 'Title'), 'Contains'),
       ((SELECT id FROM criteria_type WHERE type_name = 'Title'), 'Starts with'),
       ((SELECT id FROM criteria_type WHERE type_name = 'Title'), 'Ends with'),
       ((SELECT id FROM criteria_type WHERE type_name = 'Title'), 'Equals'),

       ((SELECT id FROM criteria_type WHERE type_name = 'Date'), 'To'),
       ((SELECT id FROM criteria_type WHERE type_name = 'Date'), 'From'),
       ((SELECT id FROM criteria_type WHERE type_name = 'Date'), 'Equal');

INSERT INTO filter (name, user_id, created_at)
VALUES ('Budget Filter', (SELECT id FROM filter_user WHERE username = 'admin'), NOW()),
       ('Sales Filter', (SELECT id FROM filter_user WHERE username = 'admin'), NOW());

INSERT INTO filter_criteria (filter_id, criteria_type_id, condition_id, user_id, criteria_value)
VALUES ((SELECT id FROM filter WHERE name = 'Budget Filter'),
        (SELECT id FROM criteria_type WHERE type_name = 'Amount'),
        (SELECT id FROM comparison_condition WHERE name = 'Less'),
        (SELECT id FROM filter_user WHERE username = 'admin'),
        '1500'),
       ((SELECT id FROM filter WHERE name = 'Budget Filter'),
        (SELECT id FROM criteria_type WHERE type_name = 'Date'),
        (SELECT id FROM comparison_condition WHERE name = 'From'),
        (SELECT id FROM filter_user WHERE username = 'admin'),
        NOW()),

       ((SELECT id FROM filter WHERE name = 'Sales Filter'),
        (SELECT id FROM criteria_type WHERE type_name = 'Title'),
        (SELECT id FROM comparison_condition WHERE name = 'Contains'),
        (SELECT id FROM filter_user WHERE username = 'admin'),
        'Q1 Report'),
       ((SELECT id FROM filter WHERE name = 'Sales Filter'),
        (SELECT id FROM criteria_type WHERE type_name = 'Amount'),
        (SELECT id FROM comparison_condition WHERE name = 'More'),
        (SELECT id FROM filter_user WHERE username = 'admin'),
        '2000');
