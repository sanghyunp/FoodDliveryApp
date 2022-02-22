package com.cogent.fooddeliveryapp.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.xml.ws.Response;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.dto.Food;
import com.cogent.fooddeliveryapp.exception.NoDataFoundException;
import com.cogent.fooddeliveryapp.repository.FoodRepository;


@RequestMapping("/food")
//@ResponseBody
//@Controller
@RestController
@Validated
public class FoodController {
	
	@Autowired
	FoodRepository foodRepository;
	
	@PostMapping(value = "/") // @Valid ; activate method
	public ResponseEntity<?> createFood(@Valid @RequestBody Food food) {
		
		Food food2 = foodRepository.save(food);
		
		return ResponseEntity.status(201).body(food2);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id") @Min(1) Long id) {
		System.out.println("hello from controller method begining");
		
		Food food = foodRepository.findById(id).orElseThrow(()-> new NoDataFoundException("no data found"));
		return ResponseEntity.ok(food);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> putFoodById(@PathVariable("id") Long id,
			@Valid @RequestBody Food food) {
		Food food2 = foodRepository.findById(id).orElseThrow(()-> new NoDataFoundException("Sorry Food Not Found"));
		
		// add some
		
		food2.setFoodName(food.getFoodName());
		food2.setFoodPrice(food.getFoodPrice());
		food2.setFoodType(food.getFoodType());
		food2.setDescription(food.getDescription());
		food2.setFoodPic(food.getFoodPic());
		
		Food updatedFood = foodRepository.save(food2);
//		final Food updatedFood = foodRepository.save(food2);
		
		return ResponseEntity.status(201).body(updatedFood);
//		return ResponseEntity.ok(updatedFood);
	}
	
//	@GetMapping(value = "/")
//	public ResponseEntity<?> getFood() {
//		
//		return new ResponseEntity<?>();
//	}


//	@GetMapping(value = "/{foodType}")
//	public ResponseEntity<?> getFoodByFoodType(@PathVariable("foodType") String foodType) {
//		
//		List<Food> list = foodRepository.findAllById(foodType);
//		
////		Food food = foodRepository.findBy(null, null);
//		
//		Collections.sort(list, (a,b) -> a.getFoodName().compareTo(b.getFoodName()));
//		
//		return ResponseEntity.status(200).body(list);
//	}

	
	@GetMapping(value = "/all/desc")
	public ResponseEntity<?> getAllDescOrder() {
		
		List<Food> list = foodRepository.findAll();
		
//		Collections.sort(list, (a,b) -> a.getId().compareTo(b.getId())); // ascending order
		Collections.sort(list, (a,b) -> b.getId().compareTo(a.getId())); // descending order
		return ResponseEntity.status(200).body(list);
	}

	
}
