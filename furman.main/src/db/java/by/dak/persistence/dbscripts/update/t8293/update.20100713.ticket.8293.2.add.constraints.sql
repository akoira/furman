-- PRIMARY KEYS should be first
ALTER TABLE ORDER_DETAIL ADD CONSTRAINT PK_ORDER_DETAIL PRIMARY KEY(ID);
ALTER TABLE ORDER_ITEM ADD CONSTRAINT PK_ORDER_ITEM PRIMARY KEY(ID);

-- FURNITURE_CODE
alter table FURNITURE_CODE add constraint FURNITURE_CODE_UNIQ unique(CODE, NAME, MANUFACTURER_ID, SURFACE, PRICED_TYPE);

-- ORDER_DETAIL
alter table ORDER_DETAIL add constraint FURNITURE_FURNITURE_LINK foreign key (FURNITURE_ID) references FURNITURE;
alter table ORDER_DETAIL add constraint COMPLEX_DEF_FURN_TYPE foreign key (COMLEX_BOARD_DEF_ID) references FURNITURE_TYPE;
alter table ORDER_DETAIL add constraint SECOND_BOARD foreign key (SECOND_BOARD_ID) references ORDER_DETAIL;
alter table ORDER_DETAIL add constraint ORDER_DETAIL_ORDER_ITEM foreign key (ORDER_ITEM_ID) references ORDER_ITEM;
alter table ORDER_DETAIL add constraint ORDER_DETAIL_FURNITURE_TYPE foreign key (FURNITURE_TYPE_ID) references FURNITURE_TYPE;
alter table ORDER_DETAIL add constraint ORDER_DETAIL_FURNITURE_CODE foreign key (FURNITURE_CODE_ID) references FURNITURE_CODE;


-- FURNITURE_TYPE
alter table FURNITURE_TYPE add constraint BOARD_SIMPLE_TYPE_1 foreign key (SIMPLE_ID_1) references FURNITURE_TYPE;
alter table FURNITURE_TYPE add constraint BOARD_SIMPLE_TYPE_2 foreign key (SIMPLE_ID_2) references FURNITURE_TYPE;

-- STRIPS
delete from STRIPS where ID in (select S.ID from STRIPS S left join ORDER_ITEM OI on s.ORDER_ITEM_ID = OI.ID where OI.ID is null and s.ORDER_ITEM_ID is not null);
alter table STRIPS add constraint STRIPS_ORDER_ITEM foreign key (ORDER_ITEM_ID) references ORDER_ITEM (ID) on delete cascade;
alter table STRIPS add constraint STRIPS_FURNITURE_TYPE foreign key (FURNITURE_TYPE_ID) references FURNITURE_TYPE;
alter table STRIPS add constraint STRIPS_FURNITURE_CODE foreign key (FURNITURE_CODE_ID) references FURNITURE_CODE;

--ORDER_ITEM
alter table ORDER_ITEM add constraint ORDER_ITEM_ORDER foreign key (ORDER_ID) references FURN_ORDER;

--FURNITURE
alter table FURNITURE add constraint FURNITURE_ORDER foreign key (ORDER_ID) references FURN_ORDER;
alter table FURNITURE add constraint FURNITURE_CREATED_ORDER foreign key (CREATEDBY_ORDER_ID) references FURN_ORDER;
alter table FURNITURE add constraint FURNITURE_PROVIDER foreign key (PROVIDER_ID) references PROVIDER;
alter table FURNITURE add constraint FURNITURE_FURNITURE_TYPE foreign key (FURNITURE_TYPE_ID) references FURNITURE_TYPE;
alter table FURNITURE add constraint FURNITURE_FURNITURE_CODE foreign key (FURNITURE_CODE_ID) references FURNITURE_CODE;
alter table FURNITURE add constraint FURNITURE_DELIVERY foreign key (DELIVERY_ID) references DELIVERY;

update FURNITURE_CODE set NAME = SERVICE_TYPE where PRICED_TYPE = 'Service';
