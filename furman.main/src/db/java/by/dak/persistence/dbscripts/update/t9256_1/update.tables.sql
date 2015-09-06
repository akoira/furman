ALTER TABLE `public`.`storage_element_link` ADD COLUMN `STRIPS_ID` BIGINT(64) NULL  AFTER `FURNITURE_ID` ;

call  update_sel_board;
call  update_sel_furniture;

delete from `public`.`storage_element_link` where strips_id is null;

ALTER TABLE `public`.`storage_element_link` DROP FOREIGN KEY `BOARD_LINK_ORDER` ;
ALTER TABLE `public`.`storage_element_link` DROP COLUMN `ORDER_ID`
, DROP INDEX `SYS_IDX_10201` ;

ALTER TABLE `public`.`storage_element_link` CHANGE COLUMN `STRIPS_ID` `STRIPS_ID` BIGINT(64) NOT NULL  ;

ALTER TABLE `public`.`storage_element_link`
  ADD CONSTRAINT `STRIPS_SE_LINK`
  FOREIGN KEY (`STRIPS_ID` )
  REFERENCES `public`.`strips` (`ID` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `STRIPS_SE_LINK` (`STRIPS_ID` ASC) ;


ALTER TABLE `public`.`furn_order` ADD COLUMN `ORDER_GROUP_ID` BIGINT(64) NULL  AFTER `DIALER_COST` ;
ALTER TABLE `public`.`furn_order`
  ADD CONSTRAINT `ORDER_GROUP_ORDER`
  FOREIGN KEY (`ORDER_GROUP_ID` )
  REFERENCES `public`.`order_group` (`ID` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `ORDER_GROUP_ORDER` (`ORDER_GROUP_ID` ASC) ;

call update_order_group;
drop table `public`.`order_group_link`;

                          M