package com.zettamine.materialInspection.entities;

import java.sql.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Scope("prototype")
public class MaterialInspLot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lot_id")
	@SequenceGenerator(name = "lot_id",sequenceName = "lot_sequence",initialValue = 4801,allocationSize = 1)
	private Integer lotId;
	
	private Date createdOn;
	@Column(name = "insp_st_date")
	private Date inspStartDate;
	private Date inspEndDate;
	private String result;
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "mat_id")
	private Material material;
	
	@ManyToOne
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;
	
	@ManyToOne
	@JoinColumn(name = "plant_id")
	private Plant plant;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "matInsplot")
	private List<MaterialActuals> matActuals;

}
