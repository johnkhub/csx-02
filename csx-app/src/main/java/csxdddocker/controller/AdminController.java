package csxdddocker.controller;

import csxdddocker.repository.OrderRepository;
import csxdddocker.repository.StockRepository;
import csxdddocker.repository.TraderRepository;
import csxdddocker.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping(value = "/admin")
    @Slf4j
    public class AdminController {

        @Autowired
        private OrderRepository orderRepo;
        @Autowired
        private StockRepository stockRepo;
        @Autowired
        private TraderRepository traderRepo;
        @Autowired
        private TransactionRepository transactionRepo;

        @GetMapping("/resetdb")
        public String resetDB(){
            orderRepo.deleteAll();
            transactionRepo.deleteAll();
            //stockRepo.deleteAll();
            //transactionRepo.deleteAll();
            return "All deleted";
        }

    }

