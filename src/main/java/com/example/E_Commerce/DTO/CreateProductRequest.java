package com.example.E_Commerce.DTO;

import com.example.E_Commerce.model.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class CreateProductRequest {

    private String title;
    private  String description;
    private int price;
    private int discountPrice;
    private  int discountPresent;
    private int quantity;
    private String brand;
    private String color;
    private Set<Size> size;
    private String imageUrl;
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;

}
