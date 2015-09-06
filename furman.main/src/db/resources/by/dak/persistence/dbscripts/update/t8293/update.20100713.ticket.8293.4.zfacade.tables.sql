alter table FURNITURE_TYPE add OFFSET_LENGTH double;
alter table FURNITURE_TYPE add OFFSET_WIDTH double;

alter table FURNITURE_CODE add OFFSET_LENGTH double;
alter table FURNITURE_CODE add OFFSET_WIDTH double;

update FURNITURE_CODE set code = 0 where code is null;

alter table ORDER_ITEM ADD TYPE VARCHAR(255) DEFAULT 'first' not null ;

alter table ORDER_ITEM ADD NUMBER BIGINT DEFAULT 1 not null;
alter table ORDER_ITEM ADD AMOUNT BIGINT DEFAULT 1 not null;

alter table ORDER_ITEM ADD LENGTH Double;
alter table ORDER_ITEM ADD WIDTH Double;
alter table ORDER_ITEM add DISCRIMINATOR VARCHAR(255) DEFAULT 'OrderItem' not null;
