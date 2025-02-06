package csxdddocker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)  //IDENTITY)
    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "stock_code",unique = true)
    private String stockCode;

    @Column(name = "stock_description")
    private String stockDescription;


/*
    @OneToOne(
            mappedBy = "stock",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Order order;
    */

    /*
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "stocks")
    */
    /*
    @ManyToOne()
    @JsonIgnore
    private Order order;// = new HashSet<>();
    */


}
