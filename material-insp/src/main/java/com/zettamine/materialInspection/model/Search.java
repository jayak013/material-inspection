package com.zettamine.materialInspection.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
	
	private Date fromDate = Date.valueOf(LocalDate.now().minus(90, ChronoUnit.DAYS));
	private Date toDate = Date.valueOf(LocalDate.now());
	private String materialId;
	private String plantId;
	private String status;
	
}
