package com.zettamine.materialInspection.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zettamine.materialInspection.entities.Vendor;
import com.zettamine.materialInspection.repository.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {
	
	
	private VendorRepository vendorRepo;
	
	public VendorServiceImpl(VendorRepository vendorRepo) {
		this.vendorRepo = vendorRepo;
	}



	@Override
	public Vendor addVendor(Vendor vendor) {
		
		vendor.setName(vendor.getName().toUpperCase().trim());
		vendor.setEmail(vendor.getEmail().toUpperCase().trim());
		vendor.setCity(vendor.getCity().toUpperCase().trim());
		vendor.setState(vendor.getState().toUpperCase().trim());
		
		return vendorRepo.save(vendor);
	}



	@Override
	public List<Vendor> getAllVendors() {
		List<Vendor> activeVendors = vendorRepo.findAll().stream().filter(v->v.getStatus().equals("active")).sorted((v1,v2)->v1.getVendorId()-v2.getVendorId()).collect(Collectors.toList());
		return activeVendors;
	}



	@Override
	public void removeVendor(Integer id) {
		Vendor vendor = vendorRepo.findById(id).get();
		vendor.setStatus("inactive");
		vendorRepo.save(vendor);
	}



	@Override
	public Vendor getVendorById(Integer id) {
		return vendorRepo.findById(id).get();
	}

}
