drop table if exists navigation;
drop table if exists customer;

create table customer (
  id bigint(20) not null auto_increment,
  version int(11) not null default 0,
  first_name varchar(128) not null,
  last_name varchar(128) not null,
  birthday date not null,
  gender char(1) not null,
  last_contact timestamp not null,
  lifetime_value int(11) not null,
  primary key (id)
) engine=InnoDB default charset=utf8;

create table navigation (
  id bigint(20) not null auto_increment,
  version int(11) not null default 0,
  customer_id bigint(20) not null,
  page char(1) not null,
  stamp timestamp not null,
  primary key (id),
  constraint navigation_customer foreign key (customer_id) references customer (id)
) engine=InnoDB default charset=utf8;

insert into customer
  (first_name, last_name, birthday, gender, last_contact, lifetime_value)
  values
  ("Peter", "Smith", "1996-10-12", "m", "2013-06-01", 19112),
  ("Anna", "Hopp", "1987-05-03", "w", "2013-07-08", 5099),
  ("Christian", "Cox", "1991-02-21", "m", "2013-08-01", 0),
  ("Roxy", "Fox", "1979-06-30", "w", "2012-01-29", 21312),
  ("Eric", "Adam", "1969-11-21", "m", "2013-03-18", 101991);

insert into navigation
  (customer_id, page, stamp)
  values
  (1, "A", "2013-06-01 10:10:12"),
  (1, "B", "2013-06-01 10:11:12"),
  (1, "A", "2013-06-01 10:12:12"),
  (2, "C", "2013-07-08 09:03:09"),
  (2, "A", "2013-07-08 09:09:09"),
  (2, "D", "2013-07-08 09:19:09"),
  (3, "B", "2013-07-08 09:19:09"),
  (3, "A", "2013-07-08 09:19:10"),
  (4, "D", "2013-07-08 09:19:11"),
  (4, "A", "2013-07-08 09:19:12"),
  (5, "X", "2013-07-08 09:19:13"),
  (5, "A", "2013-07-08 09:19:14"),
  (5, "B", "2013-07-08 09:19:15");

