select ft.ID as ft_id, ft.NAME as ft_name, fc.ID as fc_id, fc.NAME as fc_name, st.red, st.area, ft.unit from
(
select ft.ID as ft_id,fc.ID as fc_id, count(s.ID) as red, sum(s.WIDTH * s.USED_LENGTH + (s.LENGTH - s.USED_LENGTH)*s.WIDTH) as area
        from SEGMENT s inner join FURNITURE f on  f.ID = s.BOARD_ID
        inner join FURNITURE_TYPE ft on ft.ID = f.FURNITURE_TYPE_ID
        inner join FURNITURE_CODE fc on fc.ID = f.FURNITURE_CODE_ID
        inner join STRIPS st on s.FK_STRIPS_ID = st.ID
        inner join ORDER_ITEM oi on oi.ID = st.ORDER_ITEM_ID
        inner join FURN_ORDER fo on oi.ORDER_ID = fo.ID
        where s.FK_STRIPS_ID is not null and
--        fo.READY_DATE >= :start and
--        fo.READY_DATE <= :end and
        fo.STATUS in ('production','made','shipped','archive')
        group by ft.ID, fc.ID
) st
inner join FURNITURE_TYPE ft on ft.ID = st.ft_id
inner join FURNITURE_CODE fc on fc.ID = st.fc_id
order by ft.NAME, fc.NAME
