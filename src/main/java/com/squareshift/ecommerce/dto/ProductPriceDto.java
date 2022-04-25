package com.squareshift.ecommerce.dto;

import lombok.Data;

@Data
public class ProductPriceDto extends ProductDto {
    private Long price;
}
