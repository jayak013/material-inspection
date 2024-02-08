package com.zettamine.materialInspection.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zettamine.materialInspection.entities.MaterialChars;

public interface MaterialCharsRepository extends JpaRepository<MaterialChars, Serializable> {
	
	
}
