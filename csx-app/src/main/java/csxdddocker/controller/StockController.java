package csxdddocker.controller;

import csxdddocker.entity.*;
import csxdddocker.repository.StockBidAskRepository;
import csxdddocker.repository.StockSummariesAllRepository;
import csxdddocker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

//* OKOK @CrossOrigin(origins = "http://localhost")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService stockService;
    @Autowired
    StockBidAskRepository stockBidAskRepository;
    @Autowired
    StockSummariesAllRepository stockSummariesAllRepository;


    public static String stkcode;

    @GetMapping()
    public ResponseEntity<List<Stock>> getAll() {
        try {
            List<Stock> objList = new ArrayList<Stock>();
            objList = stockService.getAll();
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getById(@PathVariable("id") String id) {
        Stock obj = stockService.getById(id);
        if (obj != null) {
            return new ResponseEntity<Stock>(obj, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bycode/{code}")
    public ResponseEntity<Stock> getByCode(@PathVariable("code") String code) {
        Stock obj = stockService.findStockByCode(code);
        if (obj != null) {
            return new ResponseEntity<Stock>(obj, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<Stock>  create(@RequestBody Stock objIn) {
        Stock obj = stockService.create(objIn);
        if (obj != null) {
            return new ResponseEntity<>(obj, new HttpHeaders(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> update(@PathVariable(value = "id") String id, @RequestBody Stock objIn) {
        try {
            Stock obj = stockService.update(id, objIn);
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
        boolean resp = stockService.deleteById(id);
        if (resp == true) {
            return new ResponseEntity<>(true, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }

    //*
    @GetMapping("/stocksummaries/{code}")
    public ResponseEntity<List<StockSummaries>> getStockSummariesByStockCode(@PathVariable("code") String code){
        //*
        try {
            List<StockSummaries> objList = stockService.listStockSummariesByStockCode(code,code,code,code,code,code,code);

        } catch (Exception e) {
            System.out.println("ERRR+++++++++++ BA "+e.getMessage());
        }
        //*
        try {
            List<StockSummaries> objList;
            objList = stockService.listStockSummaries();
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }

    @GetMapping("/stocksummaries/{mode}/{detail}")
    public ResponseEntity<List<StockSummariesAll>> getStockSummariesByStockCode(@PathVariable("mode") String mode,@PathVariable("detail") String detail) {

      //---  this.stockSummariesAllRepository.deleteAll();
        //*
        List<StockSummariesAll> objList= new ArrayList<>();
        String codes[];
        if (mode.equals("_single_stock")) {
           // codes = new String[1];
          //  codes[0] = detail;
          //  for (String code : codes) {
                try {
                    stockService.insertStockSummariesAll(detail, detail, detail, detail, detail, detail, detail, detail, detail, detail, detail);

                } catch (Exception e) {
                    System.out.println("ERRR+++++++++++S  " + e.getMessage());
                }
            objList =  stockService.findSummariesByCode(detail);

        } else if (mode.equals("_all_stocks")) {
            List<Stock> stockList = stockService.getAll();
            if (objList.isEmpty()!=true) {
                String codess[] = new String[stockList.size()];
                int ii = 0;
                for (Stock st : stockList) {
                    codess[ii] = st.getStockCode();
                    ii+=1;
                }
                //*
                for (String code : codess) {
                    System.out.println("CODEDEEEE==="+code);
                    try {
                        List<StockSummariesAll> objListt = stockService.insertStockSummariesAll(code, code, code, code, code, code, code, code, code, code, code);
                   } catch (Exception e) {
                      System.out.println("ERRR+++++++++++XXXX-  " + e.getMessage());
                   }
                }
            }
        }

        if (detail.equals("_active_stocks")) {
            System.out.println("ACTTT");
            objList = stockService.findActiveStocks();
        } else if (detail.equals("_loser_stocks")) {
            System.out.println("ALZ");
            objList = stockService.findLoserStocks();
        } else if (detail.equals("_gainer_stocks")) {
            System.out.println("GNN");
                objList = stockService.findGainerStocks();
        }
        try {
            System.out.println("TRY");
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }

    }


    //*
    @GetMapping("/stocksummaries")
    public ResponseEntity<List<StockSummaries>> getStockSummariesForStock() {
        try {
            List<StockSummaries> objList;
            objList = stockService.listStockSummaries();
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }

    }
    //* listStockBidAskByStockCode
    /*
    @GetMapping("/stockbidask/")//{code}/{side}")
    public ResponseEntity <List<StockBidAskDTO>> getStockBidAskByStockCode(){//@PathVariable("code") String code,@PathVariable("side") String side) {
        try {
            List<StockBidAskDTO> objList;
            objList = stockService.listStockBidAskByStockCode();//code,side);
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERRR+++++++++++  "+e.getMessage());
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }
    */

    @GetMapping("/stockbidask/{code}/{side}")
    public ResponseEntity<List<StockBidAsk>> getStockBidAskByStockCode(@PathVariable("code") String code,@PathVariable("side") String side) {
        try {
            List<StockBidAsk> objList;
            objList=stockService.listStockBidAskByStockCode(code,side);
            //objList = stockBidAskRepository.findAll(); //code,side);
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERRR+++++++++++ Y "+e.getMessage());
          //  throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }

        try {
            List<StockBidAsk> objList;
            objList=stockBidAskRepository.findAll();
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception ee) {
            System.out.println("ERRR+++++++++++ YZZZ "+ee.getMessage());
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, ee.getMessage(), ee);
        }
    }
}
