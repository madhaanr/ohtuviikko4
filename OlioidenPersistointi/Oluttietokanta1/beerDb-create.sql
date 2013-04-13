create table beer (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  brewery_id                integer)
;

create table brewery (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255))
;

create table user (
  id                        integer primary key AUTOINCREMENT,
  kayttajatunnus            varchar(255))
;

create index ix_beer_brewery_1 on beer (brewery_id);


