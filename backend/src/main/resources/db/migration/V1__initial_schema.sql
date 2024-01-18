CREATE TABLE filter
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at DATE         NOT NULL
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
    filter_id        INT,
    criteria_type_id INT,
    condition_id     INT,
    criteria_value   VARCHAR(255) NOT NULL,
    FOREIGN KEY (filter_id) REFERENCES filter (id),
    FOREIGN KEY (criteria_type_id) REFERENCES criteria_type (id),
    FOREIGN KEY (condition_id) REFERENCES comparison_condition (id)
);

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

INSERT INTO filter (name, created_at)
VALUES ('Budget Filter', '2024-01-10'),
       ('Sales Filter', '2024-01-10');

INSERT INTO filter_criteria (filter_id, criteria_type_id, condition_id, criteria_value)
VALUES ((SELECT id FROM filter WHERE name = 'Budget Filter'),
        (SELECT id FROM criteria_type WHERE type_name = 'Amount'),
        (SELECT id FROM comparison_condition WHERE name = 'Less'),
        '1500'),
       ((SELECT id FROM filter WHERE name = 'Budget Filter'),
        (SELECT id FROM criteria_type WHERE type_name = 'Date'),
        (SELECT id FROM comparison_condition WHERE name = 'From'),
        '2024-01-01'),

       ((SELECT id FROM filter WHERE name = 'Sales Filter'),
        (SELECT id FROM criteria_type WHERE type_name = 'Title'),
        (SELECT id FROM comparison_condition WHERE name = 'Contains'),
        'Q1 Report'),
       ((SELECT id FROM filter WHERE name = 'Sales Filter'),
        (SELECT id FROM criteria_type WHERE type_name = 'Amount'),
        (SELECT id FROM comparison_condition WHERE name = 'More'),
        '2000');
