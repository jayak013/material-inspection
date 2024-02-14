package com.zettamine.materialInspection.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zettamine.materialInspection.entities.MaterialActuals;
import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.MaterialInspLot;
import com.zettamine.materialInspection.model.CombinedList;
import com.zettamine.materialInspection.model.Search;
import com.zettamine.materialInspection.repository.MaterialInspLotRepository;
import com.zettamine.materialInspection.utils.SpaceRemover;

@Service
public class MaterialInspLotServiceImpl implements MaterialInspLotService {

	
	private MaterialInspLotRepository matInspLotRepo;
	
	private MaterialActualsService matActualsService;
	
	@Autowired
	private MaterialService matService;

	@Autowired
	private SpaceRemover spacesRemover;
	
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
		MaterialInspLot materialInspLot = null;
		try {
			materialInspLot = matInspLotRepo.findById(lotId).get();
		}catch(Exception ex) {
			return null;
		}
		return materialInspLot;
	
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



	@Override
	public List<MaterialInspLot> getMatInspBetweenDates(Date startDate, Date endDate, Date startDate2, Date endDate2) {
		return matInspLotRepo.findByCreatedOnBetween(startDate,endDate);
	}



	@Override
	public List<MaterialInspLot> getSearchBasedResults(Search search) {
		
		
		
		List<MaterialInspLot> matInspLots = matInspLotRepo.findByCreatedOnBetween(search.getFromDate(),search.getToDate() );
		if(!search.getPlantId().isEmpty()) {
			search.setPlantId(spacesRemover.removeSpaces(search.getPlantId()).toUpperCase());
			matInspLots = matInspLots.stream().filter(mil->mil.getPlant().getPlantId().equals(search.getPlantId())).collect(Collectors.toList());
		}
		if(!search.getMaterialId().isEmpty()) {
			search.setMaterialId(spacesRemover.removeSpaces(search.getMaterialId()).toUpperCase());
			matInspLots = matInspLots.stream().filter(mil->mil.getMaterial().getMaterialId().equals(search.getMaterialId())).collect(Collectors.toList());
		}
		if(!search.getStatus().isEmpty()) {
				matInspLots = matInspLots.stream().filter(mil->mil.getResult().equals(search.getStatus())).collect(Collectors.toList());
		}
		return matInspLots;
	}
	
	public List<CombinedList> getCombinedListOfMatActsAndChars(List<MaterialChars> matChars,List<MaterialActuals> matActuals){
		List<CombinedList> combinedList = new ArrayList<>();
//		matChars = matChars.stream().sorted((mc1,mc2)->mc1.getCharDesc().compareTo(mc2.getCharDesc())).collect(Collectors.toList());
//		matActuals = matActuals.stream().sorted((ma1,ma2)->ma1.getMatChars().getCharDesc().compareTo(ma2.getMatChars().getCharDesc())).collect(Collectors.toList());
		matChars = matChars.stream().sorted((mc1,mc2)->mc1.getCharId()-mc2.getCharId()).collect(Collectors.toList());
		matActuals = matActuals.stream().sorted((ma1,ma2)->ma1.getMatChars().getCharId() -ma2.getMatChars().getCharId()).collect(Collectors.toList());
		for (int i = 0; i < matActuals.size(); i++) {
            MaterialChars matChar = matChars.get(i);
            MaterialActuals matActual = matActuals.get(i);
            combinedList.add(new CombinedList(matChar, matActual));
        }
		return combinedList;
	}
	
	
	
	
}
