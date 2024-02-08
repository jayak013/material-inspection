package com.zettamine.materialInspection.service;

import java.util.List;

import com.zettamine.materialInspection.entities.Vendor;

public interface VendorService {
	
	Vendor addVendor(Vendor vendor);
	
	List<Vendor> getAllVendors();
	
	void removeVendor(Integer id);
	
	Vendor getVendorById(Integer id);
	
}
