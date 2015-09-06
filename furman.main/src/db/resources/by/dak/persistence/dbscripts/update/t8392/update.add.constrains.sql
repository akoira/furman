alter table PRICE add constraint PRICE_FURNITURE_TYPE foreign key (PRICEAWARE_ID) references FURNITURE_TYPE;
delete from PRICE P1 where P1.ID in (
select P.ID from  PRICE P left join FURNITURE_TYPE FT on FT.ID = P.PRICEAWARE_ID
where
FT.ID is null);
alter table PRICE add constraint PRICE_FURNITURE_CODE foreign key (PRICED_ID) references FURNITURE_CODE;
