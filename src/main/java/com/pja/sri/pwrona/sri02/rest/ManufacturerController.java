package com.pja.sri.pwrona.sri02.rest;

import com.pja.sri.pwrona.sri02.dto.ManufacturerDetailsDto;
import com.pja.sri.pwrona.sri02.dto.ManufacturerDto;
import com.pja.sri.pwrona.sri02.dto.ProductDto;
import com.pja.sri.pwrona.sri02.dto.mapper.ManufacturerDtoMapper;
import com.pja.sri.pwrona.sri02.model.Manufacturer;
import com.pja.sri.pwrona.sri02.model.Product;
import com.pja.sri.pwrona.sri02.repo.ManufacturerRepository;
import com.pja.sri.pwrona.sri02.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manufactures")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ModelMapper modelMapper;
    private final ManufacturerDtoMapper manufacturerDtoMapper;

    @GetMapping(produces = { "application/hal+json" })
    public ResponseEntity<CollectionModel<ManufacturerDto>> getManufacturers(){
        List<Manufacturer> allManufacturers = manufacturerRepository.findAll();
        List<ManufacturerDto> result = allManufacturers.stream()
                .map(manufacturerDtoMapper::convertToDto)
                .collect(Collectors.toList());
        for(ManufacturerDto dto: result){
            dto.add(createManufacturerSelfLink(dto.getId()));
            dto.add(createManufacturerProductsLink(dto.getId()));
        }
        Link linkSelf = linkTo(methodOn(ManufacturerController.class).getManufacturers()).withSelfRel();
        CollectionModel<ManufacturerDto> res = CollectionModel.of(result, linkSelf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "/{manufacturerId}", produces = { "application/hal+json" })
    public ResponseEntity<ManufacturerDetailsDto> getManufacturerById(@PathVariable Long manufacturerId){
        Optional<Manufacturer> man = manufacturerRepository.findById(manufacturerId);
        if(man.isPresent()){
            ManufacturerDetailsDto manufacturerDetailsDto = manufacturerDtoMapper.convertToDtoDetails(man.get());
            manufacturerDetailsDto.add(createManufacturerSelfLink(manufacturerId));
            return new ResponseEntity<>(manufacturerDetailsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{manufacturerId}/products")
    public ResponseEntity<Collection<ProductDto>> getProductsOfManufacturerById(@PathVariable Long manufacturerId){
        List<Product> allProducts = productRepository.findProductsByManufacturerId(manufacturerId);
        List<ProductDto> result = allProducts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewManufacturer(@Valid @RequestBody ManufacturerDto man){
        Manufacturer entity = manufacturerDtoMapper.convertToEntity(man);
        manufacturerRepository.save(entity);

        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity updateManufacturer( @PathVariable Long manufacturerId, @Valid @RequestBody ManufacturerDto employeeDto) {
        Optional<Manufacturer> currentEmp = manufacturerRepository.findById(manufacturerId);
        if(currentEmp.isPresent()) {
            employeeDto.setId(manufacturerId);
            Manufacturer entity = manufacturerDtoMapper.convertToEntity(employeeDto);
            manufacturerRepository.save(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity deleteManufacturer(@PathVariable Long manufacturerId)
    {
        manufacturerRepository.deleteById(manufacturerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    // ################################################
    private ProductDto convertToDto(Product p) {
        return modelMapper.map(p, ProductDto.class);
    }

    private Link createManufacturerSelfLink(Long manufacturerId){
        return linkTo(methodOn(ManufacturerController.class).getManufacturerById(manufacturerId)).withSelfRel();
    }

    private Link createManufacturerProductsLink(Long manufacturerId){
        return linkTo(methodOn(ManufacturerController.class).getProductsOfManufacturerById(manufacturerId)).withSelfRel();
    }


}





