CREATE DATABASE getirdb;
CREATE USER myuser with password 'mypass';
\c getirdb;
CREATE SCHEMA dbo;

drop table if exists dbo.book cascade;
CREATE TABLE dbo.book(
  id serial PRIMARY KEY,
  name varchar(255),
  author varchar(255),
  price decimal
);

drop table if exists dbo.book_stock cascade;
CREATE TABLE dbo.book_stock(
  id serial PRIMARY KEY,
  book_id int not null UNIQUE,
  quantity int not null CHECK(quantity >= 0),
  FOREIGN KEY (book_id) REFERENCES dbo.book (id)
);

drop table if exists dbo.customer cascade;
CREATE TABLE dbo.customer(
  id serial PRIMARY KEY,
  name varchar(255),
  surname varchar(255),
  age int,
  email varchar(255)
);

drop table if exists dbo.order cascade;
CREATE TABLE dbo.order(
  id serial PRIMARY KEY,
  customer_id int not null,
  price decimal,
  created_date timestamp,
  FOREIGN KEY (customer_id) REFERENCES dbo.customer (id)
);

drop table if exists dbo.order_detail cascade;
CREATE TABLE dbo.order_detail(
  id serial PRIMARY KEY,
  book_id int not null,
  order_id int,
  quantity int not null,
  price decimal,
  order_status varchar(255),
  FOREIGN KEY (book_id) REFERENCES dbo.book (id),
  FOREIGN KEY (order_id) REFERENCES dbo.order (id)
);

GRANT USAGE ON SCHEMA dbo TO myuser;
GRANT ALL ON ALL TABLES IN SCHEMA dbo TO myuser;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA dbo TO myuser;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA dbo TO myuser;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO myuser;
