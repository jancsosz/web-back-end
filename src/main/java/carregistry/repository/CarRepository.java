package carregistry.repository;

import carregistry.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByNumPlate(String numPlate);

    boolean existsByNumPlate(String numPlate);

    @Transactional
    void deleteByNumPlate(String numPlate);

    List<Car> findByOwner_Username(String ownerName);

    @Modifying
    @Transactional
    @Query("update Car c " +
            "set c.name = :name, c.weight = :weight, c.type = :type, c.owner = :owner " +
            "where c.numPlate = :numPlate")
    void update(@Param("name") String name, @Param("weight") Integer weight,
                @Param("numPlate") String numPlate, @Param("type") String type, @Param("owner") String owner);
}
