CREATE TABLE "User"
(
  Id               BIGSERIAL PRIMARY KEY,
  Username         TEXT NOT NULL,
  FirstName        TEXT,
  LastName         TEXT,
  Password         TEXT,
  Salt             TEXT
)
