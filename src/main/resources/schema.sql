CREATE TABLE  IF NOT EXISTS orders  (
  id bigint primary key auto_increment not null ,
  fn varchar(255) not null
);
truncate orders ;