package com.zettamine.materialInspection.repository;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.materialInspection.entities.MaterialInspLot;

public interface MaterialInspLotRepository extends JpaRepository<MaterialInspLot, Serializable> {
	List<MaterialInspLot> findByCreatedOnBetween(Date startDate, Date endDate);
}
