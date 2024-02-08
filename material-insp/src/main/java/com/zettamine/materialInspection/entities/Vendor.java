package com.zettamine.materialInspection.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vendors")
public class Vendor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_id")
	@SequenceGenerator(name = "vendor_id",sequenceName = "vendor_sequence",initialValue = 5001,allocationSize = 1)
	private Integer vendorId;
	private String name;
	private String mobile;
	private String email;
	private String city;
	private String state;
	@Column(name = "status")
	private String status = "active";
	
	@OneToMany(mappedBy = "vendor",cascade = CascadeType.ALL)
	private List<MaterialInspLot> materialInspLots = new ArrayList<>();
}
