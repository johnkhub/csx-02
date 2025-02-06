package csxdddocker.route;

import csxdddocker.config.DataSourceConfig;
import csxdddocker.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class MatchingEngineRoute extends RouteBuilder {
    @Autowired
    DataSourceConfig dataSourceConfig;
    @Autowired
    private OrderProcessService orderProcessService;
    @Bean
    public SqlComponent sql(){
        SqlComponent sql = new SqlComponent();
        sql.setDataSource(dataSourceConfig.getConfig());
        return sql;
    }

    @Override
    //@SuppressWarnings("unchecked")
    public void configure() throws Exception {
        from("timer:get_active_orders?period=10000")
        .to("jpa:csxdddocker.model.Order?namedQuery=Order.findActiveOrders")
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    ArrayList<Order> orders=  (ArrayList<Order>) exchange.getIn().getBody();
                    log.info("\n\n ===) Matching Engine, List of Active Orders to process: \t" +" Count: "+orders.size()+" = "+ orders);
                    //*
                    orderProcessService.processOrder(orders);
                    exchange.getIn().setBody(orders);
                }
            });
        }
    }

