package com.pja.sri.pwrona.sri02.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotBlank (message = "Name must not be empty and length should be between 2 and 50")
    @Size(min=2, max=50)
    private String name;
    @NotBlank(message = "Category must not be empty")
    private String category;
    private Float price;
    private int amount;
    private Boolean isAvailable;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="manufacturer")
    @JsonBackReference
    private Manufacturer manufacturer;


}
