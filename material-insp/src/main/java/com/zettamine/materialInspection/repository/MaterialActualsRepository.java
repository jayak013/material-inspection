package com.zettamine.materialInspection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.materialInspection.entities.MaterialActuals;
import com.zettamine.materialInspection.model.MaterialActualsCompKey;

public interface MaterialActualsRepository extends 
			JpaRepository<MaterialActuals, MaterialActualsCompKey>{
	
}
