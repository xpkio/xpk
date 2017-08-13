CREATE TABLE spot (
  id BIGSERIAL PRIMARY KEY,
  creator_id BIGINT REFERENCES xpk_user (id),
  name text
);

CREATE TABLE post (
  id BIGSERIAL PRIMARY KEY,
  body text,
  author_id BIGINT REFERENCES xpk_user (id),
  create_date TIMESTAMP
);

CREATE TABLE spot_post (
  spot_id BIGINT REFERENCES spot (id),
  post_id BIGINT REFERENCES post (id)
);

CREATE TABLE registration (
  id BIGSERIAL PRIMARY KEY,
  spot_id BIGINT REFERENCES spot (id),
  xpk_user_id BIGINT REFERENCES xpk_user (id),
  poster BOOLEAN DEFAULT FALSE NOT NULL,
  viewer BOOLEAN DEFAULT FALSE NOT NULL
);
