.mode columns
.headers on

create table student (sid);
create table course (cid);
create table prerequisite (cid, precid);
create table record (sid, cid, qtr, year, grade);

insert into student values ('A_001');
insert into student values ('A_002');
insert into student values ('A_003');

insert into course values ('CSE105');
insert into course values ('CSE107');
insert into course values ('CSE132A');
insert into course values ('CSE132B');
insert into course values ('CSE132X');

insert into prerequisite values ('CSE132B', 'CSE132A');

insert into record values ('A_001', 'CSE105', 'F', 2015, 3);
insert into record values ('A_001', 'CSE132A', 'F', 2015, 4);

insert into record values ('A_002', 'CSE132A', 'S', 2016, 1);
insert into record values ('A_003', 'CSE132A', 'S', 2016, 3);

insert into record values ('A_001', 'CSE132B', 'F', 2016, 1);
insert into record values ('A_002', 'CSE132B', 'F', 2016, 2);
insert into record values ('A_003', 'CSE132B', 'F', 2016, 2);
insert into record values ('A_002', 'CSE107', 'F', 2016, 3);
insert into record values ('A_003', 'CSE107', 'F', 2016, 4);
insert into record values ('A_003', 'CSE105', 'W', 2017, 3);
insert into record values ('A_001', 'CSE132X', 'S', 2017, 4);
insert into record values ('A_002', 'CSE132X', 'S', 2017, 4);
insert into record values ('A_003', 'CSE132X', 'S', 2017, 3);
