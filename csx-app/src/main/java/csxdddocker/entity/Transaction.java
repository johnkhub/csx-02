package csxdddocker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // IDENTITY)
    @Column(name = "trans_id")
    private String transId;

    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "stock_Code")
    private String stockCode;

    // @JsonFormat(pattern = "yyyy/MM/dd")
    @Column(name = "trade_time")
    private LocalDateTime tradeTime;

    @Column(name = "trade_price")
    private BigDecimal tradePrice;

    @Column(name = "trade_size")
    private Long tradeSize;

    @Column(name = "buyer_order_id")
    private String buyerOrderId;

    @Column(name = "buyer_id")
    private String buyerId;

    @Column(name = "buyer_Code")
    private String buyerCode;

    @Column(name = "seller_order_id")
    private String sellerOrderId;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "seller_Code")
    private String sellerCode;

    @Column(name = "trade_amount")
    private BigDecimal tradeAmount;

    @Column(name = "market_price")
    private BigDecimal marketPrice;
}
