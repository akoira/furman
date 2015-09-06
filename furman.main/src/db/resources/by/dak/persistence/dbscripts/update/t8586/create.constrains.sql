alter table FURN_LINK_FURN_LINK add constraint PFLFL_ORDER_DETAIL foreign key (PARENT_ID) references ORDER_DETAIL (ID) on delete cascade;
alter table FURN_LINK_FURN_LINK add constraint CFLFL_FURNITURE foreign key (CHILD_ID) references FURNITURE (ID) on delete cascade;
