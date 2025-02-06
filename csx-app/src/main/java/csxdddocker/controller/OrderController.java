package csxdddocker.controller;

import csxdddocker.dto.OrderRequestDTO;
import csxdddocker.entity.Order;
import csxdddocker.entity.OrderStockTraderListDTO;
import csxdddocker.entity.Stock;
import csxdddocker.entity.Trader;
import csxdddocker.repository.OrderRepository;
import csxdddocker.repository.StockRepository;
import csxdddocker.repository.TraderRepository;
import csxdddocker.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private TraderRepository traderRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @GetMapping()
    ///public ResponseEntity<List<Order>> getAll() {
    public ResponseEntity<List<OrderStockTraderListDTO>> getAll() {
        try {
            List<OrderStockTraderListDTO> objList;
            objList = orderService.getAll();
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }
/* REVIEW BIEN
    @GetMapping("/ordersstockstraders")
    public ResponseEntity<List<OrdersStocksTradersDTO>> getListAll() {
        try {
            List<OrdersStocksTradersDTO> objList;
            objList = orderService.listOrdersStocksTraders();

            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERRR+++++++++++  "+e.getMessage());
            //return null
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }

    @GetMapping("/orderstransactions")
    public ResponseEntity<List<OrdersTransactionsDTO>> getAllOrderTransactions() {
        try {
            List<OrdersTransactionsDTO> objList;
            objList = orderService.listOrdersTransactions();

            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERRR+++++++++++  "+e.getMessage());
            //return null
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }

    @GetMapping("/ordersstockstradersbystockcode/{code}")
    public ResponseEntity<List<OrdersStocksTradersDTO>> getOrdersStocksTradersStockCode(@PathVariable("code") String code) {
        try {
            List<OrdersStocksTradersDTO> objList;
            objList = orderService.listOrdersStocksTradersByStockCode(code);
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERRR+++++++++++  "+e.getMessage());
            //return null
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }

    @GetMapping("/orderstransactions/{code}")
    public ResponseEntity<List<OrdersTransactionsDTO>> getAllOrderTransactionsByStockCode(@PathVariable("code") String code) {
        try {
            List<OrdersTransactionsDTO> objList;
            objList = orderService.listOrdersTransactionsByStockCode(code);

            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERRR+++++++++++  "+e.getMessage());
            //return null
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable("id") String id) {
        Order obj = orderService.getById(id);
        if (obj != null) {
            return new ResponseEntity<Order>(obj, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* LATER
    @GetMapping("/ordersbystock/{id}")
    public ResponseEntity<List<Order>> getOrdersActiveByStock(@PathVariable("id") long id) {
        try {
            List<Order> objList;
            objList = orderService.getAllActiveOrdersByStock(id);
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }
*/


    @PostMapping()
    public ResponseEntity<Order> create(@RequestBody OrderRequestDTO objIn) {

        Order order=new Order();
        order.setOrderId(UUID.randomUUID().toString()); //* to be changed with Order entity orderId
        order.setOrderTime(objIn.orderTime());
        order.setOrderType(objIn.orderType());
        order.setSide(objIn.side());

        order.setSize(objIn.size());
        order.setPrice(objIn.price());
        order.setIsactive(true);

log.info("\n\n\n\n *************** "+objIn.stockCode());
        Optional<Stock> stock=stockRepository.findStockByCode(objIn.stockCode());

        if(stock.isPresent()){
            order.setOrder_Stock(stock.get());
        }
        else{
            throw new RuntimeException("Stock not found");
        }
        Optional<Trader> trader=traderRepository.findTraderByCode(objIn.traderCode());
        if(trader.isPresent()){
            order.setOrder_Trader(trader.get());
        }
        else{
            throw new RuntimeException("Trader not found");
        }

        Order obj = orderService.create(order);
        if (obj != null) {
            return new ResponseEntity<>(obj, new HttpHeaders(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable(value = "id") String id, @RequestBody Order objIn) {
        try {
            Order obj = orderService.update(id, objIn);
            if (obj != null) {
                return new ResponseEntity<>(obj, new HttpHeaders(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
        boolean resp = orderService.deleteById(id);
        if (resp == true) {
            return new ResponseEntity<>(true, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }

}