ALTER TABLE `public`.`order_detail` DROP FOREIGN KEY `SECOND_BOARD` ;
ALTER TABLE `public`.`order_detail`
  ADD CONSTRAINT `SECOND_BOARD`
  FOREIGN KEY (`SECOND_BOARD_ID` )
  REFERENCES `public`.`order_detail` (`ID` )
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

ALTER TABLE `public`.`storage_element_link` DROP FOREIGN KEY `STRIPS_SE_LINK` ;
ALTER TABLE `public`.`storage_element_link`
  ADD CONSTRAINT `STRIPS_SE_LINK`
  FOREIGN KEY (`STRIPS_ID` )
  REFERENCES `public`.`strips` (`ID` )
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

ALTER TABLE `public`.`currency` DROP FOREIGN KEY `CURRENCY_DAILYSHEET` ;
ALTER TABLE `public`.`currency`
  ADD CONSTRAINT `CURRENCY_DAILYSHEET`
  FOREIGN KEY (`DAILYSHEET_ID` )
  REFERENCES `public`.`daily_sheet` (`ID` )
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

ALTER TABLE `public`.`furn_order` DROP FOREIGN KEY `CREATED_DAILY_SHEET` ;
ALTER TABLE `public`.`furn_order`
  ADD CONSTRAINT `CREATED_DAILY_SHEET`
  FOREIGN KEY (`FK_CREATED_DAILY_SHEET_ID` )
  REFERENCES `public`.`daily_sheet` (`ID` )
  ON DELETE CASCADE
  ON UPDATE NO ACTION;
ALTER TABLE `public`.`furniture` DROP FOREIGN KEY `FURNITURE_CREATED_ORDER` ;

ALTER TABLE `public`.`furniture`
  ADD CONSTRAINT `FURNITURE_CREATED_ORDER`
  FOREIGN KEY (`CREATEDBY_ORDER_ID` )
  REFERENCES `public`.`furn_order` (`ID` )
  ON DELETE SET NULL
  ON UPDATE NO ACTION;

ALTER TABLE `public`.`storage_element_link` DROP FOREIGN KEY `STORAGE_FURN_CONSTR` ;
ALTER TABLE `public`.`storage_element_link`
  ADD CONSTRAINT `STORAGE_FURN_CONSTR`
  FOREIGN KEY (`FURNITURE_ID` )
  REFERENCES `public`.`furniture` (`ID` )
  ON DELETE CASCADE
  ON UPDATE NO ACTION;


-- delete from storage_element_link where furniture_id in (select id );
-- delete from furniture where furniture_code_id in ( select id from furniture_code where manufacturer_id in (6651,6652))
-- delete from order_detail where furniture_code_id in (select id from furniture_code where manufacturer_id in (6651,6652))
-- delete from strips where furniture_code_id in (select id from furniture_code where manufacturer_id in (6651,6652))
-- delete from furniture_code where manufacturer_id in (6651,6652)


