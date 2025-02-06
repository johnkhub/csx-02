package csxdddocker.controller;

import csxdddocker.entity.Transaction;
import csxdddocker.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @GetMapping()
    public ResponseEntity<List<Transaction>> getAll() {
        try {
            List<Transaction> objList;
            objList = transactionService.getAll();
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }
}
