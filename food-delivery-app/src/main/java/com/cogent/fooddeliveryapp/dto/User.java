package com.cogent.fooddeliveryapp.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
//@Setter
//@Getter
@EqualsAndHashCode(exclude = {"addresses", "roles"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = {"addresses", "roles"}) // toString not contain address
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email"),
//	@UniqueConstraint(columnNames = "name")}) // not allowed duplicate value about email and name(username)
@Table(name = "user_tbl",
	uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String email;

	private String password;

	private String username;
	
//	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate doj = LocalDate.now();
	
	// OneToMany Relation; user can take more than one address
	// fetch = FetchType.LAZY; improve performance and memory management
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Address> addresses;
	
//	@OneToMany(mappedBy = "userRoles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private Set<Role> roles;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "id"))
	public Set<Role> roles = new HashSet<>();
	{
		
	}

	
}
