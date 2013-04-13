create table beer (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  brewery_id                integer)
;

create table brewery (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255))
;

create table rating (
  id                        integer primary key AUTOINCREMENT,
  beer_id                   integer,
  user_id                   integer,
  value                     integer)
;

create table user (
  id                        integer primary key AUTOINCREMENT,
  kayttajatunnus            varchar(255))
;

create index ix_beer_brewery_1 on beer (brewery_id);
create index ix_rating_beer_2 on rating (beer_id);
create index ix_rating_user_3 on rating (user_id);


