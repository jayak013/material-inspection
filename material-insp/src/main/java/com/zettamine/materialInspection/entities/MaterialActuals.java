package com.zettamine.materialInspection.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(MaterialActualsCompKey.class)
public class MaterialActuals {
	
	private double maxMes;
	private double minMes;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "lot_id")
	private MaterialInspLot matInsplot;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "char_id")
	private MaterialChars matChars;
	
}
