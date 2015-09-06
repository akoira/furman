-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `clear_delivery`(smy TEXT, emy TEXT)
BEGIN
    DECLARE _output TEXT DEFAULT '';
    DECLARE done INT DEFAULT 0;
    DECLARE d_id BIGINT(64);

    DECLARE d_cur CURSOR FOR select id from delivery where  delivery_date < emy and delivery_date > smy;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN d_cur;
    d_loop: LOOP
                FETCH d_cur INTO d_id;

                IF done THEN
                    LEAVE d_loop;
                END IF;
--                SET _output = CONCAT(_output, ", ");
--                SET _output = CONCAT(_output, d_id);
                update furniture set delivery_id = null where delivery_id = d_id;

        END LOOP;
  CLOSE d_cur;
--  SELECT _output;
END
