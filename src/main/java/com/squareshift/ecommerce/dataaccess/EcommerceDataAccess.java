package com.squareshift.ecommerce.dataaccess;

import com.squareshift.ecommerce.dto.*;
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
    Long totalPrice = 0L;
    Long distance = 0L;
    Long totalWeight = 0L;
    int minPrice = 1;
    int maxPrice = 100;


    public CartAddItemResponseDto addItemToCart(ItemDto requestedCartItem) {
        CartAddItemResponseDto addItemToCartResponse = new CartAddItemResponseDto();
        try {
            ProductResponseDto product = proxy.getProductById(requestedCartItem.getProductId());
            requestedCartItem.setDescription(product.getProductDto().getDescription());
            Integer priceOfProduct = (int)Math.floor(Math.random()*(maxPrice-minPrice+1)+minPrice);
            totalPrice = totalPrice + ((priceOfProduct - (priceOfProduct * product.getProductDto().getDiscount_percentage()/100))
                    *requestedCartItem.getQuantity());
            totalWeight = totalWeight + product.getProductDto().getWeight_in_grams();
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
            totalPrice = 0L;
            distance = 0L;
            totalWeight = 0L;
            return true;
    }

    public CommonResponseDto calculateCartValue(Long postalCode) {
        CommonResponseDto totalCartValueResponse = new CartAddItemResponseDto();
        Long totalCartValue = 0L;
        try {
            WarehouseResponseDto deliverDest = proxy.getWareHouseDistanceByPostalCode(postalCode);
            if(deliverDest.getStatus().equalsIgnoreCase("error")) {
                totalCartValueResponse.setStatus(deliverDest.getStatus());
                totalCartValueResponse.setMessage("Invalid postal code, valid ones are 465535 to 465545");
                return totalCartValueResponse;
            }
            distance = deliverDest.getDistance_in_kilometers();
            if(distance < 5) {
                totalCartValue = totalCartValue + totalPrice + calculateShippingCost("L1");
            } else if(distance >=5 && distance < 20) {
                totalCartValue = totalCartValue + totalPrice + calculateShippingCost("L2");
            } else if(distance >= 20 && distance < 50) {
                totalCartValue = totalCartValue + totalPrice + calculateShippingCost("L3");
            } else if(distance >= 50 && distance < 500) {
                totalCartValue = totalCartValue + totalPrice + calculateShippingCost("L4");
            } else if (distance >= 500 && distance < 800) {
                totalCartValue = totalCartValue + totalPrice + calculateShippingCost("L5");
            } else if(distance >= 800) {
                totalCartValue = totalCartValue + totalPrice + calculateShippingCost("L6");
            }
            totalCartValueResponse.setStatus("success");
            totalCartValueResponse.setMessage("Total value of your shopping cart is - $" + totalCartValue);
            return totalCartValueResponse;
        } catch (Exception e) {
            totalCartValueResponse.setStatus("error");
            totalCartValueResponse.setMessage("Invalid postal code");
            return totalCartValueResponse;
        }

    }

    public Long calculateShippingCost(String level) {
        if(totalWeight == 0) {
            return 0L;
        }
        switch(level) {
            case "L1":
                if(totalWeight <= 2) {
                    return 12L;
                } else if(totalWeight > 2 && totalWeight <= 5) {
                    return 14L;
                } else if(totalWeight > 5 && totalWeight <= 20) {
                    return 16L;
                } else if(totalWeight > 20) {
                    return 21L;
                }
                break;
            case "L2":
                if(totalWeight <= 2) {
                    return 15L;
                } else if(totalWeight > 2 && totalWeight <= 5) {
                    return 18L;
                } else if(totalWeight > 5 && totalWeight <= 20) {
                    return 25L;
                } else if(totalWeight > 20) {
                    return 35L;
                }
                break;
            case "L3":
                if(totalWeight <= 2) {
                    return 20L;
                } else if(totalWeight > 2 && totalWeight <= 5) {
                    return 24L;
                } else if(totalWeight > 5 && totalWeight <= 20) {
                    return 30L;
                } else if(totalWeight > 20) {
                    return 50L;
                }
                break;
            case "L4":
                if(totalWeight <= 2) {
                    return 50L;
                } else if(totalWeight > 2 && totalWeight <= 5) {
                    return 55L;
                } else if(totalWeight > 5 && totalWeight <= 20) {
                    return 80L;
                } else if(totalWeight > 20) {
                    return 90L;
                }
                break;
            case "L5":
                if(totalWeight <= 2) {
                    return 100L;
                } else if(totalWeight > 2 && totalWeight <= 5) {
                    return 110L;
                } else if(totalWeight > 5 && totalWeight <= 20) {
                    return 130L;
                } else if(totalWeight > 20) {
                    return 150L;
                }
                break;
            case "L6":
                if(totalWeight <= 2) {
                    return 220L;
                } else if(totalWeight > 2 && totalWeight <= 5) {
                    return 250L;
                } else if(totalWeight > 5 && totalWeight <= 20) {
                    return 270L;
                } else if(totalWeight > 20) {
                    return 300L;
                }
                break;
            default:
                return 0L;
        }
        return 0L;
    }
}
