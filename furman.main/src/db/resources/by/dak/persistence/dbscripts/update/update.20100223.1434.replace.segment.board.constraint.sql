alter table SEGMENT drop constraint BOARD_SEGMENT;
alter table SEGMENT add constraint BOARD_SEGMENT foreign key (BOARD_ID) references BOARD;
