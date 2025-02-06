package csxdddocker.repository;

import csxdddocker.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StockSummariesAllRepository extends JpaRepository<StockSummariesAll, Long>{

    @Modifying
    @Query(name = "NamedQueryStockSummariesAll", nativeQuery = true)
    List<StockSummariesAll> insertStockSummariesAll(String code1, String code2,String code3,String code4,String code5,String code6,String code7,String code8,String code9,String code10,String code11);

    @Query(value="SELECT ssa FROM StockSummariesAll ssa WHERE ssa.ssaChange >= 0")
    List<StockSummariesAll> findGainerStocks();

    @Query(value="SELECT ssa FROM StockSummariesAll ssa WHERE ssa.ssaChange < 0")
    List<StockSummariesAll> findLoserStocks();

    @Query(value="SELECT ssa FROM StockSummariesAll ssa WHERE ssa.ssaValue >= 0 ORDER BY ssa.ssaValue DESC")
    List<StockSummariesAll> findActiveStocks();

    @Query(value="SELECT ssa FROM StockSummariesAll ssa WHERE ssa.ssaStockCode =  :code")
    List<StockSummariesAll> findSummariesByCode(String code);

    /*
    @Query(value="INSERT INTO tbl_stock_bid_ask(sba_price,sba_size,sba_count)" +
            "             SELECT tbl_orders.price,sum(tbl_orders.size),count(tbl_orders.price)\n" +
            "             FROM tbl_stocks INNER JOIN tbl_orders ON tbl_orders.stock_id=tbl_stocks.stock_id\n" +
            "             WHERE tbl_stocks.stock_code=? AND tbl_orders.side=?\n" +
            "             GROUP BY (tbl_orders.price) stc.stockCode = :stockCode AND ")
    //Optional<Stock>//
    void listStockBidAskByStockCode(String stockcode, String orderside);
    */

    //@Query("Select ord from Order ord where ord.side = :side and ord.isactive = true order by ord.price desc")

  //  @Query=("INSERT INTO Stock_Summaries_All (SELECT Stock Stock.stock_code,Stock.stock_description,77 FROM tbl_stocks WHERE tbl_stocks.stock_code=?)")
            //* previous
          //  " ,(SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
          //  "                                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?) ))) AS previous " +
            //* last
         //   " ,(SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  " +
         //   "  WHERE (order_time=(SELECT max(order_time) " +
         //   " FROM tbl_orders) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?)) AS last " +
            //* change
         //   ",((SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  " +
         //   "  WHERE (order_time=(SELECT max(order_time) " +
         //   " FROM tbl_orders) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?))  " +
         //   " - (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
          //  "                                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?) )))) AS change " +
            //* % change
         //   " ,(((SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  " +
         //   "  WHERE (order_time=(SELECT max(order_time) " +
         //   " FROM tbl_orders) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?))  " +
         //   " - (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
        //    "                                 max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?) )))) " +
        //    " / ( (SELECT  (SELECT price FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id  WHERE order_time=(SELECT " +
        //    "      max(order_time) FROM tbl_orders  WHERE (Date(order_time)=(current_date-1) AND tbl_orders.side='BUY' AND tbl_stocks.stock_code=?) ))) /100 ))  AS percchange " +

         //   " FROM tbl_stocks WHERE tbl_stocks.stock_code=?;"
   // public void insertAll(@Param("side") String side);
}
