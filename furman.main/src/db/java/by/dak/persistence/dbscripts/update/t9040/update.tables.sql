alter table STRIPS add DISCRIMINATOR varchar(255) default 'StripsEntity' not null;
--update STRIPS set DISCRIMINATOR = 'StripsEntity';
alter table STRIPS alter column ORDER_ID set default null;
alter table STRIPS add ORDER_GROUP_ID bigint default null;
alter table STRIPS add constraint STRIPS_ORDER_GROUP foreign key (ORDER_GROUP_ID) references ORDER_GROUP(ID) on delete cascade;
ALTER TABLE STRIPS ALTER COLUMN ORDER_ID SET NULL;