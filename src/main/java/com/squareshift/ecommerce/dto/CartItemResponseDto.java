package com.squareshift.ecommerce.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartItemResponseDto extends CommonResponseDto{
    private List<ItemDto> items = new ArrayList<ItemDto>();
}
