package csxdddocker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;


@SqlResultSetMapping(
        name="StockSummariesAllResult",
        classes = @ConstructorResult(
                targetClass = csxdddocker.entity.StockSummariesAll.class,
                columns = {
                        @ColumnResult(name = "ssa_id", type = Long.class),
                        @ColumnResult(name = "ssa_stock_code", type = String.class),
                        @ColumnResult(name = "ssa_stock_description", type = String.class),
                        @ColumnResult(name = "ssa_value", type = BigDecimal.class),
                        @ColumnResult(name = "ssa_max", type = BigDecimal.class),
                        @ColumnResult(name = "ssa_min", type = BigDecimal.class),
                        @ColumnResult(name = "ssa_previous", type = BigDecimal.class),
                        @ColumnResult(name = "ssa_last", type = BigDecimal.class),
                        @ColumnResult(name = "ssa_change", type = BigDecimal.class),
                        @ColumnResult(name = "ssa_percentage_change", type = BigDecimal.class)
                }
        )
)

@SqlResultSetMapping(
        name="ActiveStocksResult",
        classes = @ConstructorResult(
                targetClass = csxdddocker.entity.ActiveStocksDTO.class,
                columns = {
                        @ColumnResult(name = "as_id", type = Long.class),
                        @ColumnResult(name = "as_volume", type = BigDecimal.class),
                        @ColumnResult(name = "as_min", type = BigDecimal.class),
                        @ColumnResult(name = "as_max", type = BigDecimal.class),
                        @ColumnResult(name = "as_stock_code", type = String.class),
                        @ColumnResult(name = "as_stock_description", type = String.class)
                }
        )
)

@NamedNativeQuery(
        name="NamedQueryStockSummariesAll",
///////        query="DELETE FROM tbl_stock_summaries WHERE tbl_stock_summaries.ss_id>0;" +
                //*1 Previous
        query=" INSERT INTO tbl_stock_summaries_all (ssa_stock_code,ssa_stock_description,ssa_value,ssa_max,ssa_min,ssa_previous, ssa_last, ssa_change,ssa_percentage_change)" +
                " SELECT " +
                "tbl_stocks.stock_code,tbl_stocks.stock_description" +
                //*value
                ",(SELECT sum(trans.size)" +
                "  FROM tbl_transactions trans INNER JOIN tbl_stocks stock ON trans.stock_id=stock.stock_id" +
                "  where stock.stock_code=?" +
                "  GROUP BY (stock.stock_id)) " +
                //* max
                ",(SELECT max(trans.price)" +
                "  FROM tbl_transactions trans INNER JOIN tbl_stocks stock ON trans.stock_id=stock.stock_id" +
                "  where stock.stock_code=?)" +
                //* min
                ",(SELECT min(trans.price)" +
                "  FROM tbl_transactions trans INNER JOIN tbl_stocks stock ON trans.stock_id=stock.stock_id" +
                "  where stock.stock_code=?)" +
                 //* previous
                " ,(SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "                                 max(order_time) FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id   WHERE (Date(order_time)<=(current_date-1) AND tbl_stocks.stock_code=?) ))) AS previous " +
                //* last
                " ,(SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  " +
                "  WHERE (order_time=(SELECT max(order_time) " +
                " FROM tbl_orders) AND tbl_stocks.stock_code=?)) AS last " +
                 //* change
                ",((SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  " +
                "  WHERE (order_time=(SELECT max(order_time) " +
                " FROM tbl_orders)  AND tbl_stocks.stock_code=?))  " +
                " - (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "                                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)<=(current_date-1) AND tbl_stocks.stock_code=?) )))) AS change " +
                 //* % change
                " ,(((SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  " +
                "  WHERE (order_time=(SELECT max(order_time) " +
                " FROM tbl_orders) AND tbl_stocks.stock_code=?))  " +
                " - (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "                                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_stocks.stock_code=?) )))) " +
                " / ( (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "      max(order_time) FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE (Date(order_time)<=(current_date-1) AND tbl_stocks.stock_code=?) ))) /100 ))  AS percchange " +

                " FROM tbl_stocks WHERE tbl_stocks.stock_code=?;"
        ,
        //resultClass=csxdddocker.model.StockSummariesAll.class
        resultSetMapping = "StockSummariesAllResult"
)

@NamedNativeQuery(
        name="NamedQueryActiveStocks",
        query="SELECT sum(trans.size),max(trans.price),min(trans.price),stock.stock_id,stock.stock_code,stock.stock_description"+
               "   FROM tbl_transactions trans INNER JOIN tbl_stocks stock ON trans.stock_id=stock.stock_id"+
			   "	  where stock.stock_code=?" +
               "   GROUP BY (stock.stock_id)"
        ,
        resultSetMapping = "ActiveStocksResult"
)

@NamedNativeQuery(
        name="NamedQueryGainerStocks",
        //*1 Previous
        query=" INSERT INTO tbl_stock_summaries_all (ssa_stock_code,ssa_stock_description,ssa_value,ssa_previous, ssa_last, ssa_change,ssa_percentage_change)" +
                " SELECT " +
                "tbl_stocks.stock_code,tbl_stocks.stock_description,77 " +
                //* previous
                " ,(SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "                                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?) ))) AS previous " +
                //* last
                " ,(SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  " +
                "  WHERE (order_time=(SELECT max(order_time) " +
                " FROM tbl_orders) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?)) AS last " +
                //* change
                ",((SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  " +
                "  WHERE (order_time=(SELECT max(order_time) " +
                " FROM tbl_orders) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?))  " +
                " - (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "                                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?) )))) AS change " +
                //* % change
                " ,(((SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  " +
                "  WHERE (order_time=(SELECT max(order_time) " +
                " FROM tbl_orders) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?))  " +
                " - (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "                                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?) )))) " +
                " / ( (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
                "      max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?) ))) /100 ))  AS percchange " +

                " FROM tbl_stocks WHERE tbl_stocks.stock_code=?;"
        ,
        resultClass=csxdddocker.entity.StockSummariesAll.class
        //resultSetMapping = "StockSummariesResult"
)



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_stock_summaries_all")
public class StockSummariesAll {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ssa_id")
    private Long ssaId;

    @Column(name = "ssa_stock_code")
    private String ssaStockCode;

    @Column(name = "ssa_stock_description")
    private String ssaStockDescription;

    @Column(name = "ssa_value")
    private BigDecimal ssaValue;

    @Column(name = "ssa_max")
    private BigDecimal ssaMax;

    @Column(name = "ssa_min")
    private BigDecimal ssaMin;

    @Column(name = "ssa_previous")
    private BigDecimal ssaPrevious;

    @Column(name = "ssa_last")
    private BigDecimal ssaLast;

    @Column(name = "ssa_change")
    private BigDecimal ssaChange;

    @Column(name = "ssa_percentage_change")
    private BigDecimal ssaPercentageChange;


}
