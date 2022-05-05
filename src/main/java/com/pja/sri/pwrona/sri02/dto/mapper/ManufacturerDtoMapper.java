package com.pja.sri.pwrona.sri02.dto.mapper;

import com.pja.sri.pwrona.sri02.dto.ManufacturerDetailsDto;
import com.pja.sri.pwrona.sri02.dto.ManufacturerDto;
import com.pja.sri.pwrona.sri02.model.Manufacturer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManufacturerDtoMapper {
    private final ModelMapper modelMapper;

    public ManufacturerDto convertToDto(Manufacturer m){
        return modelMapper.map(m, ManufacturerDto.class);
    }

    public ManufacturerDetailsDto convertToDtoDetails(Manufacturer m){
        return modelMapper.map(m, ManufacturerDetailsDto.class);
    }

    public Manufacturer convertToEntity(ManufacturerDto dto){
        return modelMapper.map(dto, Manufacturer.class);
    }

}
