package csxdddocker.service;

import csxdddocker.entity.Transaction;
import csxdddocker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> getAll(){
        List<Transaction> objList=transactionRepository.findAll();
        if(!objList.isEmpty()) {
            return objList;
        }
        else {
            return new ArrayList<Transaction>();
        }
    }

    public List<Transaction> saveAll(List<Transaction> transactions){
        List<Transaction> objList=transactionRepository.saveAllAndFlush(transactions);
        if(!objList.isEmpty()) {
            System.out.println("\n\nOOOOOOOOOOOOXXXXXXXXXXXXXXXX "+objList.size());
            return objList;
        }
        else {
            System.out.println("\n\n MNMNMUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLK ");
            return new ArrayList<Transaction>();
        }


    }
}
