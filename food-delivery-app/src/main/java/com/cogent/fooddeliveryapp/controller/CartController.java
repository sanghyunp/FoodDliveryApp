package com.cogent.fooddeliveryapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.dto.Cart;
import com.cogent.fooddeliveryapp.exception.DataAlreadyExistsException;
import com.cogent.fooddeliveryapp.exception.NameAlreadyExistsException;
import com.cogent.fooddeliveryapp.repository.CartRepository;

@RequestMapping("/api/cart")
@RestController
@Validated
public class CartController {
	
	@Autowired
	CartRepository cartRepository;

	@PutMapping(value = "/{foodId}")  // /api/user/:userId/cart/
	public ResponseEntity<?> addFoodInCart(@PathVariable("foodId") Long foodId, 
			@Valid @RequestBody Cart cart) {
//			HttpSession session) {
//		Cart cart2 = cartRepository.findByFoodId(foodId).orElseThrow(() -> new NoDataFoundException("food not found"));
		
		if (cartRepository.existsByFoodId(foodId)) {
//			throw new DataAlreadyExistsException("food is already in the cart");
			return ResponseEntity.status(200).body("food is already in the cart");
		} else {
			Cart cart2 = cartRepository.save(cart);
			return ResponseEntity.status(201).body(cart2);
		}
		
	}
	
}
