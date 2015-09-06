--ADDITIONAL
alter table ADDITIONAL drop constraint ADDITIONAL_ORDER;

-- FURNITURE_CODE
alter table FURNITURE_CODE drop constraint FURNITURE_CODE_UNIQ;

-- FURNITURE_LINK
alter table FURNITURE_LINK drop constraint FURNITURE_CODE_FURNITURE_LINK;
alter table FURNITURE_LINK drop constraint FURNITURE_TYPE_FURNITURE_LINK;
alter table FURNITURE_LINK drop constraint FURN_ORDER_FURNITURE_LINK;
alter table FURNITURE_LINK drop constraint FURNITURE_FURNITURE_LINK;


-- ORDER_FURNITURE
alter table ORDER_FURNITURE drop constraint FURN_DEF_ORDER_ITEM;
alter table ORDER_FURNITURE drop constraint FURN_DEF_COMLEX_ORDER_ITEM;
alter table ORDER_FURNITURE drop constraint TEXTURE_ORDER_FURN;
alter table ORDER_FURNITURE drop constraint ORDER_FURN_ORDER_ITEM;
ALTER TABLE ORDER_FURNITURE DROP CONSTRAINT PK_ORDER_FURN;

--BOARD_DEF
alter table BOARD_DEF drop constraint BOARD_SIMPLE_TYPE_1;
alter table BOARD_DEF drop constraint BOARD_SIMPLE_TYPE_2;

-- FURN_ORDER_ITEM
ALTER TABLE FURN_ORDER_ITEM DROP CONSTRAINT PK_FURN_ORDER_ITEM;
alter table FURN_ORDER_ITEM drop constraint ORDER_ITEM_FURN_ORDER;

--FURNITURE
alter table FURNITURE drop constraint DELIVERY_FURNITURE;
alter table FURNITURE drop constraint PROVIDER_FURNITURE;
alter table FURNITURE drop constraint FURNITURE_TYPE_FURNITURE;
alter table FURNITURE drop constraint FURNITURE_CODE_FURNITURE;
alter table FURNITURE drop constraint ORDER_FURNITURE;
alter table FURNITURE drop constraint CREATEDBY_ORDER_FURNITURE;

--BOARD
alter table BOARD drop constraint BOARD_DEF_BOARD;
alter table BOARD drop constraint TEXTURE_BOARD;
--alter table BOARD drop constraint BOARD_PROVIDER;
alter table BOARD drop constraint DELIVERY_BOARD;
alter table BOARD drop constraint ORDER_BOARD;
--alter table BOARD drop constraint BOARD_CREATED_ORDER;

-- BORDER
alter table BORDER drop constraint ORDER_BORDER;
--alter table BORDER drop constraint BORDER_CREATED_ORDER;
alter table BORDER drop constraint BORDER_DEF_BORDER;
alter table BORDER drop constraint TEXTURE_BORDER;
--alter table BORDER drop constraint PROVIDER_BORDER;
alter table BORDER drop constraint DELIVERY_BORDER;