package csxdddocker.entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///////import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdersStocksTradersDTO {

    @Id
    private Long order_id;
    private LocalDateTime order_time;
    private BigDecimal size;
    private BigDecimal price;
    private String side;
    private Boolean isactive;
    private Long stock_id;
    private String stock_code;
    private String stock_description;
    private Long trader_id;
    private String trader_description;
    private String trader_username;
    private String trader_password;


}
