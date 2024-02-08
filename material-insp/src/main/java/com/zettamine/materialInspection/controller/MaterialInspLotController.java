package com.zettamine.materialInspection.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zettamine.materialInspection.entities.Material;
import com.zettamine.materialInspection.entities.MaterialInspLot;
import com.zettamine.materialInspection.entities.Plant;
import com.zettamine.materialInspection.entities.Vendor;
import com.zettamine.materialInspection.service.MaterialInspLotService;
import com.zettamine.materialInspection.service.MaterialService;
import com.zettamine.materialInspection.service.PlantService;
import com.zettamine.materialInspection.service.VendorService;

@Controller
@RequestMapping("/user")
public class MaterialInspLotController {

	private MaterialService matService;

	private VendorService vendorService;

	private PlantService plantService;

	private MaterialInspLotService matInspLotService;

	@Autowired
	public MaterialInspLotController(MaterialService matService, VendorService vendorService, PlantService plantService,
			MaterialInspLotService matInspLotService) {
		super();
		this.matService = matService;
		this.vendorService = vendorService;
		this.plantService = plantService;
		this.matInspLotService = matInspLotService;
	}

	@GetMapping("/add-material-insp-lot")
	public String addInspectionLot(Model model) {


		List<Material> materials = matService.getAllMaterials();
		model.addAttribute("materials", materials);

		List<Vendor> vendors = vendorService.getAllVendors();
		model.addAttribute("vendors", vendors);

		List<Plant> plants = plantService.getAllPlants();
		model.addAttribute("plants", plants);

		return "add-insp-lot";
	}

	@PostMapping("/save-material-insp-lot")
	public String saveInspectionLot(@RequestParam("material") String matId, @RequestParam("plant")String plantId, @RequestParam("vendor")String vendorId,
			@RequestParam("crDate")Date crDate, @RequestParam("inspStDate")Date inspStDate, Model model) {
		model.addAttribute("message");
		
		MaterialInspLot materialInspLot = new MaterialInspLot();
		
		
		materialInspLot.setPlant(plantService.getPlantById(plantId));
		materialInspLot.setMaterial(matService.getMaterialById(matId));
		materialInspLot.setVendor(vendorService.getVendorById(Integer.valueOf(vendorId)));
		
		materialInspLot.setCreatedOn(crDate);
		materialInspLot.setInspStartDate(inspStDate);
		
		MaterialInspLot savedMatLot =  matInspLotService.addLot(materialInspLot);
		
		matInspLotService.addLot(materialInspLot);
		return "redirect:/user/add-material-insp-lot";
	}
	
	

}
