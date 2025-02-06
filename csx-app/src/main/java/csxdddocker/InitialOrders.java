package csxdddocker;

import csxdddocker.entity.Order;
import csxdddocker.entity.Stock;
import csxdddocker.entity.Trader;
import csxdddocker.repository.OrderRepository;
import csxdddocker.repository.StockRepository;
import csxdddocker.repository.TraderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author atquil
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class InitialOrders implements CommandLineRunner {
    private final StockRepository stockRepo;
    private final TraderRepository traderRepo;
    private final OrderRepository orderRepo;

    @Override
    public void run(String... args) throws Exception {

        Stock stock1 = new Stock(null,"stk1","stk11");
        Stock stock2 = new Stock(null,"stk2","stk22");
        Stock stock3 = new Stock(null,"stk3","stk33");
        if(stockRepo.findStockByCode(stock1.getStockCode()).isEmpty()){Stock stk=stockRepo.save(stock1);}
        if(stockRepo.findStockByCode(stock2.getStockCode()).isEmpty()) {stockRepo.save(stock2);}
        if(stockRepo.findStockByCode(stock3.getStockCode()).isEmpty()) {stockRepo.save(stock3);}

        Trader trader1=new Trader(null, "trd1","trd11",null,null);
        Trader trader2=new Trader(null, "trd2","trd22",null,null);;
        Trader trader3=new Trader(null, "trd3","trd33",null,null);
        if(traderRepo.findTraderByCode(trader1.getTraderCode()).isEmpty()) {traderRepo.save(trader1);}
        if(traderRepo.findTraderByCode(trader2.getTraderCode()).isEmpty()) {traderRepo.save(trader2);}
        if(traderRepo.findTraderByCode(trader3.getTraderCode()).isEmpty()) {traderRepo.save(trader3);}

        Order order1=new Order("Ord1","MARKET",LocalDateTime.of(2025, Month.JANUARY,1,10,10),20L,
                BigDecimal.valueOf(10.0),"BUY",true,stockRepo.findStockByCode(stock2.getStockCode()).get(),traderRepo.findTraderByCode(trader1.getTraderCode()).get());
        Order order2=new Order("Ord2","MARKET",LocalDateTime.of(2025, Month.JANUARY,1,10,15),45L,
                BigDecimal.valueOf(10.0),"SELL",true,stockRepo.findStockByCode(stock2.getStockCode()).get(),traderRepo.findTraderByCode(trader1.getTraderCode()).get());
        Order order3=new Order("Ord3","MARKET",LocalDateTime.of(2025, Month.JANUARY,1,10,20),30L,
                BigDecimal.valueOf(10.0),"SELL",true,stockRepo.findStockByCode(stock2.getStockCode()).get(),traderRepo.findTraderByCode(trader1.getTraderCode()).get());
        Order order4=new Order("Ord4","MARKET",LocalDateTime.of(2025, Month.JANUARY,1,10,25),30L,
                BigDecimal.valueOf(10.0),"BUY",true,stockRepo.findStockByCode(stock2.getStockCode()).get(),traderRepo.findTraderByCode(trader1.getTraderCode()).get());
        Order order5=new Order("Ord5","MARKET",LocalDateTime.of(2025, Month.JANUARY,1,10,30),20L,
                BigDecimal.valueOf(10.0),"SELL",true,stockRepo.findStockByCode(stock2.getStockCode()).get(),traderRepo.findTraderByCode(trader1.getTraderCode()).get());

        if(orderRepo.findById(order1.getOrderId()).isEmpty()) {orderRepo.saveAndFlush(order1);}
        if(orderRepo.findById(order2.getOrderId()).isEmpty()) {orderRepo.saveAndFlush(order2);}
        if(orderRepo.findById(order3.getOrderId()).isEmpty()) {orderRepo.saveAndFlush(order3);}
        if(orderRepo.findById(order4.getOrderId()).isEmpty()) {orderRepo.saveAndFlush(order4);}
        if(orderRepo.findById(order5.getOrderId()).isEmpty()) {orderRepo.saveAndFlush(order5);}

    }

}
