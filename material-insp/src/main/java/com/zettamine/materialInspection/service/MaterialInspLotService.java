package com.zettamine.materialInspection.service;

import java.util.List;

import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.MaterialInspLot;

public interface MaterialInspLotService {

	MaterialInspLot addLot(MaterialInspLot materialInspLot);

	MaterialInspLot getByLotId(Integer lotId);

	List<MaterialInspLot> getAll();

	boolean matActalsPassOrFail(MaterialInspLot matInspLot);

	List<MaterialChars> getMatCharsForActuals(Integer lotId);
}
