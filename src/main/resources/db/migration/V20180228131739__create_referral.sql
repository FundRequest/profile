CREATE TABLE referral (
  id       BIGINT NOT NULL AUTO_INCREMENT,
  referrer VARCHAR(100),
  referee  VARCHAR(100),
  PRIMARY KEY (id)
);