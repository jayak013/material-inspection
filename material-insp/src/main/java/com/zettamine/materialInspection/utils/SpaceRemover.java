package com.zettamine.materialInspection.utils;

import org.springframework.stereotype.Component;

@Component
public class SpaceRemover {
	
	public String removeSpaces(String s) {
		return s.replaceAll(" ", "");
	}
	
}
