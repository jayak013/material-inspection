package com.zettamine.materialInspection.model;

import java.util.List;

import com.zettamine.materialInspection.entities.MaterialActuals;
import com.zettamine.materialInspection.entities.MaterialChars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CombinedList {
	
	private MaterialChars matChars;
	private MaterialActuals matActuals;
	
	public CombinedList(MaterialChars matChar, MaterialActuals matActual) {
		super();
		this.matChars = matChar;
		this.matActuals = matActual;
	}
	
	
	
}
