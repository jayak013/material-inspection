package com.zettamine.materialInspection.service;

import java.sql.Date;
import java.util.List;

import com.zettamine.materialInspection.entities.Material;
import com.zettamine.materialInspection.entities.MaterialActuals;
import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.MaterialInspLot;
import com.zettamine.materialInspection.model.CombinedList;
import com.zettamine.materialInspection.model.Search;

public interface MaterialInspLotService {

	MaterialInspLot addLot(MaterialInspLot materialInspLot);

	MaterialInspLot getByLotId(Integer lotId);

	List<MaterialInspLot> getAll();

	boolean matActalsPassOrFail(MaterialInspLot matInspLot);

	List<MaterialChars> getMatCharsForActuals(Integer lotId);

	List<MaterialInspLot> getMatInspBetweenDates(Date startDate, Date endDate, Date startDate2, Date endDate2);

	List<MaterialInspLot> getSearchBasedResults(Search search);
	
	List<CombinedList> getCombinedListOfMatActsAndChars(List<MaterialChars> matChars,List<MaterialActuals> matActuals);
	
}
