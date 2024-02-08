package com.zettamine.materialInspection.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Plant {
	
	@Id
	private String plantId;
	private String name;
	private String city;
	private String state;
	private String status = "active";
	
	@OneToMany(mappedBy = "plant",cascade = CascadeType.ALL)
	private List<MaterialInspLot> materialInspLots = new ArrayList<>();
}
