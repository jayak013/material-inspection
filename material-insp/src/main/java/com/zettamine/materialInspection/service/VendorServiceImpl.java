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
	
	@Autowired
	public VendorServiceImpl(VendorRepository vendorRepo) {
		super();
		this.vendorRepo = vendorRepo;
	}



	@Override
	public Vendor addVendor(Vendor vendor) {
		
		vendor.setName(vendor.getName().toLowerCase());
		vendor.setEmail(vendor.getEmail().toLowerCase());
		vendor.setCity(vendor.getCity().toLowerCase());
		vendor.setState(vendor.getState().toLowerCase());
		
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
