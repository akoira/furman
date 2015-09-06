alter table PRICE drop constraint PRICE_FURNITURE_CODE;
alter table PRICE drop constraint PRICE_FURNITURE_TYPE;

-- В прайсе не может быть null полей
update PRICE set PRICE_FAKTOR = 0 where PRICE_FAKTOR is null;
ALTER TABLE PRICE ALTER PRICEAWARE_ID bigint not null;
ALTER TABLE PRICE ALTER PRICED_ID bigint not null;
ALTER TABLE PRICE ALTER PRICE DOUBLE not null;
ALTER TABLE PRICE ALTER PRICE_DEALER DOUBLE not null;
ALTER TABLE PRICE ALTER PRICE_FAKTOR DOUBLE not null;

alter table PRICE add constraint PRICE_FURNITURE_CODE foreign key (PRICED_ID) references FURNITURE_CODE;
alter table PRICE add constraint PRICE_FURNITURE_TYPE foreign key (PRICEAWARE_ID) references FURNITURE_TYPE;

-- Добавляем рамер по умолчанию в FurnitureType
alter table FURNITURE_TYPE add DEFAULT_SIZE double DEFAULT 1 not null;

-- size перегоняем в amount для piece
update FURNITURE f  set f.AMOUNT = f.SIZE, f.SIZE = 1
where f.ID in (
select  f.ID from FURNITURE f
inner join FURNITURE_TYPE ft on f.FURNITURE_TYPE_ID = ft.ID
where f.DISCRIMINATOR =  'Furniture' and ft.UNIT = 'piece');
