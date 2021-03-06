create table STORAGE_ELEMENT_LINK (ID bigint generated by default as identity,
AMOUNT bigint not null,
ORDER_ID bigint not null,
FURNITURE_ID bigint not null,
constraint PK_STORAGE_ELEMENT_LINK primary key (ID)
);

alter table STORAGE_ELEMENT_LINK add constraint BOARD_LINK_ORDER foreign key (ORDER_ID) references FURN_ORDER (ID) on delete cascade;
alter table STORAGE_ELEMENT_LINK add constraint STORAGE_FURN_CONSTR foreign key (FURNITURE_ID) references FURNITURE (ID) on delete cascade;

