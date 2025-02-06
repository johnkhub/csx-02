package csxdddocker.controller;


import csxdddocker.entity.Trader;
import csxdddocker.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/csx/trader")
public class TraderController {

    @Autowired
    TraderService traderService;

    @GetMapping()
    public ResponseEntity<List<Trader>> getAll() {
        try {
            List<Trader> objList = new ArrayList<Trader>();
            objList = traderService.getAll();
            if (objList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(objList, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trader> getById(@PathVariable("id") String id) {
        Trader obj = traderService.getById(id);
        if (obj != null) {
            return new ResponseEntity<Trader>(obj, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Trader> create(@RequestBody Trader objIn) {
        Trader obj = traderService.create(objIn);
        if (obj != null) {
            return new ResponseEntity<>(obj, new HttpHeaders(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trader> update(@PathVariable(value = "id") String id, @RequestBody Trader objIn) {
        try {
            Trader obj = traderService.update(id, objIn);
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
        boolean resp = traderService.deleteById(id);
        if (resp == true) {
            return new ResponseEntity<>(true, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
}

