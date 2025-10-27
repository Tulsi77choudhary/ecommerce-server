package com.example.E_Commerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddItemRequest {

    private  Long productId;
    private String size;
    private int quantity;
    private int price;

}
