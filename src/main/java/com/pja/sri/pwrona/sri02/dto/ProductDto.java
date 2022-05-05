package com.pja.sri.pwrona.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends RepresentationModel<ProductDto> {
    private Long id;
    @NotBlank
    @Size(min=2, max=50)
    private String name;
    @NotBlank
    private String category;
    private Long price;
    private Long amount;
    private Boolean isAvailable;

}
