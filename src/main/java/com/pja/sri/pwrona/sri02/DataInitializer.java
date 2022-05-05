package com.pja.sri.pwrona.sri02;

import com.pja.sri.pwrona.sri02.model.Manufacturer;
import com.pja.sri.pwrona.sri02.model.Product;
import com.pja.sri.pwrona.sri02.repo.ManufacturerRepository;
import com.pja.sri.pwrona.sri02.repo.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataInitializer implements ApplicationRunner {

    private ManufacturerRepository manufacturerRepository;
    private ProductRepository productRepository;

    public DataInitializer (ManufacturerRepository manufacturerRepository, ProductRepository productRepository){
        this.productRepository=productRepository;
        this.manufacturerRepository=manufacturerRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Product p1 = Product.builder()
                .name("JustDoIt")
                .category("T-SHIRT")
                .price(59.99f)
                .amount(100)
                .isAvailable(true)
                .build();

        Product p2 = Product.builder()
                .name("754")
                .category("SHOES")
                .price(329.99f)
                .amount(43)
                .isAvailable(true)
                .build();

        Product p3 = Product.builder()
                .name("AirForce1")
                .category("SHOES")
                .price(420f)
                .amount(30)
                .isAvailable(true)
                .build();

        Manufacturer m1 = Manufacturer.builder()
                .brand("Nike")
                .products(new HashSet<>())
                .build();

        Manufacturer m2 = Manufacturer.builder()
                .brand("NewBalance")
                .products(new HashSet<>())
                .build();


        p1.setManufacturer(m1);
        p2.setManufacturer(m2);
        p3.setManufacturer(m1);
        m1.getProducts().add(p1);
        m1.getProducts().add(p2);
        m2.getProducts().add(p2);


        productRepository.saveAll(Arrays.asList(p1, p2, p3));
        manufacturerRepository.saveAll(Arrays.asList(m1, m2));
        }



}
