package com.zettamine.materialInspection.service;

import java.util.List;

import com.zettamine.materialInspection.entities.MaterialChars;

public interface MaterialCharsService {

	MaterialChars addMaterialChar(MaterialChars matChars);

	MaterialChars getByCharId(Integer charId);

	void removeMatChars(Integer charId);

}
