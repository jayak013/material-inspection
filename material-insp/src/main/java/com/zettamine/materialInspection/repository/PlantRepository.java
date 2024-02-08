package com.zettamine.materialInspection.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.materialInspection.entities.Plant;

public interface PlantRepository extends JpaRepository<Plant, Serializable> {

}
