package com.ecommerce.application.response;

import com.ecommerce.application.model.CartItem;
import com.ecommerce.application.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CartHandler {

    private final CartItemRepository cartRepository;

    @Autowired
    public CartHandler(CartItemRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Object GenerateResponse(){

        // Get the latest cart
        List<CartItem> cartItemList = new ArrayList<>();
        cartRepository.findAll().forEach(cartItemList::add);

        // Get sum of cart's price
        Long sum = Long.valueOf(cartItemList.stream().mapToInt(x -> Math.toIntExact(x.getPrice())).sum());

        System.out.println(cartItemList);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cart_list", cartItemList);
        map.put("total_items", cartItemList.size()); // get the total items
        map.put("total_amount", sum);

        return map;
    }

}
