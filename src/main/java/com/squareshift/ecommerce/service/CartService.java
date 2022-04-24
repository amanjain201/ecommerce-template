package com.squareshift.ecommerce.service;

import com.squareshift.ecommerce.dto.CartAddItemResponseDto;
import com.squareshift.ecommerce.dto.CartItemResponseDto;
import com.squareshift.ecommerce.dto.ItemDto;

public interface CartService {

    public CartItemResponseDto getItemsList();

    public CartAddItemResponseDto addItemToCart(ItemDto itemAddToCart);

    public void deleteCartItems();
}
