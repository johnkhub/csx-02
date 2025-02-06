package csxdddocker.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

////import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdersTransactionsDTO {

    @Id
    private Long trans_id;
    private LocalDateTime trade_time;
    private BigDecimal stock_price;
    private Integer stock_size;
    private Long stock_id;
    private String stock_code;
    private Long buyer_order_id;
    private Long buyer_id;
    private String buyer_code;
    private Long seller_order_id;
    private Long seller_id;
    private String seller_code;
    private BigDecimal trade_amount;
}
