package com.zettamine.materialInspection.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
	
	@Id
	private Integer userId;
	private String userName;
	private String password;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<MaterialInspLot> materialInspLots = new ArrayList<>();
}
