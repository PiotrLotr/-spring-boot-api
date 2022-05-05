package com.pja.sri.pwrona.sri02.repo;

import com.pja.sri.pwrona.sri02.model.Manufacturer;
import com.pja.sri.pwrona.sri02.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAll();

    @Query("SELECT m.products FROM Manufacturer AS m WHERE m.id = :manufacturerId")
    List<Product> findProductsByManufacturerId(@PathVariable Long manufacturerId);


}
