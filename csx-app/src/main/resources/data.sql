--INSERT  into tbl (order_time, size, price, side, stock_id,trader_id,isactive)
--values(	'10-10-2000', 750, 101 , 'SELL',1,1, true);
--INSERT  into tbl (order_time, size, price, side, stock_id,trader_id,isactive)
---values(	'10-10-2000', 200, 100 , 'BUY',1,1, true);


--LocalDate order_time,BigDecimal size, BigDecimal price, String side, Long stockID, Long traderID, Boolean isactive

CREATE OR REPLACE FUNCTION public.secgetdate(
	)
    RETURNS timestamp without time zone
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
declare
mydate timestamp;
begin
select order_time
into mydate
from tbl_orders
where order_id=1;

return mydate;
end;
$BODY$;

