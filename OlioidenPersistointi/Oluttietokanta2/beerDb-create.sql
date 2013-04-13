create table beer (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  brewery_id                integer)
;

create table brewery (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255))
;

create table pub (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255))
;

create table user (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255))
;


create table pub_beer (
  pub_id                         integer not null,
  beer_id                        integer not null,
  constraint pk_pub_beer primary key (pub_id, beer_id))
;
create index ix_beer_brewery_1 on beer (brewery_id);




