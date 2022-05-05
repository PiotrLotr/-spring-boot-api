package com.pja.sri.pwrona.sri02.repo;

import com.pja.sri.pwrona.sri02.model.Manufacturer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends CrudRepository<Manufacturer, Long> {

    List<Manufacturer> findAll();

    @Override
    Optional<Manufacturer> findById(Long aLong);

//    @Query("SELECT Manufacturer as m FROM Manufacturer WHERE m.id = :manufacturerId")
//    Optional<Manufacturer> (@Param("manufacturerId") Long manufacturerId);

}
