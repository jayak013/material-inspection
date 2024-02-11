package com.zettamine.materialInspection.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zettamine.materialInspection.entities.MaterialActuals;
import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.MaterialInspLot;
import com.zettamine.materialInspection.repository.MaterialInspLotRepository;

@Service
public class MaterialInspLotServiceImpl implements MaterialInspLotService {

	
	private MaterialInspLotRepository matInspLotRepo;
	
	private MaterialActualsService matActualsService;
	
	@Autowired
	private MaterialService matService;

	public MaterialInspLotServiceImpl(MaterialInspLotRepository matInspLotRepo,
			MaterialActualsService matActualsService) {
		super();
		this.matInspLotRepo = matInspLotRepo;
		this.matActualsService = matActualsService;
	}



	@Override
	public MaterialInspLot addLot(MaterialInspLot materialInspLot) {
		return matInspLotRepo.save(materialInspLot);
	}



	@Override
	public MaterialInspLot getByLotId(Integer lotId) {
		return matInspLotRepo.findById(lotId).get();
	
	}



	@Override
	public List<MaterialInspLot> getAll() {
		return matInspLotRepo.findAll();
	}



	@Override
	public boolean matActalsPassOrFail(MaterialInspLot matInspLot) {
		
		
		MaterialInspLot materialInspLot = matInspLotRepo.findById(matInspLot.getLotId()).get();
		
		List<MaterialActuals> matActuals = materialInspLot.getMatActuals();
		System.out.println(matInspLot.getMaterial());
		
		List<MaterialChars> matChars = matInspLot.getMaterial().getMatChars();
		
		for(MaterialActuals matActual :matActuals) {
			Integer matActualCharId = matActual.getMatChars().getCharId();
			for(MaterialChars matChar:matChars) {
				Integer matCharId = matChar.getCharId();
				if(matCharId.equals(matActualCharId)) {
					double minMes = matActual.getMinMes();
					double maxMes = matActual.getMaxMes();
					double toleranceUl = matChar.getToleranceUl();
					double toleranceLl = matChar.getToleranceLl();

					if(!(maxMes<=toleranceUl && minMes>=toleranceLl)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}



	@Override
	public List<MaterialChars> getMatCharsForActuals(Integer lotId) {
		
		MaterialInspLot materialInspLot = matInspLotRepo.findById(lotId).get();
		
		List<MaterialActuals> matActuals = materialInspLot.getMatActuals().stream().collect(Collectors.toList());
		List<MaterialChars> matChars = materialInspLot.getMaterial().getMatChars().stream().collect(Collectors.toList());
		
		for(MaterialActuals matActual:matActuals) {
			matChars.remove(matActual.getMatChars());
		}
		
		return matChars;
	}
	
	
	
}
