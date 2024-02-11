package com.zettamine.materialInspection.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Material {
	@Id
	private String materialId;
	private String description;
	private String type;
	private String status = "active";
	
	@OneToMany(mappedBy = "material",cascade = CascadeType.ALL)
	private List<MaterialChars> matChars = new ArrayList<>();
	
	@OneToMany(mappedBy = "material",cascade = CascadeType.ALL)
	private List<MaterialInspLot> materialInspLots = new ArrayList<>();
}
