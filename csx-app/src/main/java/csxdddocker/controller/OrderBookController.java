package csxdddocker.controller;

import csxdddocker.route.OrderProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/order")
@Slf4j
public class OrderBookController {

    @Autowired
    private OrderProcessService bookService;
/*
    @RequestMapping(method = POST, value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> processOrder(@RequestBody Order addOrderRequest,
                                                HttpServletRequest request) throws JsonProcessingException,
            CustomException, UnsupportedEncodingException, GeneralSecurityException {
        //==ProcessedOrderResponse response = bookService.processOrder(addOrderRequest);
        //==return new ResponseEntity<>(new ApiResponseSuccess(response, "Order processed successfully."), HttpStatus.OK);
        return null;
    }
*/
    /*
    @RequestMapping(method = POST, value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addOrder(@RequestBody Order addOrderRequest,
                                                HttpServletRequest request) throws JsonProcessingException,
            CustomException, UnsupportedEncodingException, GeneralSecurityException {
        ProcessedOrderResponse response = bookService.processOrder(addOrderRequest);
        return new ResponseEntity<>(new ApiResponseSuccess(response, "Order processed successfully."), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse>fetchAllOrders(@RequestParam(name = "active", required = false, defaultValue = "true") Boolean active,
                                                             HttpServletRequest request) throws JsonProcessingException,
            CustomException, UnsupportedEncodingException, GeneralSecurityException {

        List<Order> response = bookService.getAllOrders(active);
        return new ResponseEntity<>(new ApiResponseSuccess(response, "Order Book fetched successfully."), HttpStatus.OK);
    }
    */
}
