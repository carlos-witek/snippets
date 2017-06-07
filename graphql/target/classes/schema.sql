DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id                         BIGINT NOT NULL,
  user_profile_id            BIGINT NOT NULL,
  username                   VARCHAR(20) DEFAULT NULL,
  email                      VARCHAR(20) DEFAULT NULL,
  is_admin                   BOOLEAN     DEFAULT FALSE,
  image                      BIGINT      DEFAULT NULL,
  pp_credit_account_id       BIGINT      DEFAULT NULL,
  pp_credit_bonus_account_id BIGINT      DEFAULT NULL,
  pp_credit_fdp_account_id   BIGINT      DEFAULT NULL,
  status                     TINYINT     DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_profile;

CREATE TABLE user_profile (
  id                        BIGINT NOT NULL,
  firstname                 VARCHAR(20) DEFAULT NULL,
  lastname                  VARCHAR(20) DEFAULT NULL,
  has_confirmed_legal_state TINYINT     DEFAULT NULL,
  PRIMARY KEY (id)
);