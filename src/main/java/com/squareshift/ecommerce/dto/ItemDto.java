package com.squareshift.ecommerce.dto;

import lombok.Data;

@Data
public class ItemDto {
    private Long productId;
    private String description;
    private Integer quantity;

}
