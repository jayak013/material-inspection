package com.zettamine.materialInspection.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.Vendor;
import com.zettamine.materialInspection.repository.MaterialCharsRepository;

@Service
public class MaterialCharsServiceImpl implements MaterialCharsService {
	
	private MaterialCharsRepository matCharRepo;
	
	@Autowired
	public MaterialCharsServiceImpl(MaterialCharsRepository matCharRepo) {
		super();
		this.matCharRepo = matCharRepo;
	}



	@Override
	public MaterialChars addMaterialChar(MaterialChars matChars) {
		return matCharRepo.save(matChars);
	}



	@Override
	public MaterialChars getByCharId(Integer charId) {
		return matCharRepo.findById(charId).get();
	}



	@Override
	public void removeMatChars(Integer charId) {
		MaterialChars matChars = matCharRepo.findById(charId).get();
		matChars.setStatus("inactive");
		matCharRepo.save(matChars);
	}





}
