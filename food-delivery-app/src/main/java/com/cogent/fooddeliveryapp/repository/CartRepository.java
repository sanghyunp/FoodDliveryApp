package com.cogent.fooddeliveryapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cogent.fooddeliveryapp.dto.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
//	public List<Cart> findByFoodId(Cart cart);
	
	Boolean existsByFoodId(Long foodId);
}
