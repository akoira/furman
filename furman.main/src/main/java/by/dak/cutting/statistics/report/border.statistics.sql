select st.TYPE_ID, st.CODE_ID, ft.NAME, fc.NAME, m.NAME as MNAME, st.SUMMA  from
(select f.FURNITURE_TYPE_ID as TYPE_ID, f.FURNITURE_CODE_ID as CODE_ID, SUM(f.LENGTH) as SUMMA from FURNITURE f
inner join FURN_ORDER fo on f.ORDER_ID = fo.ID
where
f.DISCRIMINATOR = 'Border'
and
f.STATUS = 'used'
and
fo.READY_DATE >= $P{START} and
fo.READY_DATE <= $P{END} and
GROUP BY
f.FURNITURE_TYPE_ID,f.FURNITURE_CODE_ID) st
inner join FURNITURE_TYPE ft on ft.ID = st.TYPE_ID
inner join FURNITURE_CODE fc on fc.ID = st.CODE_ID
inner join MANUFACTURER m on m.ID = fc.MANUFACTURER_ID
order by
ft.NAME, fc.NAME, MNAME