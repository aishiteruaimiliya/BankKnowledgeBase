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
drop table if exists knowledgetype;

#机构
create table department(
	dep_id int auto_increment primary key,
    fist varchar(20) not null,
    second varchar(20),
    third varchar(20),
    fourth varchar(20)
);
#知识类型
create table knowledgetype(
	typeid int auto_increment primary key,
    typecontent varchar(20) not null
);
#系统管理员
create table systemManager(
	systemManager_id int  auto_increment primary key,
    name varchar(20),
    password varchar(40)
);
#普通用户
create table normalUser(
	normal_id int  auto_increment primary key,
    dep_id int,
    name varchar(20),
    password varchar(40),
    foreign key (dep_id) references department(dep_id)
);
create table expectUser(
	expect_id int auto_increment primary key,
    dep_id int,
    name varchar(20),
    password varchar(40),
    foreign key (dep_id) references department(dep_id)
);
create table knowledgeManager(
	knowledgeManager_id int auto_increment primary key,
    dep_id int,
    name varchar(20),
    password varchar(40),
    foreign key (dep_id) references department(dep_id)
);
create table knowledge(
	know_id int auto_increment primary key,
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
    foreign key(judge_id) references expectUser(expect_id)
);
create table favorite(
	normal_user_id int ,
    know_id int,
    primary key(normal_user_id,know_id),
    foreign key(normal_user_id) references normalUser(normal_id),
    foreign key(know_id) references knowledge(know_id)
);
create table user_click_knowledgetype(
	normal_user_id int,
    typeid int ,
    times int default 0,
    primary key(normal_user_id,typeid),
    foreign key (typeid) references knowledgetype(typeid),
    foreign key (normal_user_id) references normalUser(normal_id)
);
create table comment(
	comment_id int auto_increment primary key,
	know_id int,
    normal_user_id int,
    foreign key (normal_user_id) references normalUser(normal_id),
    foreign key(know_id) references knowledge(know_id)
);

