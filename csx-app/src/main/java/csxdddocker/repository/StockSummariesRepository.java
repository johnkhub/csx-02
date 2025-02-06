package csxdddocker.repository;

import csxdddocker.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StockSummariesRepository extends JpaRepository<StockSummaries, Long>{
    //@Query(name = "NamedQueryStockSummariesByStockCode", nativeQuery = true)
    //Boolean listStockSummariesByStockCode(String stockCode);

    @Query(name = "NamedQueryStockSummariesByStockCode", nativeQuery = true)
    List<StockSummaries> listStockSummariesByStockCode(String code1, String code2,String code3,String code4,String code5,String code6,String code7);

    @Query(name = "NamedQueryStockBidAskByStockCode", nativeQuery = true)
    List<StockBidAsk> listStockBidAskByStockCode(String stockcode, String orderside);


}
