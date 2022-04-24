package com.squareshift.ecommerce.dataaccess;

import com.squareshift.ecommerce.dto.CartAddItemResponseDto;
import com.squareshift.ecommerce.dto.CartItemResponseDto;
import com.squareshift.ecommerce.dto.ItemDto;
import com.squareshift.ecommerce.dto.ProductResponseDto;
import com.squareshift.ecommerce.proxy.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcommerceDataAccess {

    @Autowired
    Proxy proxy;

    List<ItemDto> itemsList = new ArrayList<>();

    public CartAddItemResponseDto addItemToCart(ItemDto requestedCartItem) {
        CartAddItemResponseDto addItemToCartResponse = new CartAddItemResponseDto();
        try {
            ProductResponseDto product = proxy.getProductById(requestedCartItem.getProductId());
            requestedCartItem.setDescription(product.getProductDto().getDescription());
        } catch (Exception e) {
            addItemToCartResponse.setStatus("error");
            addItemToCartResponse.setMessage("Invalid product id");
            return addItemToCartResponse;
        }
        itemsList.add(requestedCartItem);
        addItemToCartResponse.setStatus("success");
        addItemToCartResponse.setMessage("Item has been added to cart");
        return addItemToCartResponse;
    }

    public CartItemResponseDto getCartItems() {
        CartItemResponseDto cartItems = new CartItemResponseDto();
        cartItems.setStatus("success");
        cartItems.setItems(itemsList);
        if(itemsList.size() > 0) {
            cartItems.setMessage("Item available in the cart");
        } else {
            cartItems.setMessage("Cart empty");
        }
        return cartItems;
    }

    public boolean deleteCartItems() {
            itemsList.clear();
            return true;
    }
}
