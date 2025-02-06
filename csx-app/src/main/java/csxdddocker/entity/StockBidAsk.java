package csxdddocker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_stock_bid_ask")
public class StockBidAsk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sba_id")
    private Long sbaNo;

    @Column(name = "sba_price")
    private BigDecimal sbaPrice;

    @Column(name = "sba_size")
    private BigDecimal sbaSize;

    @Column(name = "sba_count")
    private Long sbaCount;
}
