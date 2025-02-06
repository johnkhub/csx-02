package csxdddocker;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CsxDdDockerApplication implements CommandLineRunner {

    @Autowired
    private EntityManager entityManager;
    public static void main(String[] args) {
        SpringApplication.run(CsxDdDockerApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        /*
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateFormat.parse("2020-10-10 10:10:10");
        long time = date.getTime();
        this.orderRepository.save(new Order(new Timestamp(time), new BigDecimal(20.0), new BigDecimal(10.0), "BUY", 1L, 1L, true));
        dateFormat.parse("2020-10-10 10:10:10");
        time = date.getTime();
        this.orderRepository.save(new Order(new Timestamp(time), new BigDecimal(20.0), new BigDecimal(10.0), "BUY", 1L, 1L, true));
        */
/*
try {
    String sql = "CREATE TABLE IF NOT EXISTS tbl_orders(" +
            "order_id BIGSERIAL PRIMARY KEY," +
            "isactive boolean," +
            "order_time timestamp(6) without time zone," +
            "order_type character varying(255)," +
            "price numeric(38,2)," +
            "size bigint," +
            "stock_id bigint," +
            "trader_id bigint," +
            "side character varying(255)," +
            "FOREIGN KEY (stock_id) REFERENCES tbl_stocks(stock_id)," +
            "FOREIGN KEY (trader_id) REFERENCES tbl_traders(trader_id))";

    entityManager.createNativeQuery(sql).executeUpdate();
}catch (Exception e){
    System.out.println("\n\nEXCEPtionnnnnnn"+e.getMessage());
}

*/

        /*

EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Author a = new Author();
a.setFirstName("Thorben");
a.setLastName("Janssen");
a.setCategory(Category.NON_FICTION);
a.setPseudonym("Thorben Janssen");
em.persist(a);

em.getTransaction().commit();
em.close();
         */
    }


}
