package csxdddocker.service;

import csxdddocker.entity.*;
import csxdddocker.repository.StockRepository;
import csxdddocker.repository.StockSummariesAllRepository;
import csxdddocker.repository.StockSummariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    StockSummariesRepository stockSummariesRepository;
    @Autowired
    StockSummariesAllRepository stockSummariesAllRepository;

    public List<Stock> getAll(){
        List<Stock> objList=stockRepository.findAll();
        if(!objList.isEmpty()) {
            return objList;
        }
        else {
            return new ArrayList<Stock>();
        }
    }

    public Stock getById(String id) {
        Optional<Stock> obj=stockRepository.findById(id);
        if(obj.isPresent()) {
            return obj.get();
        }
        else {
            return null;
        }
    }


    public Stock findStockByCode(String stockCode) {
        Optional<Stock> obj=stockRepository.findStockByCode(stockCode);
        if(obj.isPresent()) {
            return obj.get();
        }
        else {
            return null;
        }
    }

    public Stock create(Stock objIn) {
        Stock newObj=stockRepository.save(objIn);
        return newObj;
    }

    public Stock update(String id, Stock objIn) {
        if(id != null) {
            Optional<Stock> obj=stockRepository.findById(id);
            if(obj.isPresent()) {
                Stock newObj=obj.get();
                newObj.setStockCode(objIn.getStockCode());
                newObj.setStockDescription(objIn.getStockDescription());
                newObj=stockRepository.save( newObj);
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
        Optional<Stock>obj=stockRepository.findById(id);
        if(obj.isPresent()) {
            stockRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    public List<StockSummaries> listStockSummariesByStockCode(String code1,String code2,String code3,String code4,String code5,String code6,String code7){
        List<StockSummaries> objList= stockSummariesRepository.listStockSummariesByStockCode(code1,code2,code3,code4,code5,code6,code7);
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<>();
        }
    }
//List<StockSummariesAll>
    public List<StockSummariesAll> insertStockSummariesAll(String code1,String code2,String code3,String code4,String code5,String code6,String code7,String code8,String code9,String code10,String code11){
        List<StockSummariesAll>objList= stockSummariesAllRepository.insertStockSummariesAll(code1,code2,code3,code4,code5,code6,code7,code8,code9,code10,code11);
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<StockSummariesAll>();
        }

    }


    public List<StockSummaries> listStockSummaries() {
        List<StockSummaries> objList= stockSummariesRepository.findAll();
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<StockSummaries>();
        }
    }

    public List<StockSummariesAll> listStockSummariesAll() {
        List<StockSummariesAll> objList= stockSummariesAllRepository.findAll();
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<StockSummariesAll>();
        }
    }

    public List<StockSummariesAll> findGainerStocks(){
        List<StockSummariesAll> objList= stockSummariesAllRepository.findGainerStocks();
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<StockSummariesAll>();
        }
    }

    public List<StockSummariesAll> findLoserStocks(){
        List<StockSummariesAll> objList= stockSummariesAllRepository.findLoserStocks();
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<StockSummariesAll> findActiveStocks(){
        List<StockSummariesAll> objList= stockSummariesAllRepository.findLoserStocks();
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<StockSummariesAll> findSummariesByCode(String code){
        List<StockSummariesAll> objList= stockSummariesAllRepository.findSummariesByCode(code);
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<StockBidAsk> listStockBidAskByStockCode(String stockcode,String orderside){
       /* List<StockBidAskDTO> objList= stockSummariesRepository.listStockBidAskByStockCode(stockcode,orderside);
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<>();
        }
        */
        List<StockBidAsk> objList= stockSummariesRepository.listStockBidAskByStockCode(stockcode,orderside);
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<>();
        }

    }
}
