package csxdddocker.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//@Getter
//@AllArgsConstructor
public record OrderRequestDTO(String orderType,LocalDateTime orderTime,Long size,BigDecimal price,String side,String stockCode,String traderCode) {
}
