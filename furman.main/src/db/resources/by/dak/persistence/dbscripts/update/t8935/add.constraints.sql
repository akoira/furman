alter table FURN_ORDER add constraint FURN_ORDER_CATEGORY foreign key (CATEGORY_ID) references CATEGORY;
