CREATE TABLE xpk_user
(
  id BIGSERIAL PRIMARY KEY,
  username TEXT UNIQUE NOT NULL,
  first_name TEXT,
  last_name TEXT,
  email TEXT,
  password TEXT
);
