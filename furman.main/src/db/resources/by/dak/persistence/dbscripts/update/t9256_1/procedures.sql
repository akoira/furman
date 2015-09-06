CREATE PROCEDURE `update_sel_board`()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE order_id,sel_id,strips_id,furniture_id BIGINT(64);

    DECLARE cur1 CURSOR FOR select sel.order_id as order_id,
                                   sel.id as sel_id,
                                   s.id as strips_id,
                                   f.id as furniture_id
                            from storage_element_link sel
                            inner join furniture f on f.id = sel.furniture_id
                            inner join strips s on s.order_id = sel.order_id
                                                and f.furniture_type_id = s.furniture_type_id
                                                and f.FURNITURE_CODE_ID = s.FURNITURE_CODE_ID
                            order by sel.order_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur1;
read_loop: LOOP
            FETCH cur1 INTO order_id,sel_id,strips_id,furniture_id;

            IF done THEN
                LEAVE read_loop;
            END IF;

            UPDATE STORAGE_ELEMENT_LINK sel SET sel.STRIPS_ID = strips_id  WHERE sel.id = sel_id and sel.furniture_id = furniture_id;
        END LOOP;

  CLOSE cur1;
  END;

CREATE  PROCEDURE `update_sel_furniture`()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE order_group_id,order_id,sel_id,strips_id,furniture_id BIGINT(64);

    DECLARE cur1 CURSOR FOR select s.order_group_id as order_group_id,
                                            sel.order_id,
                                            sel.id as sel_id,
                                            s.id as strips_id,
                                            f.id as furniture_id
                            from storage_element_link sel
                            inner join furniture f on f.id = sel.furniture_id
                            inner join order_group_link ogl on ogl.order_id = sel.order_id
                            inner join strips s on s.order_group_id = ogl.group_id and f.furniture_type_id = s.furniture_type_id and f.FURNITURE_CODE_ID = s.FURNITURE_CODE_ID
                            order by sel.order_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    OPEN cur1;
read_loop: LOOP
            FETCH cur1 INTO order_group_id,order_id,sel_id,strips_id,furniture_id;

            IF done THEN
                LEAVE read_loop;
            END IF;

            UPDATE STORAGE_ELEMENT_LINK sel SET sel.STRIPS_ID = strips_id  WHERE sel.id = sel_id and sel.furniture_id = furniture_id;
        END LOOP;

  CLOSE cur1;
  END;


CREATE PROCEDURE `update_order_group`()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE o,g BIGINT(64);

    DECLARE c1 CURSOR FOR select order_id, group_id from public.order_group_link;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN c1;
    l1: LOOP
            FETCH c1 INTO o,g;

            IF done THEN
                LEAVE l1;
            END IF;
           UPDATE FURN_ORDER fo SET fo.ORDER_GROUP_ID = g WHERE fo.id = o;
        END LOOP;
    CLOSE c1;
END;
