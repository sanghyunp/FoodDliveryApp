package com.cogent.fooddeliveryapp.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.IdNotFoundException;
import com.cogent.fooddeliveryapp.dto.Address;
import com.cogent.fooddeliveryapp.dto.Role;
import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.enums.ERole;
import com.cogent.fooddeliveryapp.payload.request.SigninRequest;
import com.cogent.fooddeliveryapp.payload.request.SignupRequest;
import com.cogent.fooddeliveryapp.payload.response.JwtResponse;
import com.cogent.fooddeliveryapp.repository.RoleRepository;
import com.cogent.fooddeliveryapp.security.jwt.JwtUtils;
import com.cogent.fooddeliveryapp.security.service.UserDetailsImpl;
import com.cogent.fooddeliveryapp.service.UserService;

@RestController
//  /api
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@Valid @RequestBody SigninRequest signinRequest){
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken
						(signinRequest.getUsername(), signinRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl)authentication.getPrincipal();
		
		List<String> roles = userDetailsImpl.getAuthorities().stream()
				.map(e->e.getAuthority()).collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetailsImpl.getId()
				, userDetailsImpl.getUsername()
				, userDetailsImpl.getEmail()
				, roles));
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest) {
		// can you create user object?
		// can you initialize the values based on the signuprequest object?
		Set<Role> roles = new HashSet<>();
		if(signupRequest.getRoles() == null) {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
			.orElseThrow(()-> new IdNotFoundException("RoleId not found exception"));
			roles.add(userRole);
		} else {
			signupRequest.getRoles().forEach(e->{

				switch (e) {
				case "user":
					
					Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
					.orElseThrow(()-> new IdNotFoundException("RoleId not found exception"));
					roles.add(userRole);
					break;
				case "admin":
					Role userAdmin = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
					.orElseThrow(()-> new IdNotFoundException("RoleId not found exception"));
					roles.add(userAdmin);
					break; // I added
				default:
					break;
				}

			});
		}
		
		User user = new User();
		
		Set<Address> addresses = new HashSet<>();
		signupRequest.getAddress().forEach(e->{
			Address address = new Address();
			address.setCity(e.getCity());
			address.setCountry(e.getCountry());
			address.setHouseNumber(e.getHouseNumber());
			address.setState(e.getState());
			address.setStreet(e.getStreet());
			address.setUser(user);
			address.setZip(e.getZip());
			addresses.add(address);
		});
		
		user.setAddresses(addresses);
		user.setEmail(signupRequest.getEmail());
		user.setUsername(signupRequest.getUsername());
//		user.setPassword(signupRequest.getPassword());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		user.setRoles(roles);
		user.setDoj(signupRequest.getDoj());
		
		User user2 = userService.addUser(user);
		
		return ResponseEntity.status(201).body(user2); // 201; created successfully
		
	}
	
	
	
}
