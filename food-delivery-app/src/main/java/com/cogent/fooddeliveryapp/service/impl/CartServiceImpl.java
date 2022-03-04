package com.cogent.fooddeliveryapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogent.fooddeliveryapp.dto.Cart;
import com.cogent.fooddeliveryapp.repository.UserRepository;
import com.cogent.fooddeliveryapp.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public Cart addFoodInCart(Cart cart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findAllInCart(Cart cart) {
		// TODO Auto-generated method stub
		return false;
	}

}
