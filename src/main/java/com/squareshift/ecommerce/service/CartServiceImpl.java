package com.squareshift.ecommerce.service;

import com.squareshift.ecommerce.dataaccess.EcommerceDataAccess;
import com.squareshift.ecommerce.dto.CartAddItemResponseDto;
import com.squareshift.ecommerce.dto.CartItemResponseDto;
import com.squareshift.ecommerce.dto.CommonResponseDto;
import com.squareshift.ecommerce.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    EcommerceDataAccess ecommerceDataAccess;

    @Override
    public CartItemResponseDto getItemsList() {
        return ecommerceDataAccess.getCartItems();
    }

    @Override
    public CartAddItemResponseDto addItemToCart(ItemDto itemAddToCart) {
        return ecommerceDataAccess.addItemToCart(itemAddToCart);
    }

    @Override
    public void deleteCartItems() {
        ecommerceDataAccess.deleteCartItems();
    }

    @Override
    public CommonResponseDto getCartValue(Long postalCode) {
        return ecommerceDataAccess.calculateCartValue(postalCode);
    }


}
