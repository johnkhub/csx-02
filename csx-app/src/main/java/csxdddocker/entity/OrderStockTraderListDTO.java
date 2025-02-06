package csxdddocker.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
//@Entity
public class OrderStockTraderListDTO {
  //  @Id
    private String orderId;
    private String orderType;
    private LocalDateTime orderTime;
    private Long size;
    private BigDecimal price;
    private String side;
    private Boolean isactive;

    private Stock stock;
    private  Trader trader;
}
