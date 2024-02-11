package com.zettamine.materialInspection.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MaterialChars {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mat_char_id")
	@SequenceGenerator(name = "mat_char_id",sequenceName = "mat_char_id_sequence",initialValue = 101,allocationSize = 1)
	private Integer charId;
	private String charDesc;
	private String uom;
	private double toleranceUl;
	private double toleranceLl;
	private String status = "active";
	@ManyToOne	
	@JoinColumn(name = "material_id")
	private Material material;
	
	@OneToMany(mappedBy = "matChars")
	private List<MaterialActuals> matActuals;
	
}
