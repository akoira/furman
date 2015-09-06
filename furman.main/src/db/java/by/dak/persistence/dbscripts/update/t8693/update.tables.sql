alter table STRIPS add PAIDVALUE double default 0.0 not null;
alter table STRIPS add STRIPS VARCHAR(2147483647) default '' not null;
alter table STRIPS add ORDER_ID bigint default -1 not null;

update STRIPS s set s.ORDER_ID = (
select oi.ORDER_ID from STRIPS s1
inner join ORDER_ITEM oi on s1.ORDER_ITEM_ID = oi.ID
where
s1.ID = s.ID
);
alter table STRIPS drop ORDER_ITEM_ID;

