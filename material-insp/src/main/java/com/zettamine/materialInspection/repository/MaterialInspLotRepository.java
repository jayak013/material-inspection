package com.zettamine.materialInspection.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.materialInspection.entities.MaterialInspLot;

public interface MaterialInspLotRepository extends JpaRepository<MaterialInspLot, Serializable> {
	
}
