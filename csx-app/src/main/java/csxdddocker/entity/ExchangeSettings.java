package csxdddocker.entity;


import jakarta.persistence.*;
import lombok.*;

///import javax.persistence.*;
import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "tbl_exchange_settings")
public class ExchangeSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sett_id")
    private Long orderId;

    @Column(name = "open_time")
    private Timestamp openTime;

    @Column(name = "closing_time")
    private Timestamp closingTime;
}
