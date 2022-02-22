package com.cogent.fooddeliveryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cogent.fooddeliveryapp.dto.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

}
