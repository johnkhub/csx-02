package csxdddocker.repository;

import csxdddocker.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TraderRepository extends JpaRepository<Trader, String> {
    @Query(value="SELECT trd FROM Trader trd WHERE trd.traderCode = :traderCode")
    Optional<Trader> findTraderByCode(String traderCode);
}
