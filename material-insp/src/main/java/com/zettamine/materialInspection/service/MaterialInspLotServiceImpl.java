package com.zettamine.materialInspection.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zettamine.materialInspection.entities.MaterialInspLot;
import com.zettamine.materialInspection.repository.MaterialInspLotRepository;

@Service
public class MaterialInspLotServiceImpl implements MaterialInspLotService {

	
	private MaterialInspLotRepository matInspLotRepo;

	public MaterialInspLotServiceImpl(MaterialInspLotRepository matInspLotRepo) {
		super();
		this.matInspLotRepo = matInspLotRepo;
	}



	@Override
	public MaterialInspLot addLot(MaterialInspLot materialInspLot) {
		return matInspLotRepo.save(materialInspLot);
	}
	
	
	
}
