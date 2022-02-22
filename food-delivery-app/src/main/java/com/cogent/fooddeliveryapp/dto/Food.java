package com.cogent.fooddeliveryapp.dto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cogent.fooddeliveryapp.enums.FoodType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String foodName;
	@NotBlank
	private String description;
	@NotBlank
	private String foodPic;
	@NotNull
	private float foodPrice;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private FoodType foodType;
}
