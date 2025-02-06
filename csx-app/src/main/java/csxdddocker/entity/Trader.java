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
@Table(name = "tbl_traders")
public class Trader {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)  //IDENTITY)
    @Column(name = "trader_id")
    private String traderId;

    @Column(name = "trader_code",unique = true)
    private String traderCode;

    @Column(name = "trader_description")
    private String traderDescription;

    @Column(name = "trader_username")
    private String traderUsername;

    @Column(name = "trader_password")
    private String traderPassword;


/*
    @OneToOne(
            mappedBy = "trader",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Order order;
    */

    /*
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "traders")
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();
    */
  //  private Order order;// = new HashSet<>();

/*
    @ManyToOne()
    @JsonIgnore
    private Order order;// = new HashSet<>();
    */
}
