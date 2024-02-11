package com.zettamine.materialInspection.service;

import java.util.List;

import com.zettamine.materialInspection.entities.MaterialActuals;
import com.zettamine.materialInspection.entities.MaterialInspLot;

public interface MaterialActualsService {

	List<MaterialInspLot> getAllInspLot();

	MaterialActuals addMaterialActuals(MaterialActuals materialActuals);

}
