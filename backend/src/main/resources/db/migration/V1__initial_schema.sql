CREATE TABLE filters (
    filter_id INT AUTO_INCREMENT PRIMARY KEY,
    filter_name VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL
);

CREATE TABLE criteria_types (
    criteria_type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL,
    data_type VARCHAR(255) NOT NULL
);

CREATE TABLE comparison_conditions (
    condition_id INT AUTO_INCREMENT PRIMARY KEY,
    criteria_type_id INT NOT NULL,
    condition_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (criteria_type_id) REFERENCES criteria_types(criteria_type_id)
);

CREATE TABLE filter_criteria (
    criteria_id INT AUTO_INCREMENT PRIMARY KEY,
    filter_id INT,
    criteria_type_id INT,
    condition_id INT,
    criteria_value VARCHAR(255) NOT NULL,
    FOREIGN KEY (filter_id) REFERENCES filters(filter_id),
    FOREIGN KEY (criteria_type_id) REFERENCES criteria_types(criteria_type_id),
    FOREIGN KEY (condition_id) REFERENCES comparison_conditions(condition_id)
);

INSERT INTO criteria_types (type_name, data_type) VALUES 
('Amount', 'NUMBER'), 
('Title', 'STRING'), 
('Date', 'DATE');

INSERT INTO comparison_conditions (criteria_type_id, condition_name) VALUES

((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Amount'), 'More'),
((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Amount'), 'Less'),
((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Amount'), 'Equal'),

((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Title'), 'Contains'),
((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Title'), 'Starts with'),
((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Title'), 'Ends with'),
((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Title'), 'Equals'),

((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Date'), 'To'),
((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Date'), 'From'),
((SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Date'), 'Equal');

INSERT INTO filters (filter_name, created_at) VALUES 
('Budget Filter', '2024-01-10'), 
('Sales Filter', '2024-01-10');

INSERT INTO filter_criteria (filter_id, criteria_type_id, condition_id, criteria_value) VALUES

((SELECT filter_id FROM filters WHERE filter_name = 'Budget Filter'), 
    (SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Amount'), 
    (SELECT condition_id FROM comparison_conditions WHERE condition_name = 'Less'), 
    '1500'),
((SELECT filter_id FROM filters WHERE filter_name = 'Budget Filter'), 
    (SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Date'), 
    (SELECT condition_id FROM comparison_conditions WHERE condition_name = 'From'), 
    '2024-01-01'),
    
((SELECT filter_id FROM filters WHERE filter_name = 'Sales Filter'), 
    (SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Title'), 
    (SELECT condition_id FROM comparison_conditions WHERE condition_name = 'Contains'), 
    'Q1 Report'),
((SELECT filter_id FROM filters WHERE filter_name = 'Sales Filter'), 
    (SELECT criteria_type_id FROM criteria_types WHERE type_name = 'Amount'), 
    (SELECT condition_id FROM comparison_conditions WHERE condition_name = 'More'), 
    '2000');
