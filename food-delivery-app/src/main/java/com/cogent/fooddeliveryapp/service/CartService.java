package com.cogent.fooddeliveryapp.service;

import com.cogent.fooddeliveryapp.dto.Cart;

public interface CartService {

	public Cart addFoodInCart(Cart cart);
	public boolean findAllInCart(Cart cart);
	
}
