CREATE TABLE IF NOT EXISTS person
(
    id      serial CONSTRAINT id PRIMARY KEY,
    name    VARCHAR NOT NULL,
    age     INTEGER NULL,
    address VARCHAR NULL,
    work    VARCHAR NULL
);
