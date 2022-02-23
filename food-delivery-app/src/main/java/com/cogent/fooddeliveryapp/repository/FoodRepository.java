package com.cogent.fooddeliveryapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cogent.fooddeliveryapp.dto.Food;
import com.cogent.fooddeliveryapp.enums.FoodType;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

	public List<Food> findByFoodType(FoodType foodType);
}
