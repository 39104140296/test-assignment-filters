CREATE TABLE filters (
    filter_id INT AUTO_INCREMENT PRIMARY KEY,
    filter_name VARCHAR(255) NOT NULL,
    created_at DATE DEFAULT CURRENT_DATE
);

CREATE TABLE criteria_types (
    criteria_type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL,
    data_type VARCHAR(255) NOT NULL
);

CREATE TABLE comparison_conditions (
    condition_id INT AUTO_INCREMENT PRIMARY KEY,
    criteria_type_id INT,
    condition_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (criteria_type_id) REFERENCES criteria_types(criteria_type_id)
);

CREATE TABLE filter_criteria (
    criteria_id INT AUTO_INCREMENT PRIMARY KEY,
    filter_id INT,
    criteria_type_id INT,
    condition_id INT,
    "value" VARCHAR(255)
);

ALTER TABLE filter_criteria
ADD CONSTRAINT fk_filter_criteria_filters
FOREIGN KEY (filter_id) REFERENCES filters(filter_id);

ALTER TABLE filter_criteria
ADD CONSTRAINT fk_filter_criteria_criteria_types
FOREIGN KEY (criteria_type_id) REFERENCES criteria_types(criteria_type_id);

ALTER TABLE filter_criteria
ADD CONSTRAINT fk_filter_criteria_comparison_conditions
FOREIGN KEY (condition_id) REFERENCES comparison_conditions(condition_id);

-- Population of test data
INSERT INTO filters (filter_name, created_at) VALUES ('Budget Filter', '2023-04-01'), ('Sales Filter', '2023-04-02');
INSERT INTO criteria_types (type_name, data_type) VALUES ('Amount', 'NUMBER'), ('Title', 'STRING'), ('Date', 'DATE');
INSERT INTO comparison_conditions (criteria_type_id, condition_name) VALUES (1, 'More'), (1, 'Less'), (1, 'Equal');
INSERT INTO filter_criteria (filter_id, criteria_type_id, condition_id, "value") VALUES (1, 1, 1, '1000'), (1, 1, 2, '5000'), (2, 1, 2, '8000');
