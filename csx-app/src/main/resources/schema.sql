

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