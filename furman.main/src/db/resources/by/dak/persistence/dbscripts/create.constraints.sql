-- FURNITURE_CODE
alter table FURNITURE_CODE add constraint FURNITURE_CODE_UNIQ unique(CODE, NAME, MANUFACTURER_ID, SURFACE, PRICED_TYPE, OFFSET_LENGTH, OFFSET_WIDTH);

-- ORDER_DETAIL
alter table ORDER_DETAIL add constraint FURNITURE_FURNITURE_LINK foreign key (FURNITURE_ID) references FURNITURE;
alter table ORDER_DETAIL add constraint COMPLEX_DEF_FURN_TYPE foreign key (COMLEX_BOARD_DEF_ID) references FURNITURE_TYPE;
delete from ORDER_DETAIL where ID in ( select OD.ID FROM ORDER_DETAIL OD LEFT JOIN
FURNITURE_TYPE FT ON FT.ID = OD.SECOND_BOARD_DEF_ID
WHERE
FT.ID is NULL
and
OD.SECOND_BOARD_DEF_ID is NOT NULL);
alter table ORDER_DETAIL add constraint SECOND_DEF_FURN_TYPE foreign key (SECOND_BOARD_DEF_ID) references FURNITURE_TYPE;
alter table ORDER_DETAIL add constraint ORDER_DETAIL_ORDER_ITEM foreign key (ORDER_ITEM_ID) references ORDER_ITEM;
alter table ORDER_DETAIL add constraint ORDER_DETAIL_FURNITURE_TYPE foreign key (FURNITURE_TYPE_ID) references FURNITURE_TYPE;
alter table ORDER_DETAIL add constraint ORDER_DETAIL_FURNITURE_CODE foreign key (FURNITURE_CODE_ID) references FURNITURE_CODE;

-- BORDER
alter table BORDER add constraint BORDER_FURNITURE_TYPE foreign key (BORDER_DEF_ID) references FURNITURE_TYPE;
alter table BORDER add constraint BORDER_FURNITURE_CODE foreign key (TEXTURE_ID) references FURNITURE_CODE;
alter table BORDER add constraint BORDER_CREATED_ORDER foreign key (CREATEDBY_ORDER_ID) references FURN_ORDER;
alter table BORDER add constraint PROVIDER_BORDER foreign key (PROVIDER_ID) references PROVIDER;
alter table BORDER add constraint ORDER_BORDER foreign key (ORDER_ID) references FURN_ORDER;

--PRICE
alter table PRICE add constraint PRICE_FURNITURE_CODE foreign key (PRICED_ID) references FURNITURE_CODE;
alter table PRICE add constraint PRICE_FURNITURE_TYPE foreign key (PRICEAWARE_ID) references FURNITURE_TYPE;


--FURNITURE_TYPE_LINK
alter table FURNITURE_TYPE_LINK add constraint PLINK_FURNITURE_TYPE foreign key (PARENT_ID) references FURNITURE_TYPE (ID) on delete cascade;
alter table FURNITURE_TYPE_LINK add constraint CLINK_FURNITURE_TYPE foreign key (CHILD_ID) references FURNITURE_TYPE (ID) on delete cascade;

--FURNITURE_CODE_LINK
alter table FURNITURE_CODE_LINK add constraint PLINK_FURNITURE_CODE foreign key (PARENT_ID) references FURNITURE_CODE (ID) on delete cascade;
alter table FURNITURE_CODE_LINK add constraint CLINK_FURNITURE_CODE foreign key (CHILD_ID) references FURNITURE_CODE (ID) on delete cascade;

--FURNITURE_TYPE
alter table FURNITURE_TYPE add constraint BOARD_SIMPLE_TYPE_1 foreign key (SIMPLE_ID_1) references FURNITURE_TYPE;
alter table FURNITURE_TYPE add constraint BOARD_SIMPLE_TYPE_2 foreign key (SIMPLE_ID_2) references FURNITURE_TYPE;

--STRIPS
alter table STRIPS add constraint STRIPS_ORDER foreign key (ORDER_ID) references FURN_ORDER (ID) on delete cascade;
alter table STRIPS add constraint STRIPS_ORDER_GROUP foreign key (ORDER_GROUP_ID) references ORDER_GROUP(ID) on delete cascade;

--COMMON_DATA
alter table COMMON_DATA add constraint COMMON_DATA_ORDER foreign key (ORDER_ID) references FURN_ORDER (ID) on delete cascade;

--ORDER_GROUP_LINK
alter table ORDER_GROUP_LINK add constraint ORDER_ORDER_GROUP_LINK foreign key (ORDER_ID) references FURN_ORDER;
alter table ORDER_GROUP_LINK add constraint GROUP_ORDER_GROUP_LINK foreign key (GROUP_ID) references ORDER_GROUP;

--ORDER_GROUP
alter table ORDER_GROUP add constraint ORDER_GROUP_DAILYSHEED foreign key (DAILYSHEET_ID) references DAILY_SHEET;

--DAILY_SHEET
alter table DAILY_SHEET add constraint DS_EMPL foreign key (FK_EMPLOYEE_ID) references EMPLOYEE;

--DAILY_SHEET_SHIFT
alter table DAILY_SHEET_SHIFT add constraint SHIFT_DS_SHIFT foreign key (FK_SHIFT_ID) references SHIFT on delete Cascade;
alter table DAILY_SHEET_SHIFT add constraint DS_DS_SHIFT foreign key (FK_DAILY_SHEET_ID) references DAILY_SHEET on delete Cascade;
