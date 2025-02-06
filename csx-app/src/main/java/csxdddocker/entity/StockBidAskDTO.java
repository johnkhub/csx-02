package csxdddocker.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockBidAskDTO {

    @Id
    private Long no;
    private BigDecimal price;
    private BigDecimal size;
    private Long count;

}

