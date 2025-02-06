package csxdddocker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;

/***** Today last price - Yesterday last price
select 'Ola', ((select price from tbl_orders where order_time=(select max(order_time) from tbl_orders))-
(select  (select price from tbl_orders where order_time=(select
max(order_time) from tbl_orders  where Date(order_time)=(current_date-1) ))))
 */
/*
INSERT INTO tbl_stock_summaries (ss_label,ss_value)
                  SELECT 'Previous', (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id WHERE order_time=(SELECT max(order_time) FROM tbl_orders))
 */

/* Previous
SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT
                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='SELL' AND tbl_stocks.stock_code='aaa') ))
 */

/*
@SqlResultSetMapping(
        name="StockSummariesResult",
        classes = @ConstructorResult(
                targetClass = csxdddocker.model.StockSummariesDTO.class,
                columns = {
                        @ColumnResult(name = "ss_id", type = Long.class),
                        @ColumnResult(name = "ss_label", type = String.class),
                        @ColumnResult(name = "ss_value", type = BigDecimal.class),
                        @ColumnResult(name = "ss_stock_code", type = String.class),
                        @ColumnResult(name = "ss_stock_description", type = String.class)
                }
        )
)
*/
//*
@SqlResultSetMapping(
        name="StockBidAskResult",
        classes = @ConstructorResult(
                targetClass = csxdddocker.entity.StockBidAsk.class,
                columns = {
                        @ColumnResult(name = "sbaNo", type = Long.class),
                        @ColumnResult(name = "sbaPrice", type = BigDecimal.class),
                        @ColumnResult(name = "sbaSize", type = BigDecimal.class),
                        @ColumnResult(name = "sbaCount", type = Long.class)
                }
        )
)

@NamedNativeQuery(
        name="NamedQueryStockSummariesByStockCode",
        query="DELETE FROM tbl_stock_summaries WHERE tbl_stock_summaries.ss_id>0;" +
                /*1 Previous
                "INSERT INTO tbl_stock_summaries (ss_label,ss_value)" +
              " SELECT 'Ma',(SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "        max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='SELL' AND tbl_stocks.stock_code='aaa') )));"+
                //*2 Last
              " INSERT INTO tbl_stock_summaries (ss_label,ss_value)" +
              "  SELECT 'Mao',sum(tbl_orders.size)" +
              " FROM tbl_stocks INNER JOIN tbl_orders ON tbl_orders.stock_id=tbl_stocks.stock_id" +
                " WHERE tbl_orders.side=? AND tbl_stocks.stock_code=?;"+
                */
                 //*1 Previous
                " INSERT INTO tbl_stock_summaries (ss_label,ss_value,ss_stock_code,ss_stock_descrition)" +
                "  SELECT 'Previous', (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='SELL' AND tbl_stocks.stock_code=?) ))),tbl_stocks.stock_code,tbl_stocks.stock_description FROM tbl_stocks WHERE tbl_stocks.stock_code=?;" +
                //*2 Last
                " INSERT INTO tbl_stock_summaries (ss_label,ss_value,ss_stock_code,ss_stock_descrition)" +
                "  SELECT 'Last', (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE (order_time=(SELECT max(order_time) FROM tbl_orders) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?)),tbl_stocks.stock_code,tbl_stocks.stock_description FROM tbl_stocks WHERE tbl_stocks.stock_code=?;" +
                //*3 Change
                " INSERT INTO tbl_stock_summaries (ss_label,ss_value,ss_stock_code,ss_stock_descrition)" +
                "  SELECT 'Change', (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE (order_time=(SELECT max(order_time) FROM tbl_orders) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?))" +
                " - (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='SELL' AND tbl_stocks.stock_code=?) ))),tbl_stocks.stock_code,tbl_stocks.stock_description FROM tbl_stocks WHERE tbl_stocks.stock_code=?;"
                //*4 % Change
            //    " INSERT INTO tbl_stock_summaries (ss_label,ss_value)" +
            //    "  SELECT '% Change', (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE (order_time=(SELECT max(order_time) FROM tbl_orders) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?))" +
            //    " - (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
            //    "                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='SELL' AND tbl_stocks.stock_code=?) )));"
                //*5 Open

                //*6 High

                //*7 Low

                //*8 Value

                //*9 Trades

                //*10 Volume

                //*11 Outstanding

                //*12 Market Capitalization

                //*13 Inst. Status

                //*14 Market Status

                //*15 BoardLot

                //*16 Fluctuation

                //*17 Floor Price

                //*18 Ceiling Price

                //*19 Dyn T Low

                //*20 Dyn T High

                //*21 Par Value

                //*22 Margin Rate

                //*23  Open To Foreigners

                    ,
        resultClass=csxdddocker.entity.StockSummaries.class
        //resultSetMapping = "StockSummariesResult"
)
@NamedNativeQuery( //ROW_NUMBER() Over(ORDER BY tbl_orders.price ASC) AS sba_id,
        name="NamedQueryStockBidAskByStockCode",
        query="DELETE FROM tbl_stock_bid_ask WHERE tbl_stock_bid_ask.sba_id>0;" +
                "INSERT INTO tbl_stock_bid_ask(sba_price,sba_size,sba_count)" +
        " SELECT tbl_orders.price,sum(tbl_orders.size),count(tbl_orders.price)" +
        " FROM tbl_stocks INNER JOIN tbl_orders ON tbl_orders.stock_id=tbl_stocks.stock_id" +
        " WHERE tbl_stocks.stock_code=? AND tbl_orders.side=?" +
        " GROUP BY (tbl_orders.price);",
      resultSetMapping = "StockBidAskResult"
        //* or this resultClass=csxdddocker.model.StockBidAsk.class
)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_stock_summaries")
public class StockSummaries {

    public final static String sss="stk1";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ss_id")
    private Long ssId;

    @Column(name = "ss_stock_code")
    private String ssStockCode;

    @Column(name = "ss_stock_descrition")
    private String ssStockDescription;

    @Column(name = "ss_label")
    private String ssLabel;

    @Column(name = "ss_Value")
    private BigDecimal ssValue;

    public StockSummaries(String ssStockCode, String ssStockDescription, String ssLabel, BigDecimal ssValue) {
        this.ssStockCode = ssStockCode;
        this.ssStockDescription = ssStockDescription;
        this.ssLabel = ssLabel;
        this.ssValue = ssValue;
    }
}
