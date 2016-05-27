psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER william_owner WITH PASSWORD 'william_owner';
    CREATE DATABASE william;
    \connect william
    GRANT ALL PRIVILEGES ON DATABASE william TO william_owner;
    CREATE TABLE ota_equipment(ota_uuid uuid primary key, ota text, description text);
    CREATE TABLE actions(action_uuid uuid primary key, ota text, plo text, date date);
EOSQL
