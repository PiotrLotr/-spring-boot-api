package com.pja.sri.pwrona.sri02.rest;

import com.pja.sri.pwrona.sri02.dto.ManufacturerDetailsDto;
import com.pja.sri.pwrona.sri02.dto.ManufacturerDto;
import com.pja.sri.pwrona.sri02.dto.ProductDto;
import com.pja.sri.pwrona.sri02.dto.mapper.ManufacturerDtoMapper;
import com.pja.sri.pwrona.sri02.model.Manufacturer;
import com.pja.sri.pwrona.sri02.model.Product;
import com.pja.sri.pwrona.sri02.repo.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private ManufacturerDtoMapper manufacturerDtoMapper;

    public ProductController(ProductRepository productRepository, ModelMapper modelMapper){
        this.productRepository = productRepository;
        this.modelMapper=modelMapper;
    }

    private ProductDto convertToDto(Product e){
        return modelMapper.map(e, ProductDto.class);
    }
    private Product convertToEntity(ProductDto dto){
        return modelMapper.map(dto, Product.class);
    }

    @GetMapping(produces = { "application/hal+json" })
    public ResponseEntity<CollectionModel<ProductDto>> getProducts (){
        List<Product> allProducts = productRepository.findAll();
        List<ProductDto> result = allProducts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        Link linkSelf = linkTo(methodOn(ProductController.class).getProducts()).withSelfRel();
        CollectionModel<ProductDto> res = CollectionModel.of(result, linkSelf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long empId){
        Optional<Product> emp = productRepository.findById(empId);
        if(emp.isPresent()){
            ProductDto productDto = convertToDto(emp.get());
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

// TODO: Try implement below function
//    @GetMapping("/{productId}/manufacturer")
//    public ResponseEntity<ManufacturerDetailsDto>getProductManufacturerDetails(@PathVariable Long productId){
//            Optional<Manufacturer> man = productRepository.findManufacturerOfProduct(productId);
//            if(man.isPresent()){
//                ManufacturerDetailsDto manufacturerDetailsDto = manufacturerDtoMapper.convertToDtoDetails(man.get());
//                return new ResponseEntity<>(manufacturerDetailsDto, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }
//    }


    @PostMapping
    public ResponseEntity saveNewProduct(@Valid @RequestBody ProductDto emp){
        Product entity = convertToEntity(emp);
        productRepository.save(entity);

        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{prodId}")
    public ResponseEntity updateProduct(@PathVariable Long prodId, @Valid @RequestBody ProductDto productDto) {
        Optional<Product> currentProd = productRepository.findById(prodId);
        if(currentProd.isPresent()) {
            productDto.setId(prodId);
            Product entity = convertToEntity(productDto);
            productRepository.save(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{prodId}")
    public ResponseEntity deleteProduct(@PathVariable Long prodId)
    {
        productRepository.deleteById(prodId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/exists/{prodId}")
    public boolean existsById(@PathVariable Long prodId){
        return productRepository.existsById(prodId);
    }

    @GetMapping("/count")
    public long countProducts(){
        return productRepository.count();
    }

//    private Link creatProductManufacturerLink(Long manufacturerId){
//        return linkTo(methodOn(ProductController.class).findById(manufacturerId)).withSelfRel();
//    }

    private Link createManufacturerSelfLink(Long manufacturerId){
        return linkTo(methodOn(ManufacturerController.class).getManufacturerById(manufacturerId)).withSelfRel();
    }


}
