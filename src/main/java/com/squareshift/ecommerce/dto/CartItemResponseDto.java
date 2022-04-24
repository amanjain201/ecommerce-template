package com.squareshift.ecommerce.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartItemResponseDto {
    private String status;
    private String message;
    private List<ItemDto> items = new ArrayList<ItemDto>();
}
