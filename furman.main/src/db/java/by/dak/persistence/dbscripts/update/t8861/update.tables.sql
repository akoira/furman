ALTER TABLE DAILY_SHEET ALTER DATE DATE;
delete from  furniture where id in (select  f.id from furniture f
inner join furniture_type ft on f.furniture_type_id = ft.id
where discriminator='Furniture' and size < 0.1 and ft.unit = 'linearMetre');