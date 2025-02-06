package csxdddocker.service;

import csxdddocker.entity.Order;
import csxdddocker.entity.OrderStockTraderListDTO;
import csxdddocker.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<OrderStockTraderListDTO> getAll(){
        List<Order> orders=orderRepository.findAll();
        List<OrderStockTraderListDTO> order_stock_trader_dtoList=new ArrayList<>();
        OrderStockTraderListDTO order_stock_trader_dto;
        for(Order ord:orders){
            order_stock_trader_dto=new OrderStockTraderListDTO();
            order_stock_trader_dto.setOrderId(ord.getOrderId());
            order_stock_trader_dto.setOrderTime(ord.getOrderTime());
            order_stock_trader_dto.setOrderType(ord.getOrderType());
            order_stock_trader_dto.setSide(ord.getSide());
            order_stock_trader_dto.setPrice(ord.getPrice());
            order_stock_trader_dto.setIsactive(ord.getIsactive());
            order_stock_trader_dto.setStock(ord.getOrder_Stock());
            order_stock_trader_dto.setTrader(ord.getOrder_Trader());
            order_stock_trader_dtoList.add(order_stock_trader_dto);
        }

        if(!order_stock_trader_dtoList.isEmpty()) {
            return order_stock_trader_dtoList;
        }
        else {
            return new ArrayList<>();
        }
    }

    public Order getById(String id) {
        Optional<Order> obj=orderRepository.findById(id);
        if(obj.isPresent()) {
            return obj.get();
        }
        else {
            return null;
        }
    }

    public Order create(Order objIn) {
        return orderRepository.save(objIn);
    }

    //* update processed orders
    public List<Order> updateProcessedOrderAll(List<Order> ordersIn){

        ArrayList<Order> savedOrders= new ArrayList<>();
        for(Order o: ordersIn){
            Order oo=new Order();
            oo.setOrderId(o.getOrderId());
            oo.setIsactive(o.getIsactive());
            oo.setOrderTime(o.getOrderTime());
            oo.setOrderType(o.getOrderType());
            oo.setSide(o.getSide());
            oo.setPrice(o.getPrice());
            oo.setSize(o.getSize());
            oo.setOrder_Stock(o.getOrder_Stock());
            oo.setOrder_Trader(o.getOrder_Trader());
            savedOrders.add(oo);
        }
        orderRepository.saveAllAndFlush(savedOrders);
        return ordersIn; //* change here
    }

    public Order update(String id, Order objIn) {
        if(id != null) {
            Optional<Order> obj=orderRepository.findById(id);
            if(obj.isPresent()) {
                Order newObj=obj.get();
                newObj.setSide(objIn.getSide());
                newObj.setOrderTime(objIn.getOrderTime());
                newObj.setPrice(objIn.getPrice());
                newObj.setSize(objIn.getSize());
                newObj.setIsactive(objIn.getIsactive());
              ///  newObj.setTraderId(objIn.getTraderId());
              ///  newObj.setTraderCode(objIn.getTraderCode());
              ///  newObj.setTraderCode(objIn.getTraderCode());
              ///  newObj.setTraderCode(objIn.getTraderCode());
               // newObj.setTrader_id(objIn.getTrader_id());
               // newObj.setTrader_id(objIn.getTrader_id());
                //newObj.setTrader(objIn.getTrader());
                //newObj.setTrader(objIn.getTrader());
                newObj=orderRepository.save( newObj);
                return newObj;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public Boolean deleteById(String id) {
        Optional<Order>obj=orderRepository.findById(id);
        if(obj.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    /*  LATER
    public List<Order> getAllActiveOrdersByStock(Long stockID){
        List<Order> objList=orderRepository.getAllActiveOrdersByStock(stockID);
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<Order>();
        }
    }
*/
    /* REVIEW BIEN
    public List<OrdersStocksTradersDTO> listOrdersStocksTraders(){
        List<OrdersStocksTradersDTO> objList=orderRepository.listOrdersStocksTraders();
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<OrdersStocksTradersDTO>();
        }
    }

    public List<OrdersTransactionsDTO> listOrdersTransactions(){
        List<OrdersTransactionsDTO> objList=orderRepository.listOrdersTransactions();
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<OrdersTransactionsDTO>();
        }
    }

    public List<OrdersTransactionsDTO> listOrdersTransactionsByStockCode(String code){
        List<OrdersTransactionsDTO> objList=orderRepository.listOrdersTransactionsByStockCode(code);
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<OrdersTransactionsDTO>();
        }
    }

    public List<OrdersStocksTradersDTO> listOrdersStocksTradersByStockCode(String code){
        List<OrdersStocksTradersDTO> objList=orderRepository.listOrdersStocksTradersByStockCode(code);
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<OrdersStocksTradersDTO>();
        }
    }
*/


}
