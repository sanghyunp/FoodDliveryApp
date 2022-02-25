package com.cogent.fooddeliveryapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cogent.fooddeliveryapp.security.jwt.AuthEntryPointJwt;
import com.cogent.fooddeliveryapp.security.jwt.AuthTokenFilter;
import com.cogent.fooddeliveryapp.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity  // it will make sure that 
// security env. is enabled.
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // I added for food reg test
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
//	private AuthEntryPointJwt authEntryPointJwt;
	private AuthEntryPointJwt unauthorizedHandler;  // for better understanding
	
	
	@Bean  // to have customized object as per the requirement.
//	@Scope("prototype")  // multiple object
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	@Bean  // one ;
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	@Override  // create all security system by this method
	protected void configure(HttpSecurity http) throws Exception {
		// core part security ---> we can restrict the access of end points through this configuration.
		// we can set unauthorized access through this.
		// we can provide direct go access for ignup and signin (authorizing the res).
		// applying token validation for end points.
		// CORS :
		// 
		// TODO Auto-generated method stub
		// endpoints specification
		http.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()   // /api/auth/** ; connected to AuthController.java
		.authorizeRequests().antMatchers("/api/auth/**").permitAll()  // register and login ; generated token
//		.antMatchers("/api/food/**").permitAll().anyRequest().authenticated();  // authenticated user allowed
		.antMatchers("/api/food/**").authenticated()
		.anyRequest().permitAll(); // it fixed /api/food/add, without token problem
		    // /api/food/** ; connected to FoodController.java
		// handling the filter
		http.addFilterBefore(authenticationJwtTokenFilter(), 
				UsernamePasswordAuthenticationFilter.class);
	}
	
	
	
}
