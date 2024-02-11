package com.zettamine.materialInspection.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zettamine.materialInspection.entities.Material;
import com.zettamine.materialInspection.entities.MaterialActuals;
import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.MaterialInspLot;
import com.zettamine.materialInspection.entities.Plant;
import com.zettamine.materialInspection.entities.User;
import com.zettamine.materialInspection.entities.Vendor;
import com.zettamine.materialInspection.model.CombinedList;
import com.zettamine.materialInspection.service.MaterialActualsService;
import com.zettamine.materialInspection.service.MaterialInspLotService;
import com.zettamine.materialInspection.service.MaterialService;
import com.zettamine.materialInspection.service.PlantService;
import com.zettamine.materialInspection.service.UserService;
import com.zettamine.materialInspection.service.VendorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class MaterialInspLotController {

	private MaterialService matService;

	private VendorService vendorService;

	private PlantService plantService;
	
	@Autowired
	private UserService userService;
	
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

	@GetMapping("/save-material-insp-lot/{lotId}")
	public String saveInspectionLot(@PathVariable Integer lotId,HttpServletRequest request,Model model) {

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
			
			MaterialInspLot matInspLot = matInspLotService.getByLotId(lotId);
			
			matInspLot.setInspEndDate(Date.valueOf(LocalDate.now()));
			if(matInspLotService.matActalsPassOrFail(matInspLot)) {
				matInspLot.setRemarks("No remarks");
				matInspLot.setResult("pass");
			}else {
				matInspLot.setResult("insp completed");
			}
			matInspLot.setUser(user);
			
			matInspLotService.addLot(matInspLot);
			return "redirect:/user/show-inspection-lots";

	}
	
	@GetMapping("/save-material-insp-lot" )
	public String saveInspectionLot(
			@RequestParam("material") String materialId, @RequestParam("plant") String plantId,
			@RequestParam("vendor") String vendorId, @RequestParam Date crDate,
			@RequestParam Date inspStDate, Model model, HttpServletRequest request) {

		model.addAttribute("message");
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		MaterialInspLot materialInspLot = new MaterialInspLot();

		materialInspLot.setPlant(plantService.getPlantById(plantId));
		materialInspLot.setMaterial(matService.getMaterialById(materialId));
		materialInspLot.setVendor(vendorService.getVendorById(Integer.valueOf(vendorId)));

		materialInspLot.setCreatedOn(crDate);
		materialInspLot.setInspStartDate(inspStDate);

		materialInspLot.setResult("under process");
		
		matInspLotService.addLot(materialInspLot);

		matInspLotService.addLot(materialInspLot);
		return "redirect:/user/add-material-insp-lot";
	}

	@GetMapping("/show-inspection-lots")
	public String showAllInspectionLots(Model model) {

		List<MaterialInspLot> matInspLots = matInspLotService.getAll();
		model.addAttribute("matInspLots", matInspLots);
		model.addAttribute("matInspLotService",matInspLotService);
		return "view-insp-lots";

	}
	
	@GetMapping("/show-results/{lotId}")
	public String showResults(@PathVariable Integer lotId , Model model) {
		
		MaterialInspLot matInspLot = matInspLotService.getByLotId(lotId);
		List<MaterialActuals> matActuals = matInspLot.getMatActuals();
		List<MaterialChars> matChars = matInspLot.getMaterial().getMatChars();
		
		List<CombinedList> combinedList = new ArrayList<>();
		for (int i = 0; i < matActuals.size(); i++) {
            MaterialChars matChar = matChars.get(i);
            MaterialActuals matActual = matActuals.get(matActuals.size()-i-1);
            combinedList.add(new CombinedList(matChar, matActual));
        }
		
		model.addAttribute("lotId", lotId);
		model.addAttribute("matInspLot", matInspLot);
		model.addAttribute("combinedList", combinedList);
		
		return "view-actuals";
	}
	
	@GetMapping("/save-remarks-results/{lotId}")
	public String saveRemarksAndResults(@PathVariable Integer lotId, Model model,@RequestParam("status") String status, @RequestParam("review") String review ) {
		
		MaterialInspLot matInspLot = matInspLotService.getByLotId(lotId);
		
		matInspLot.setRemarks(review);
		matInspLot.setResult(status);
		
		matInspLotService.addLot(matInspLot);
		model.addAttribute("matInspLot", matInspLot);
		
		return "redirect:/user/show-inspection-lots";
	}

}
