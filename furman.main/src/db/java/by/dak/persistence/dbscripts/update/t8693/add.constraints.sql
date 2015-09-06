alter table STRIPS add constraint STRIPS_ORDER foreign key (ORDER_ID) references FURN_ORDER (ID) on delete cascade;
alter table COMMON_DATA add constraint COMMON_DATA_ORDER foreign key (ORDER_ID) references FURN_ORDER (ID) on delete cascade;
