package com.zettamine.materialInspection.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;


import com.zettamine.materialInspection.entities.Vendor;


public interface VendorRepository extends JpaRepository<Vendor, Serializable> {
	
}
