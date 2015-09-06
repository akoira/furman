-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_orders`(smy TEXT, emy TEXT)
BEGIN
    DECLARE _output TEXT DEFAULT '';
    DECLARE done_order INT DEFAULT 0;
    DECLARE o_id BIGINT(64);

    DECLARE order_cur CURSOR FOR select id from furn_order where ready_date < emy and ready_date > smy;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_order = 1;
    
    
    OPEN order_cur;
order_loop: LOOP
                FETCH order_cur INTO o_id;

                IF done_order THEN
                    LEAVE order_loop;
                END IF;
                SET _output = CONCAT(_output, ", ");
                SET _output = CONCAT(_output, o_id);

            call clear_order_item(o_id);
            delete from order_item where order_id = o_id;
            delete from strips where order_id = o_id;
            update furniture set CREATEDBY_ORDER_ID = null where CREATEDBY_ORDER_ID = o_id;
            delete from furniture where order_id = o_id;
            delete from furn_order where id = o_id;
            call clear_delivery(smy, emy);
            delete from delivery where DELIVERY_DATE > smy and DELIVERY_DATE < emy; 
        END LOOP;
  CLOSE order_cur;
  SELECT _output;
END
