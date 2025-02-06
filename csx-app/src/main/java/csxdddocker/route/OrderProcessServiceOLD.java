package csxdddocker.route;

import csxdddocker.entity.Order;
import csxdddocker.entity.Transaction;
import csxdddocker.service.OrderService;
import csxdddocker.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//@Service
@Slf4j
public class OrderProcessServiceOLD {

    //@Autowired
    private OrderService orderService;
    ///@Autowired
    private TransactionService transactionService;
    private ArrayList<Transaction> transactions=new ArrayList<>();

    /* This function process */
    public void processOrder(ArrayList<Order> allOrders) {
        ArrayList<Order> contraBuyOrders = allOrders.stream().filter(p -> p.getSide().trim().equalsIgnoreCase("BUY")).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Order> contraSellOrders = allOrders.stream().filter(p -> p.getSide().trim().equalsIgnoreCase("SELL")).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Order> ordersToExecute = new ArrayList<>();
        //* find Market Prices
        BigDecimal buyMarketPrice=calculateMarketPrice(contraBuyOrders);
        BigDecimal sellMarketPrice=calculateMarketPrice(contraSellOrders);
        log.info("\n\n ==) OrderProcessService-processOrder(), \n buyMarketPrice = "+buyMarketPrice+" \t sellMarketPrice ="+sellMarketPrice);

        BigDecimal tradePrice;
        Long stockSize = null;
        BigDecimal tradeAmount;
        LocalDateTime tradeDateTime = null;
        int allOrdersLoopCount=0;

        //* start matching For loop
        for (Order requestOrder : allOrders) {

            //* 1.  requestOrder side = BUY vs contraSellOrders
            if (requestOrder.getSide().equals("BUY")) {
                tradePrice = BigDecimal.ZERO; //* review null or 0
                stockSize = null;
                tradeAmount= BigDecimal.ZERO; //* review null or 0
                tradeDateTime = null;
                int countraSellOrdersLoopCount=0;

                for (Order contraSellOrder : contraSellOrders) {
                   if(requestOrder.getSize()>0 && requestOrder.getIsactive() && contraSellOrder.getSize()>0 && contraSellOrder.getIsactive()){
                        if (requestOrder.getOrder_Stock().getStockCode().equalsIgnoreCase(contraSellOrder.getOrder_Stock().getStockCode())) {
                            BigDecimal requestOrderPrice = requestOrder.getPrice();
                            BigDecimal contraOrderPrice = contraSellOrder.getPrice();
                            String requestOrderType = requestOrder.getOrderType();
                            String contraOrderType = contraSellOrder.getOrderType();

                            if ((requestOrderPrice.compareTo(contraOrderPrice) > 0) || (requestOrderPrice.compareTo(contraOrderPrice) == 0)) {
                                //*
                                if (requestOrderType.equals("LIMIT") && (contraOrderType.equals("LIMIT"))) {
                                    if ((requestOrderPrice.compareTo(sellMarketPrice) > 0) || (requestOrderPrice.compareTo(sellMarketPrice) == 0)) {
                                        tradePrice = requestOrderPrice.add(contraOrderPrice).divide(new BigDecimal(2), RoundingMode.UP); //*
                                    }
                                    System.out.println("BAAYYOOOO ##########------- LIMIT=LIMIT ="+tradePrice);
                                } else if (requestOrderType.equals("MARKET") && (contraOrderType.equals("MARKET"))) {
                                    tradePrice = sellMarketPrice;
                                    System.out.println("BAAYYOOOO ##########------- MARKET=MARKET ="+tradePrice);
                                } else if (requestOrderType.equals("MARKET") && (contraOrderType.equals("LIMIT"))) {
                                    if ((contraOrderPrice.compareTo(sellMarketPrice) > 0) || (contraOrderPrice.compareTo(sellMarketPrice) == 0)) {
                                        tradePrice = ((contraOrderPrice.add(sellMarketPrice).divide(new BigDecimal(2), RoundingMode.UP))); //*
                                        System.out.println("BAAYYOOOO ##########------- LIMIT M>=MARKET ="+tradePrice);
                                    }
                                } else if ((requestOrderType.equals("LIMIT")) && contraOrderType.equals("MARKET")) {
                                    if ((requestOrderPrice.compareTo(sellMarketPrice) > 0) || (requestOrderPrice.compareTo(sellMarketPrice) == 0)) {
                                        tradePrice = ((requestOrderPrice.add(sellMarketPrice).divide(new BigDecimal(2), RoundingMode.UP))); //*
                                        System.out.println("BAAYYOOOO ##########------- LIMIT R>=MARKET ="+tradePrice);
                                    }
                                }
                                if (requestOrder.getSize().longValue() == contraSellOrder.getSize().longValue()) {
                                    stockSize = requestOrder.getSize();
                                    requestOrder.setSize(0L);
                                    requestOrder.setIsactive(false);
                                    allOrders.get(allOrdersLoopCount).setSize(0L);
                                    allOrders.get(allOrdersLoopCount).setIsactive(false);
                                    contraSellOrder.setSize(0L);
                                    contraSellOrder.setIsactive(false);
                                    contraSellOrders.get(countraSellOrdersLoopCount).setSize(0L);
                                    contraSellOrders.get(countraSellOrdersLoopCount).setIsactive(false);
                                    tradeDateTime = LocalDateTime.now(); //Timestamp(System.currentTimeMillis());
                                    tradeAmount = tradePrice.multiply(BigDecimal.valueOf(stockSize));
                                } else if (requestOrder.getSize() > contraSellOrder.getSize()) { //OKOKOK
                                    requestOrder.setSize(requestOrder.getSize() - contraSellOrder.getSize());
                                    allOrders.get(allOrdersLoopCount).setSize(requestOrder.getSize());
                                    stockSize = contraSellOrder.getSize();
                                    contraSellOrder.setSize(0L);
                                    contraSellOrder.setIsactive(false);
                                    contraSellOrders.get(countraSellOrdersLoopCount).setSize(0L);
                                    contraSellOrders.get(countraSellOrdersLoopCount).setIsactive(false);
                                    tradeDateTime = LocalDateTime.now(); //Timestamp(System.currentTimeMillis());
                                    tradeAmount = tradePrice.multiply(BigDecimal.valueOf(stockSize));
                                } else if (requestOrder.getSize() < contraSellOrder.getSize()) {
                                    contraSellOrder.setSize(contraSellOrder.getSize() - requestOrder.getSize());
                                    contraSellOrders.get(countraSellOrdersLoopCount).setSize(contraSellOrder.getSize());
                                    stockSize = requestOrder.getSize();
                                    requestOrder.setSize(0L);
                                    requestOrder.setIsactive(false);
                                    allOrders.get(allOrdersLoopCount).setSize(0L);
                                    allOrders.get(allOrdersLoopCount).setIsactive(false);
                                    tradeDateTime = LocalDateTime.now(); //Timestamp(System.currentTimeMillis());
                                    tradeAmount = tradePrice.multiply(BigDecimal.valueOf(stockSize));
                                }
                            }
                        }
                    }
                   //* add Orders to execute
                   ordersToExecute.add(requestOrder);
                   ordersToExecute.add(contraSellOrder);
                   //* add Trade to save
                   addTransaction("BUY",requestOrder.getOrder_Stock().getStockId(), requestOrder.getOrder_Stock().getStockCode(), tradeDateTime, tradePrice, stockSize, requestOrder.getOrderId(), requestOrder.getOrder_Trader().getTraderId(), requestOrder.getOrder_Trader().getTraderCode(), contraSellOrder.getOrderId(), contraSellOrder.getOrder_Trader().getTraderId(), contraSellOrder.getOrder_Trader().getTraderCode(), tradeAmount, sellMarketPrice);
                   countraSellOrdersLoopCount+=1;
                }
            }

            //* 2.  requestOrder side = SELL vs contraBuyOrders
            if (requestOrder.getSide().equals("SELL")) {
                tradePrice = BigDecimal.ZERO; //* review null or 0
                stockSize = null;
                tradeAmount= BigDecimal.ZERO; //* review null or 0
                tradeDateTime = null;
                int countraBuyOrdersLoopCount=0;

                for (Order contraBuyOrder : contraBuyOrders) {
                    if(requestOrder.getSize()>0 && requestOrder.getIsactive() && contraBuyOrder.getSize()>0 && contraBuyOrder.getIsactive()){
                        if (requestOrder.getOrder_Stock().getStockCode().equalsIgnoreCase(contraBuyOrder.getOrder_Stock().getStockCode())) {
                            BigDecimal requestOrderPrice = requestOrder.getPrice();
                            BigDecimal contraOrderPrice = contraBuyOrder.getPrice();
                            String requestOrderType = requestOrder.getOrderType();
                            String contraOrderType = contraBuyOrder.getOrderType();

                            if ((requestOrderPrice.compareTo(contraOrderPrice) <= 0)) {
                                //*
                                if (requestOrderType.equals("LIMIT") && (contraOrderType.equals("LIMIT"))) {
                                    if ((requestOrderPrice.compareTo(buyMarketPrice) > 0) || (requestOrderPrice.compareTo(buyMarketPrice) == 0)) {
                                        tradePrice = requestOrderPrice.add(contraOrderPrice).divide(new BigDecimal(2), RoundingMode.UP); //*
                                    }
                                    log.info("SELLOOO ##########------- LIMIT=LIMIT ="+tradePrice);
                                } else if (requestOrderType.equals("MARKET") && (contraOrderType.equals("MARKET"))) {
                                    tradePrice = buyMarketPrice;
                                    log.info("SELLOOO ##########------- MARKET=MARKET ="+tradePrice);
                                } else if (requestOrderType.equals("MARKET") && (contraOrderType.equals("LIMIT"))) {
                                    log.info("SELLOOO ##########------???- LIMIT M>=MARKET");
                                    if ((contraOrderPrice.compareTo(buyMarketPrice) >= 0) ){//|| (contraOrderPrice.compareTo(buyMarketPrice) == 0)) {
                                        tradePrice = ((contraOrderPrice.add(buyMarketPrice).divide(new BigDecimal(2), RoundingMode.UP))); //*
                                        log.info("SELLOOO ##########------- LIMIT M>=MARKET ="+tradePrice);
                                    }
                                    else{
                                        log.info("SELLOOO ##########----???");
                                    }
                                } else if ((requestOrderType.equals("LIMIT")) && contraOrderType.equals("MARKET")) {
                                    if ((requestOrderPrice.compareTo(buyMarketPrice) > 0) || (requestOrderPrice.compareTo(buyMarketPrice) == 0)) {
                                        tradePrice = ((requestOrderPrice.add(buyMarketPrice).divide(new BigDecimal(2), RoundingMode.UP))); //*
                                        log.info("SELLOOO ##########------- LIMIT R>=MARKET ="+tradePrice);
                                    }
                                }
                                else{
                                    log.info("SELLOOO ##########------- NOT FOUND");
                                }
                                if (requestOrder.getSize().longValue() == contraBuyOrder.getSize().longValue()) {
                                    stockSize = requestOrder.getSize();
                                    requestOrder.setSize(0L);
                                    requestOrder.setIsactive(false);
                                    allOrders.get(allOrdersLoopCount).setSize(0L);
                                    allOrders.get(allOrdersLoopCount).setIsactive(false);
                                    contraBuyOrder.setSize(0L);
                                    contraBuyOrder.setIsactive(false);
                                    contraBuyOrders.get(countraBuyOrdersLoopCount).setSize(0L);
                                    contraBuyOrders.get(countraBuyOrdersLoopCount).setIsactive(false);
                                    tradeDateTime = LocalDateTime.now(); //Timestamp(System.currentTimeMillis());
                                    tradeAmount = tradePrice.multiply(BigDecimal.valueOf(stockSize));
                                } else if (requestOrder.getSize() > contraBuyOrder.getSize()) { //OKOKOK
                                    requestOrder.setSize(requestOrder.getSize() - contraBuyOrder.getSize());
                                    allOrders.get(allOrdersLoopCount).setSize(requestOrder.getSize());
                                    stockSize = contraBuyOrder.getSize();
                                    contraBuyOrder.setSize(0L);
                                    contraBuyOrder.setIsactive(false);
                                    contraBuyOrders.get(countraBuyOrdersLoopCount).setSize(0L);
                                    contraBuyOrders.get(countraBuyOrdersLoopCount).setIsactive(false);
                                    tradeDateTime = LocalDateTime.now(); //Timestamp(System.currentTimeMillis());
                                    tradeAmount = tradePrice.multiply(BigDecimal.valueOf(stockSize));
                                } else { //else =requestOrder.getSize() < contraBuyOrder.getSize()
                                    contraBuyOrder.setSize(contraBuyOrder.getSize() - requestOrder.getSize());
                                    contraBuyOrders.get(countraBuyOrdersLoopCount).setSize(contraBuyOrder.getSize());
                                    stockSize = requestOrder.getSize();
                                    requestOrder.setSize(0L);
                                    requestOrder.setIsactive(false);
                                    allOrders.get(allOrdersLoopCount).setSize(0L);
                                    allOrders.get(allOrdersLoopCount).setIsactive(false);
                                    tradeDateTime = LocalDateTime.now(); //Timestamp(System.currentTimeMillis());
                                    tradeAmount = tradePrice.multiply(BigDecimal.valueOf(stockSize));
                                }
                            }
                        }
                    }
                    //* add Orders to execute
                    ordersToExecute.add(requestOrder);
                    ordersToExecute.add(contraBuyOrder);
                    //* add Trade to save
                    // stockId, stockCode, tradeTime, tradePrice, tradeSize,buyerOrderId, buyerId, buyerCode, sellerOrderId, sellerId, sellerCode, tradeAmount, marketPrice
                    addTransaction("SELL",requestOrder.getOrder_Stock().getStockId(), requestOrder.getOrder_Stock().getStockCode(), tradeDateTime, tradePrice, stockSize, requestOrder.getOrderId(), requestOrder.getOrder_Trader().getTraderId(), requestOrder.getOrder_Trader().getTraderCode(), contraBuyOrder.getOrderId(), contraBuyOrder.getOrder_Trader().getTraderId(), contraBuyOrder.getOrder_Trader().getTraderCode(), tradeAmount, buyMarketPrice);
                    countraBuyOrdersLoopCount+=1;
                }
            }
            allOrdersLoopCount+=1;
        } //* end matching For loop

        //* update with executedOrders
         orderService.updateProcessedOrderAll(ordersToExecute);
        //* save executedTrades
        boolean check=false;
    /*    for(Transaction trans:transactions){
            if (trans. && transactions.get(0).getTransId() == null) { //* Make sure only new Transactions(no trans_Id) are saved
                check=true;
            }
        } */
        if (!transactions.isEmpty() && transactions.get(0).getTransId() == null) { //* Make sure only new Transactions(no trans_Id) are saved
           List<Transaction> listTx= transactionService.saveAll(transactions);
        }else {
            log.info("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        }

            /* uncomment Later
            System.out.println("\n\nAll Orders apres== ");
            for (Order order : allOrders) {
                System.out.println(">>>>"+order.getIsactive()+" \t-Pce: "+order.getPrice()+" \t- Sze= "+order.getSize()+" \t- "+order.getSide()+" \t- "+order.getStockCode()+" \t- "+order.getTraderCode());
            }
            System.out.println("\n\nOrders to execute== ");
            for (Order order : ordersToExecute) {
                System.out.println("<<<<"+order.getPrice()+" - "+order.getSide()+" - "+order.getStockCode()+" - "+order.getTraderCode());
            }
            System.out.println("\n\nTrades executed== ");
            for (Transaction trade : transactions) {
                System.out.println("@@@@"+"\n-stcode= "+trade.getStockCode()+" \t-time= "+trade.getTradeTime()+" \t-pc="+trade.getTradePrice()+" \t- sz= "+trade.getTradeSize()+" \t- by= "+trade.getBuyerCode()+" \t- slr= "+trade.getSellerCode()+" \t- amt= "+trade.getTradeAmount());
            }
            */
        }
        /* This function generates a txn object to provide information on the orders consumed  while processing/matching any incoming order. */
          private List<Transaction> addTransaction(String tradeType,String stockId, String stockCode, LocalDateTime tradeTime, BigDecimal tradePrice, Long tradeSize, String buyerOrderId, String buyerId, String buyerCode, String sellerOrderId, String sellerId, String sellerCode, BigDecimal tradeAmount, BigDecimal marketPrice) {
            Transaction transaction = new Transaction();
            transaction.setStockId(stockId);
            transaction.setStockCode(stockCode);
            transaction.setTradeTime(tradeTime);
            transaction.setTradePrice(tradePrice);
            transaction.setTradeSize(tradeSize);
            if(tradeType.equals("BUY")) {
                transaction.setBuyerOrderId(buyerOrderId);
                transaction.setBuyerId(buyerId);
                transaction.setBuyerCode(buyerCode);
                transaction.setSellerOrderId(sellerOrderId);
                transaction.setSellerId(sellerId);
                transaction.setSellerCode(sellerCode);
            }
            else if(tradeType.equals("SELL")){
                transaction.setBuyerOrderId(sellerOrderId);
                transaction.setBuyerId(sellerId);
                transaction.setBuyerCode(sellerCode);
                transaction.setSellerOrderId(buyerOrderId);
                transaction.setSellerId(buyerId);
                transaction.setSellerCode(buyerCode);
            }

            transaction.setTradeAmount(tradeAmount);
            transaction.setMarketPrice(marketPrice);

            if(transaction.getStockId()!=null && transaction.getStockCode()!=null && transaction.getTradeTime()!=null && transaction.getTradePrice()!=null && transaction.getTradeSize()!=null
                    && transaction.getBuyerOrderId()!=null && transaction.getBuyerId()!=null && transaction.getBuyerCode()!=null && transaction.getSellerOrderId()!=null
                    && transaction.getSellerId()!=null && transaction.getSellerCode()!=null && transaction.getTradeAmount()!=null && transaction.getMarketPrice()!=null)
            {
                transactions.add(transaction);
                return transactions;
            }
            else{
                return null;
            }
        }

        private BigDecimal calculateMarketPrice(ArrayList<Order> orders){
            long count=orders.size();

            BigDecimal sum = BigDecimal.ZERO;
            for (Order order : orders) {
                sum = sum.add(order.getPrice());
            }

            BigDecimal avgMarketPrice;
            if(count>0){
                avgMarketPrice=sum.divide(BigDecimal.valueOf(count) , RoundingMode.UP);
            }
            else{
                avgMarketPrice=null;
            }

            return avgMarketPrice;
        }

}
