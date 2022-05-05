package com.pja.sri.pwrona.sri02.dto;

import com.pja.sri.pwrona.sri02.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturerDetailsDto extends RepresentationModel<ManufacturerDetailsDto> {
    private Long id;
    private String brand;
    private Set<Product> products;
}


