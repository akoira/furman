-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `clear_order_item`(order_id BIGINT(64))
BEGIN
                    DECLARE _output TEXT DEFAULT '';
                    DECLARE oi_done INT DEFAULT 0;
                    DECLARE oi_id BIGINT(64);
                    DECLARE oi_cur CURSOR FOR select id from order_item oi where oi.order_id = order_id;
                    DECLARE CONTINUE HANDLER FOR NOT FOUND SET oi_done = 1;

                    open  oi_cur;
                    oi_loop: LOOP
                        FETCH oi_cur INTO oi_id;
                        IF oi_done THEN
                            LEAVE oi_loop;
                        END IF;
                        SET _output = CONCAT(_output, ", ");
                        SET _output = CONCAT(_output, oi_id);
                        delete from order_detail where order_item_id = oi_id;
                    END LOOP;
                    close oi_cur;
                    -- select _output;

END;
