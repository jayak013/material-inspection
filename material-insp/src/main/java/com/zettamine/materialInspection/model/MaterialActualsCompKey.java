package com.zettamine.materialInspection.model;

import java.io.Serializable;
import java.util.Objects;

import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.MaterialInspLot;

import jakarta.persistence.Embeddable;
import lombok.Data;


public class MaterialActualsCompKey implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private MaterialInspLot matInsplot;
	private MaterialChars matChars;
	
	public MaterialActualsCompKey() {}
	
	public MaterialActualsCompKey(MaterialInspLot matInsplot, MaterialChars matChars) {
		super();
		this.matInsplot = matInsplot;
		this.matChars = matChars;
	}
	@Override
	public int hashCode() {
		return Objects.hash(matChars, matInsplot);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaterialActualsCompKey other = (MaterialActualsCompKey) obj;
		return Objects.equals(matChars, other.matChars) && Objects.equals(matInsplot, other.matInsplot);
	}

	
	
}
