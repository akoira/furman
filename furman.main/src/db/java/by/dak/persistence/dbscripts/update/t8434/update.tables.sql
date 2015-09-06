update FURNITURE_TYPE ft set ft.UNIT = 'linearMetre' where ft.ID in (select ft.ID from FURNITURE_TYPE ft where ft.UNIT is null and ft.DISCRIMINATOR = 'BorderDef');

ALTER TABLE FURNITURE_TYPE ALTER UNIT VARCHAR(255) not null;
