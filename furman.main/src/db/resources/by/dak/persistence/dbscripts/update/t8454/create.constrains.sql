alter table FURNITURE_CODE_LINK add constraint PLINK_FURNITURE_CODE foreign key (PARENT_ID) references FURNITURE_CODE (ID) on delete cascade;
alter table FURNITURE_CODE_LINK add constraint CLINK_FURNITURE_CODE foreign key (CHILD_ID) references FURNITURE_CODE (ID) on delete cascade;
