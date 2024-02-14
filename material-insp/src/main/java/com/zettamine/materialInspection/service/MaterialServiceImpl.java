package com.zettamine.materialInspection.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zettamine.materialInspection.entities.Material;
import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.Plant;
import com.zettamine.materialInspection.repository.MaterialRepository;
import com.zettamine.materialInspection.utils.SpaceRemover;

@Service
public class MaterialServiceImpl implements MaterialService {
	
	private MaterialRepository matRepo;
	
	@Autowired
	private SpaceRemover spacesRemover;
	
	public MaterialServiceImpl(MaterialRepository matRepo) {
		this.matRepo = matRepo;
	}

	


	@Override
	public Material addMaterial(Material material) {
		material.setMaterialId(spacesRemover.removeSpaces(material.getMaterialId()).toUpperCase());
		material.setDescription(material.getDescription().toUpperCase().trim());
		material.setType(material.getType().toUpperCase().trim());
		return matRepo.save(material);
	}




	@Override
	public List<Material> getAllMaterials() {
		List<Material> activeMaterials = matRepo.findAll().stream().filter(m->m.getStatus().equals("active")).sorted((m1,m2)->m1.getMaterialId().compareToIgnoreCase(m2.getMaterialId())).collect(Collectors.toList());
		return activeMaterials;
	}




	@Override
	public Material getMaterialById(String id) {
		Material material = matRepo.findById(id).get();
		List<MaterialChars> activeMatChars = material.getMatChars().stream().filter(mc->mc.getStatus().equals("active")).collect(Collectors.toList());
		material.setMatChars(activeMatChars);
		return material;
	}


	@Override
	public void removeMaterial(String id) {
		Material material = matRepo.findById(id).get();
		material.setStatus("inactive");
		matRepo.save(material);
	}




	

}
