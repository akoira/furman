START TRANSACTION;

-- Deleting data from dependent tables
DELETE
FROM public.detail_documents
WHERE id BETWEEN 0 AND 1000000000;

DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 122653450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 122753450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 122853450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 122953450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123053450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123153450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123253450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123353450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123353450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123453450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123553450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123653450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123753450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123853450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 123953450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124053450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124153450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124253450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124353450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124453450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124553450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124653450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124753450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124853450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 124953450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 125053450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 125153450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 125253451;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 125353450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 125453450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 125553450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 125653450;
DELETE
FROM public.order_detail
WHERE id BETWEEN 0 AND 1000000000000;

DELETE
FROM public.door_part
WHERE id BETWEEN 0 AND 1000000;
DELETE
FROM public.door_pointer
WHERE id BETWEEN 0 AND 1000000;
DELETE
FROM public.door
WHERE id BETWEEN 0 AND 1000000;
DELETE
FROM public.door_order
WHERE id BETWEEN 0 AND 1000000;

COMMIT;

-- Change foreign keys (for cascade deleting)
ALTER TABLE public.order_item
DROP
FOREIGN KEY sourceOrderItem;

ALTER TABLE public.order_item
    ADD CONSTRAINT sourceOrderItem
        FOREIGN KEY (source_ID) REFERENCES order_item (ID) ON DELETE CASCADE;

DELETE
FROM public.common_data
WHERE ORDER_ID NOT IN (SELECT ID FROM public.furn_order)
  AND id BETWEEN 0 AND 10000000000000;

DELETE
FROM public.furniture
WHERE ORDER_ID NOT IN (SELECT ID FROM public.furn_order)
  AND id BETWEEN 0 AND 10000000000000;

DELETE
FROM public.order_status_date
WHERE ORDER_ID BETWEEN 0 AND 10000000000000;

DELETE
FROM public.order_status_date
WHERE ORDER_ID NOT IN (SELECT ID FROM public.furn_order)
  AND ORDER_ID BETWEEN 0 AND 10000000000000;

DELETE
FROM public.order_item
WHERE id BETWEEN 0 AND 122922351;
DELETE
FROM public.order_item
WHERE id BETWEEN 122922351 AND 123183400;
DELETE
FROM public.order_item
WHERE id BETWEEN 123183400 AND 1000000000000000000;

START TRANSACTION;

-- Change foreign keys (for cascade deleting)
-- cash_expense
ALTER TABLE public.cash_expense
DROP
FOREIGN KEY FK_cash_expense_furn_order;
ALTER TABLE public.cash_expense
    ADD CONSTRAINT FK_cash_expense_furn_order
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- cash_income
ALTER TABLE public.cash_income
DROP
FOREIGN KEY FK_cash_income_furn_order;
ALTER TABLE public.cash_income
    ADD CONSTRAINT FK_cash_income_furn_order
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- common_data
ALTER TABLE public.common_data
DROP
FOREIGN KEY COMMON_DATA_ORDER;
ALTER TABLE public.common_data
    ADD CONSTRAINT COMMON_DATA_ORDER
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- discounts
ALTER TABLE public.discounts
DROP
FOREIGN KEY FK_discount_order_id;
ALTER TABLE public.discounts
    ADD CONSTRAINT FK_discount_order_id
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- furniture (bookedByOrder_ID)
ALTER TABLE public.furniture
DROP
FOREIGN KEY FK_l1usvik01xi7i0jxi5lgd5ek7;
ALTER TABLE public.furniture
    ADD CONSTRAINT FK_l1usvik01xi7i0jxi5lgd5ek7
        FOREIGN KEY (bookedByOrder_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- furniture (CREATEDBY_ORDER_ID)
ALTER TABLE public.furniture
DROP
FOREIGN KEY FURNITURE_CREATED_ORDER;
ALTER TABLE public.furniture
    ADD CONSTRAINT FURNITURE_CREATED_ORDER
        FOREIGN KEY (CREATEDBY_ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- furniture (ORDER_ID)
ALTER TABLE public.furniture
DROP
FOREIGN KEY FURNITURE_ORDER;
ALTER TABLE public.furniture
    ADD CONSTRAINT FURNITURE_ORDER
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- order_information
ALTER TABLE public.order_information
DROP
FOREIGN KEY FK_ORDER_INFO_ID;
ALTER TABLE public.order_information
    ADD CONSTRAINT FK_ORDER_INFO_ID
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- order_item
ALTER TABLE public.order_item
DROP
FOREIGN KEY ORDER_ITEM_ORDER;
ALTER TABLE public.order_item
    ADD CONSTRAINT ORDER_ITEM_ORDER
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- order_status_date
ALTER TABLE public.order_status_date
DROP
FOREIGN KEY FK_ORDER_ID_STATUS_DATE_ID;
ALTER TABLE public.order_status_date
    ADD CONSTRAINT FK_ORDER_ID_STATUS_DATE_ID
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- payment_schedule
ALTER TABLE public.payment_schedule
DROP
FOREIGN KEY FK_payment_schedule_furn_order_ORDER_ID;
ALTER TABLE public.payment_schedule
    ADD CONSTRAINT FK_payment_schedule_furn_order_ORDER_ID
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

-- strips
ALTER TABLE public.strips
DROP
FOREIGN KEY STRIPS_ORDER;
ALTER TABLE public.strips
    ADD CONSTRAINT STRIPS_ORDER
        FOREIGN KEY (ORDER_ID) REFERENCES furn_order (ID) ON DELETE CASCADE;

COMMIT;

-- Deleting data from parent table
DELETE
FROM public.furn_order
WHERE id BETWEEN 0 AND 122935658;
DELETE
FROM public.furn_order
WHERE id BETWEEN 122935658 AND 123199818;
DELETE
FROM public.furn_order
WHERE id BETWEEN 123199818 AND 10000000549;
DELETE
FROM public.furn_order
WHERE id BETWEEN 10000000549 AND 10000000000000000;
