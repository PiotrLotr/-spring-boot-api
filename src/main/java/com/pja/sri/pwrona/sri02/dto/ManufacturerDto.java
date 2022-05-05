package com.pja.sri.pwrona.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDto extends RepresentationModel<ManufacturerDto> {
    private Long id;
    @NotBlank
    private String brand;

}