--rename   FEEDSTOCK - BOARD
drop table FEEDSTOCK CASCADE;
create table BOARD (ID bigint generated by default as identity,
AMOUNT bigint not null,
LENGTH bigint not null,
WIDTH bigint not null,
BOARD_DEF_ID bigint not null,
TEXTURE_ID bigint not null,
ORDER_ID bigint null,
CREATEDBY_ORDER_ID bigint null,
STATUS VARCHAR(255) DEFAULT 'order' not null,
constraint PK_BOARD primary key (ID));

alter table BOARD add constraint BOARD_DEF_BOARD foreign key (BOARD_DEF_ID) references FURNITURE_DEF;
alter table BOARD add constraint TEXTURE_BOARD foreign key (TEXTURE_ID) references TEXTURE;
--delete segments if board deleted

--alter table BOARD add constraint BOARD_UNIQUE unique (TEXTURE_ID, BOARD_DEF_ID, ORDER_ID, LENGTH, WIDTH);

-- add column status to ORDER
alter table FURN_ORDER add column STATUS varchar(255) DEFAULT 'production' not null;

-- change order status on production
update FURN_ORDER set STATUS =  'production';

--alter table SEGMENT for link to BOARD
alter table SEGMENT add column BOARD_ID bigint;
alter table SEGMENT add constraint BOARD_SEGMENT foreign key (BOARD_ID) references BOARD (ID) on delete cascade;
