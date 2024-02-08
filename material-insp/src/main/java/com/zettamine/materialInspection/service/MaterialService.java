package com.zettamine.materialInspection.service;

import java.util.List;

import com.zettamine.materialInspection.entities.Material;
import com.zettamine.materialInspection.entities.MaterialChars;

public interface MaterialService {

	Material addMaterial(Material material);

	List<Material> getAllMaterials();

	Material getMaterialById(String id);

	void removeMaterial(String id);

}
