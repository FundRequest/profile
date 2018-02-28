CREATE TABLE bounties (
  id                      BIGINT(20)  NOT NULL AUTO_INCREMENT PRIMARY KEY,
  start_date              DATETIME    NOT NULL DEFAULT now(),
  end_date                DATETIME             DEFAULT NULL,
  type                    VARCHAR(10) NOT NULL,
  twitter_type            VARCHAR(20)          DEFAULT NULL,
  twittter_follow_account VARCHAR(100)         DEFAULT NULL,
  twitter_required_posts  INT                  DEFAULT 1
);