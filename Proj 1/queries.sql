/*  SQL Project 1 
	Amit Nijjar
	A11489111
*/

--.read sqlinput1.sql

/* Query 1: */
select sid, avg(grade) 
from record where year = 2015 and 
qtr = 'F' or 
year = 2016 and 
qtr = 'W' or 
year = 2016 and 
qtr = 'S' 
group by sid;

-- Query 2:

select distinct a.sid, b.sid 
from record a, record b 
where a.year = 2016 and 
a.qtr = 'F' and 
b.year = 2016 and 
b.qtr = 'F' and 
a.cid = b.cid and 
a.sid < b.sid;

-- Query 3:

select r.qtr, r.year, count(distinct r.cid) as num 
from record r 
where r.cid in 
(select rr.cid 
from record rr 
where r.year = rr.year 
group by rr.cid, rr.qtr, rr.year 
having count(rr.sid) < 5) 
group by r.qtr, r.year;

 
-- Query 4:

select distinct r.sid, count(r.cid) as num 
from record r 
where r.year = 2016 and r.qtr = 'F' 
group by sid union select rr.sid, 0 
from record rr 
where rr.sid not in 
(select distinct rrr.sid 
from record rrr 
where rrr.year = 2016 and 
rrr.qtr = 'F' 
group by sid);


--Query 5:

-- Not in:
select distinct sid 
from record 
where sid not in 
(select distinct rec.sid from record rec where rec.sid not in (select r.sid from record r, record re where r.sid = re.sid and re.cid = (select precid from prerequisite where cid = 'CSE132X' limit 1) and r.cid = (select precid from prerequisite where cid = 'CSE132X' limit 1 offset 1) and r.grade >= 2 and re.grade >= 2));

--Not exists
select r.sid from record r, record re where r.sid = re.sid and re.cid = 'CSE107' and r.cid = 'CSE132B' and r.grade >= 2 and re.grade >= 2 and not exists(select red.sid from record red where cid = 'XXXXX');

--Query 6:

create view SortGrade as select sid, qtr, year, avg(grade) as gpa from record group by year, qtr, sid order by sid, year, qtr desc;
select distinct s.sid from SortGrade s join SortGrade g on s.sid = g.sid and s.gpa > g.gpa and ((s.year < g.year) or (s.year = g.year and s.qtr < g.qtr)) and s.sid not in (select distinct r.sid from SortGrade r join SortGrade t on r.sid = t.sid and r.gpa > t.gpa and (r.year < t.year) group by r.sid) group by s.sid;

--Query 7:

update record set cid = 'CSE132c' where cid = 'CSE132A' and year = 2016 and qtr = 'F';
update record set cid = 'CSE132A' where cid = 'CSE132B' and year = 2016 and qtr = 'F';
update record set cid = 'CSE132B' where cid = 'CSE132c' and year = 2016 and qtr = 'F';


