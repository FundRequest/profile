CREATE TABLE github_bounty (
  id        BIGINT NOT NULL AUTO_INCREMENT,
  user_id   VARCHAR(100),
  github_id VARCHAR(100),
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_github_bounty_01
  ON github_bounty (user_id);