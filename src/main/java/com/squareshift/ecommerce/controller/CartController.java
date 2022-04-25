package com.squareshift.ecommerce.controller;

import com.squareshift.ecommerce.dto.CartAddItemResponseDto;
import com.squareshift.ecommerce.dto.CartItemResponseDto;
import com.squareshift.ecommerce.dto.CommonResponseDto;
import com.squareshift.ecommerce.dto.ItemDto;
import com.squareshift.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping(value = "/v1/cart/item")
    public ResponseEntity addItemToCart(@RequestBody ItemDto itemAddToCart) {
        CartAddItemResponseDto response = cartService.addItemToCart(itemAddToCart);
        if(response.getStatus().equalsIgnoreCase("error")) {
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/cart/items")
    public ResponseEntity getCartItems() {
        CartItemResponseDto response = cartService.getItemsList();
        if(response.getItems().size() <= 0) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/cart/checkout-value")
    public ResponseEntity getCartValue(@RequestParam(name="postalCode") Long postalCode) {
        CommonResponseDto response = cartService.getCartValue(postalCode);
        if(response.getStatus().equalsIgnoreCase("error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/cart/items")
    public ResponseEntity deleteCartItems() {
        cartService.deleteCartItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
