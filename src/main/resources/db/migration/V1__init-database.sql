drop table if exists book;
drop table if exists book_seq;
drop table if exists author;
drop table if exists author_seq;
create table book (
                      id bigint not null auto_increment,
                      isbn varchar(255),
                      publisher varchar(255),
                      title varchar(255),
                      primary key (id)
) engine=InnoDB;

create table book_seq (
    next_val bigint
) engine=InnoDB;

insert into book_seq values(1);

create table author (
                        id int auto_increment primary key,
                        first_name varchar(255) not null,
                        last_name varchar(255) not null
) engine=InnoDB;

create  table author_seq (
    next_val bigint
)engine=InnoDB;
insert into author_seq values (1);

