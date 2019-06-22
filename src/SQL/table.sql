#dll建表语句
drop table if exists user_click_knowledgetype;
drop table if exists systemManager;
drop table if exists comment;
drop table if exists knowledgeManager;
drop table if exists favorite;
drop table if exists knowledge;
drop table if exists normalUser;
drop table if exists expectUser;
drop table  if exists department;
drop table if exists knowledgeType;

#机构
create table department(
	depId int auto_increment primary key,
    first varchar(20) not null,
    second varchar(20),
    third varchar(20),
    fourth varchar(20),
    disable tinyint(1) default false
);
#知识类型
create table knowledgeType(
	typeId int auto_increment primary key,
    typeContent varchar(20) not null,
    nextTypeId int,
    disable tinyint(1) default false
);
#系统管理员
create table systemManager(
	systemManager_id int  auto_increment primary key,
	account varchar(20) unique ,
    name varchar(20),
    password varchar(40),
    depId int,
    disable tinyint(1) default false
);
#普通用户
create table normalUser(
	normal_id int  auto_increment primary key,
    depId int,
    account varchar(20) unique ,
    name varchar(20),
    password varchar(40),
    foreign key (depId) references department(depId),
    disable tinyint(1) default false
);
create table expectUser(
	expect_id int auto_increment primary key,
    depId int,
    account varchar(20) unique ,
    name varchar(20),
    password varchar(40),
    foreign key (depId) references department(depId),
    disable tinyint(1) default false
);
create table knowledgeManager(
	knowledgeManager_id int auto_increment primary key,
    depId int,
    account varchar(20) unique ,
    name varchar(20),
    password varchar(40),
    foreign key (depId) references department(depId),
    disable tinyint(1) default false
);
create table knowledge(
	knowId int auto_increment primary key,
    title varchar(50) not null,
    detail text not null,
    typeid int,
    digest varchar(20),
    from_user_id int not null,
    judge_id int not null,
    clicked int default 0,
    status varchar(20) not null,
	foreign key (typeid) references knowledgetype(typeid),
    foreign key (from_user_id) references normalUser(normal_id),
    foreign key(judge_id) references expectUser(expect_id),
    disable tinyint(1) default false
);
create table favorite(
	normal_user_id int ,
    knowId int,
    primary key(normal_user_id,knowId),
    foreign key(normal_user_id) references normalUser(normal_id),
    foreign key(knowId) references knowledge(knowId),
    disable tinyint(1) default false
);
create table user_click_knowledgetype(
	normal_user_id int,
    typeid int ,
    times int default 0,
    primary key(normal_user_id,typeid),
    foreign key (typeid) references knowledgetype(typeid),
    foreign key (normal_user_id) references normalUser(normal_id),
    disable tinyint(1) default false
);
create table comment(
	comment_id int auto_increment primary key,
	knowId int,
    normal_user_id int,
    star int ,
    content text,
    foreign key (normal_user_id) references normalUser(normal_id),
    foreign key(knowId) references knowledge(knowId),
    disable tinyint(1) default false
);
insert into department(first, second, third, fourth) values ('山东省','济南市','历下',null);
