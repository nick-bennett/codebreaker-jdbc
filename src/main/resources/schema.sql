CREATE TABLE code (
    code_id   CHAR(16) FOR BIT DATA NOT NULL,
    created   TIMESTAMP             NOT NULL,
    length    INTEGER CHECK (length <= 20 AND length >= 1),
    pool      VARCHAR(20)           NOT NULL,
    code_text VARCHAR(20)           NOT NULL,
    PRIMARY KEY (code_id)
);

CREATE TABLE guess (
    guess_id      CHAR(16) FOR BIT DATA NOT NULL,
    created       TIMESTAMP             NOT NULL,
    exact_matches INTEGER               NOT NULL,
    near_matches  INTEGER               NOT NULL,
    guess_text    VARCHAR(20)           NOT NULL,
    code_id       CHAR(16) FOR BIT DATA NOT NULL,
    PRIMARY KEY (guess_id)
);

CREATE INDEX IX_code_created ON code (created);

CREATE INDEX IX_guess_created ON guess (created);

ALTER TABLE guess
    ADD CONSTRAINT FK_guess_code_id FOREIGN KEY (code_id) REFERENCES code;