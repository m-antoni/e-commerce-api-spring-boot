package com.ecommerce.application.reponse;

import com.ecommerce.application.model.Cart;
import com.ecommerce.application.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CartHandler {

    private final CartRepository cartRepository;

    @Autowired
    public CartHandler(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Object GenerateResponse(){

        // Get the latest cart
        List<Cart> cartList = new ArrayList<>();
        cartRepository.findAll().forEach(cartList::add);

        // Get sum of cart's price
        Long sum = Long.valueOf(cartList.stream().mapToInt(x -> Math.toIntExact(x.getPrice())).sum());

        System.out.println(cartList);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cart_list", cartList);
        map.put("total_items", cartList.size()); // get the total items
        map.put("total_amount", sum);

        return map;
    }

}
