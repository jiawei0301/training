USE practice;

CREATE TABLE dept (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL
);

CREATE TABLE employee (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(100) NOT NULL,
                          dept_id INT,
                          FOREIGN KEY (dept_id) REFERENCES dept(id)
);

-- sample data insertion
INSERT INTO dept (name) VALUES ('Engineering'), ('Sales'), ('HR');

INSERT INTO employee (name, dept_id) VALUES
                                         ('Alice', 1),
                                         ('Bob', 1),
                                         ('Carol', 2),
                                         ('David', 3),
                                         ('Eve', 1);

-- return dept name and headcount
SELECT
    d.name AS dept_name,
    COUNT(e.id) AS headcount
FROM
    dept d
        LEFT JOIN
    employee e ON d.id = e.dept_id
GROUP BY
    d.id, d.name;
