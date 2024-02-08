package com.zettamine.materialInspection.service;

import java.util.List;

import com.zettamine.materialInspection.entities.Plant;
import com.zettamine.materialInspection.entities.Vendor;

public interface PlantService {

	Plant addPlant(Plant plant);

	List<Plant> getAllPlants();

	void removePlant(String id);

	Plant getPlantById(String id);

}
