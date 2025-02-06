package csxdddocker.service;

import csxdddocker.entity.Trader;
import csxdddocker.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TraderService {

    @Autowired
    TraderRepository traderRepository;

    public List<Trader> getAll(){
        List<Trader> objList=traderRepository.findAll();
        if(objList.size()>0) {
            return objList;
        }
        else {
            return new ArrayList<Trader>();
        }
    }

    public Trader getById(String id) {
        Optional<Trader> obj=traderRepository.findById(id);
        if(obj.isPresent()) {
            return obj.get();
        }
        else {
            return null;
        }
    }

    public Trader create(Trader objIn) {
        Trader newObj=traderRepository.save(objIn);
        return newObj;
    }

    public Trader update(String id, Trader objIn) {
        if(id != null) {
            Optional<Trader> obj=traderRepository.findById(id);
            if(obj.isPresent()) {
                Trader newObj=obj.get();
                newObj.setTraderDescription(objIn.getTraderDescription());
                newObj.setTraderUsername(objIn.getTraderUsername());
                newObj.setTraderPassword(objIn.getTraderPassword());

                newObj=traderRepository.save( newObj);
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
        Optional<Trader>obj=traderRepository.findById(id);
        if(obj.isPresent()) {
            traderRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }
}
