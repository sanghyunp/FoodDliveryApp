package com.cogent.fooddeliveryapp.payload.response;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

	@NotBlank
	private String userId;
	@NotBlank
	private String foodId;
}
