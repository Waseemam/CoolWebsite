CREATE TABLE courses (
    code        VARCHAR PRIMARY KEY NOT NULL,
    title       VARCHAR(75) NOT NULL,
    credits     VARCHAR(128) NOT NULL,
    complete    BOOLEAN DEFAULT FALSE,
    term        INT,
    finalGrade  DECIMAL(5,2)
);

CREATE TABLE evaluations (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    course      VARCHAR(9) NOT NULL,
    grade       DECIMAL(5,2),
    max         DECIMAL(5,2),
    weight      DECIMAL(5,2),
    duedate     DATE
);