    package csxdddocker.entity;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;
    import lombok.*;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    /*

    @SqlResultSetMapping(
            name="OrdersStocksTradersResult",
            classes = @ConstructorResult(
                    targetClass = csxdddocker.model.OrdersStocksTradersDTO.class,
                    columns = {
                            @ColumnResult(name = "order_id", type = Long.class),
                         //LATER   @ColumnResult(name = "order_time", type= LocalDateTimeType.class), //type = Timestamp.class),     //localDateTimeType.class),
                            @ColumnResult(name = "size", type = BigDecimal.class),
                            @ColumnResult(name = "price", type = BigDecimal.class),
                            @ColumnResult(name = "side", type = String.class),
                            @ColumnResult(name = "isactive", type = Boolean.class),
                            @ColumnResult(name = "stock_id", type = Long.class),
                            @ColumnResult(name = "stock_code", type = String.class),
                            @ColumnResult(name = "stock_description", type = String.class),
                            @ColumnResult(name = "trader_id", type = Long.class),
                            @ColumnResult(name = "trader_description", type = String.class),
                            @ColumnResult(name = "trader_username", type = String.class),
                            @ColumnResult(name = "trader_password", type = String.class)
                    }
            )

    )
    @SqlResultSetMapping(
            name="OrdersTransactionsResult",
            classes = @ConstructorResult(
                    targetClass = csxdddocker.model.OrdersTransactionsDTO.class,
                    columns = {
                            @ColumnResult(name = "trans_id", type = Long.class),
                            @ColumnResult(name = "time", type = LocalDateTime.class),
                            @ColumnResult(name = "price", type = BigDecimal.class),
                            @ColumnResult(name = "size", type = BigDecimal.class),
                            @ColumnResult(name = "stock_id", type = Long.class),
                            @ColumnResult(name = "stock_code", type = String.class),
                            @ColumnResult(name = "stock_description", type = String.class),
                            @ColumnResult(name = "buyer_order_id", type = Long.class),
                            @ColumnResult(name = "buyer_trader_id", type = Long.class),
                            @ColumnResult(name = "buyer_trader_description", type = String.class),
                            @ColumnResult(name = "seller_order_id", type = Long.class),
                            @ColumnResult(name = "seller_trader_id", type = Long.class),
                            @ColumnResult(name = "seller_trader_description", type = String.class)
                    }
            )

    )

    @NamedNativeQuery(  //* this works
            name="NamedQueryOrdersStocksTraders",
            query="SELECT tbl_orders.order_id,tbl_orders.order_time,tbl_orders.size,tbl_orders.price,tbl_orders.side,tbl_orders.isactive " +
                    " ,tbl_stocks.stock_id,tbl_stocks.stock_code,tbl_stocks.stock_description " +
                    " ,tbl_traders.trader_id,tbl_traders.trader_description, tbl_traders.trader_username, tbl_traders.trader_password   " +
                    " FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id" +
                    " INNER JOIN tbl_traders ON tbl_orders.trader_id=tbl_traders.trader_id  ",
            resultSetMapping = "OrdersStocksTradersResult"
    )

    @NamedNativeQuery( //* this works
            name="NamedQueryOrdersStocksTradersByStockCode",
            query="SELECT tbl_orders.order_id,tbl_orders.order_time,tbl_orders.size,tbl_orders.price,tbl_orders.side,tbl_orders.isactive " +
                    " ,tbl_stocks.stock_id,tbl_stocks.stock_code,tbl_stocks.stock_description " +
                    " ,tbl_traders.trader_id,tbl_traders.trader_description, tbl_traders.trader_username, tbl_traders.trader_password   " +
                    " FROM tbl_orders INNER JOIN tbl_stocks ON tbl_orders.stock_id=tbl_stocks.stock_id" +
                                    " INNER JOIN tbl_traders ON tbl_orders.trader_id=tbl_traders.trader_id  " +
                    " WHERE tbl_stocks.stock_code=?",
            resultSetMapping = "OrdersStocksTradersResult"
    )

    @NamedNativeQuery(
            name="NamedQueryOrdersTransactions",
            query="SELECT trans.trans_id,trans.order_time,trans.trade_price,trans.trade_size" +
                   "   ,stock.stock_id,stock.stock_code,stock.stock_description" +
                   "   ,buyer_order.buyer_order_id AS buyer_order_id,buyer_order.trader_id" +
                   "   ,buyer.trader_id AS buyer_trader_id,buyer.trader_description AS buyer_trader_description" +
                   "   ,seller_order.order_id AS seller_order_id,seller_order.trader_id" +
                   "   ,seller.trader_id AS seller_trader_id,seller.trader_description AS seller_trader_description" +
                   "   FROM tbl_transactions trans INNER JOIN tbl_stocks stock ON trans.stock_id=stock.stock_id" +
                   "   INNER JOIN  tbl_orders buyer_order ON trans.buyer_id=buyer_order.order_id" +
                   "   INNER JOIN  tbl_traders buyer ON buyer_order.trader_id=buyer.trader_id" +
                   "  INNER JOIN  tbl_transactions transb ON transb.buyer_id=buyer_order.order_id" +
                   "  INNER JOIN  tbl_orders seller_order ON transb.seller_id=seller_order.order_id" +
                   "  INNER JOIN  tbl_traders seller ON transb.seller_id=seller.trader_id",
             resultSetMapping = "OrdersTransactionsResult"
    )

    @NamedNativeQuery(
            name="NamedQueryOrdersTransactionsByStockCode",
            query="SELECT trans.trans_id,trans.time,trans.price,trans.size" +
                    "   ,stock.stock_id,stock.stock_code,stock.stock_description" +
                    "   ,buyer_order.order_id AS buyer_order_id,buyer_order.trader_id" +
                    "   ,buyer.trader_id AS buyer_trader_id,buyer.trader_description AS buyer_trader_description" +
                    "   ,seller_order.order_id AS seller_order_id,seller_order.trader_id" +
                    "   ,seller.trader_id AS seller_trader_id,seller.trader_description AS seller_trader_description" +
                    "   FROM tbl_transactions trans INNER JOIN tbl_stocks stock ON trans.stock_id=stock.stock_id" +
                    "   INNER JOIN  tbl_orders buyer_order ON trans.buyer_id=buyer_order.order_id" +
                    "   INNER JOIN  tbl_traders buyer ON buyer_order.trader_id=buyer.trader_id" +
                    "  INNER JOIN  tbl_transactions transb ON transb.buyer_id=buyer_order.order_id" +
                    "  INNER JOIN  tbl_orders seller_order ON transb.seller_id=seller_order.order_id" +
                    "  INNER JOIN  tbl_traders seller ON transb.seller_id=seller.trader_id" +
                    " WHERE stock.stock_code=?",
            resultSetMapping = "OrdersTransactionsResult"
    )

    @NamedNativeQuery(
            name="NamedQueryOrdersTransactionsFiveActive",
            query="SELECT trans.trans_id,trans.time,trans.price,trans.size" +
                    "   stock.stock_code,stock.stock_description" +
                    "   ,buyer_order.order_id AS buyer_order_id,buyer_order.trader_id" +
                    "   ,buyer.trader_id AS buyer_trader_id,buyer.trader_description AS buyer_trader_description" +
                    "   ,seller_order.order_id AS seller_order_id,seller_order.trader_id" +
                    "   ,seller.trader_id AS seller_trader_id,seller.trader_description AS seller_trader_description" +
                    "   FROM tbl_transactions trans INNER JOIN tbl_stocks ON trans.stock_id=tbl_stock.stock_id" +
                    "   INNER JOIN  tbl_orders ON tbl_transactions.buyer_order_id=tbl_orders.order_id" +
                    "   INNER JOIN  tbl_traders buyer ON tbl_transactions.buyer_id=buyer.trader_id" +
                    "   INNER JOIN  tbl_traders seller ON tbl_transactions.buyer_id=seller.trader_id" +
                    "  INNER JOIN  tbl_orders seller_order ON transb.seller_id=seller_order.order_id" +
                    "  INNER JOIN  tbl_traders seller ON transb.seller_id=seller.trader_id" +
                    " ORDER BY trans.size",
            resultSetMapping = "OrdersTransactionsResult"
    )
*/
    /*
    SELECT sum(trans.size),sum(trans.price)
                       ,stock.stock_id,stock.stock_code,stock.stock_description
                       FROM tbl_transactions trans INNER JOIN tbl_stocks stock ON trans.stock_id=stock.stock_id
                       INNER JOIN  tbl_orders buyer_order ON trans.buyer_id=buyer_order.order_id
                       INNER JOIN  tbl_traders buyer ON buyer_order.trader_id=buyer.trader_id
                      INNER JOIN  tbl_transactions transb ON transb.buyer_id=buyer_order.order_id
                      INNER JOIN  tbl_orders seller_order ON transb.seller_id=seller_order.order_id
                      INNER JOIN  tbl_traders seller ON transb.seller_id=seller.trader_id
                      GROUP BY (stock.stock_id)
                        Order by sum(trans.size) asc
     */

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "tbl_orders")
//    SELECT Orders.order_id,Orders.order_time,Orders.order_type,Orders.size,Orders.price,Orders.side,Orders.isactive " +
//            ",Stocks.stock_id,Stocks.stock_code " +
//            " ,Traders.trader_id,Traders.trader_code " +
//            "     FROM tbl_orders Orders  INNER JOIN tbl_stocks Stocks ON Orders.stock_id=Stocks.stock_id " +
//            "     INNER JOIN tbl_traders Traders ON Orders.trader_id=Traders.trader_id   WHERE Orders.isactive =TRUE;

    @NamedQueries({
            @NamedQuery(name="Order.findActiveOrders",
                    query="SELECT ord FROM Order ord WHERE ord.isactive = true AND ord.size>0 ")
                    //query="SELECT ord FROM Order ord WHERE ord.isactive = ?1")

//            @NamedQuery(name="Country.findByName",
//                    query="SELECT c FROM Country c WHERE c.name = :name"),
    })
    public class Order {
        @Id
        //@GeneratedValue(strategy = GenerationType.) //UUID)  //IDENTITY)
        @Column(name = "order_id",unique = true)
        private String orderId;

        @Column(name = "order_type")
        private String orderType;

        @Column(name = "order_time")
        private LocalDateTime orderTime;

        @Column(name = "size")
        private Long size;

        @Column(name = "price")
        private BigDecimal price;

        @Column(name="side")
        private String side;    // = Action.BUY;

        @Column(name = "isactive")
        private Boolean isactive = Boolean.TRUE;

//        @Column(name = "stock_id")
//        private Long stockId;
//        @Column(name = "trader_id")
//        private Long traderId;

        @ManyToOne(fetch = FetchType.EAGER)
        @JsonIgnoreProperties(value = { "order_stock_rel" }, allowSetters = true)
        private  Stock order_Stock;

        @ManyToOne(fetch = FetchType.EAGER)
        @JsonIgnoreProperties(value = { "order_trader_rel" }, allowSetters = true)
        private Trader order_Trader;




/*
        @OneToMany()//fetch = FetchType.LAZY)
        @MapsId
        @JoinColumn(name = "stock_id")//,referencedColumnName = "stock_id",  unique=false)
        private ArrayList<Stock> stocks;

        @OneToMany()//fetch = FetchType.LAZY)
        @MapsId
        @JoinColumn(name = "trader_id")//,referencedColumnName = "trader_id",  unique=false)
        private ArrayList<Trader> traders;
*/

        /*
        @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
        @JoinTable(name = "order_stock_trader", joinColumns = { @JoinColumn(name = "order_id") }, inverseJoinColumns = {
                @JoinColumn(name = "stock_id") })
        //private Set<Stock> stocks = new HashSet<>();
        private Stock stock;// = new HashSet<>();

        @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
        @JoinTable(name = "order_stock_trader", joinColumns = { @JoinColumn(name = "order_id") }, inverseJoinColumns = {
                @JoinColumn(name = "trader_id") })
        //private Set<Trader> traders = new HashSet<>();
        private Trader trader;
        */
    }



