CREATE TABLE IF NOT EXISTS t_zookeeper_info (
  id                VARCHAR(60) PRIMARY KEY,
  description       VARCHAR(100),
  connection_string VARCHAR(128),
  session_timeout   INT
);