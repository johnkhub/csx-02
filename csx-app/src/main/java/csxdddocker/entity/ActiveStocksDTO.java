package csxdddocker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class ActiveStocksDTO  {

        @Id
        private Long as_id;
        private BigDecimal as_volume;
        private BigDecimal as_min;
        private BigDecimal as_max;
        private String as_stock_code;
        private String as_stock_description;

    }
