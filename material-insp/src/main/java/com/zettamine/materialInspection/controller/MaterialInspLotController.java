package com.zettamine.materialInspection.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.zettamine.materialInspection.model.Search;
import com.zettamine.materialInspection.service.MaterialInspLotService;
import com.zettamine.materialInspection.service.MaterialService;
import com.zettamine.materialInspection.service.PlantService;
import com.zettamine.materialInspection.service.UserService;
import com.zettamine.materialInspection.service.VendorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;



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
			return "redirect:/user/search-insp-lots";

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

//	@GetMapping("/show-inspection-lots")
//	public String showAllInspectionLots(Model model) {
//
//		List<MaterialInspLot> matInspLots = matInspLotService.getAll();
//		model.addAttribute("matInspLots", matInspLots);
//		model.addAttribute("matInspLotService",matInspLotService);
//		return "view-insp-lots";
//
//	}
	
	@GetMapping("/show-results/{lotId}")
	public String showResults(@PathVariable Integer lotId , Model model) {
		
		MaterialInspLot matInspLot = matInspLotService.getByLotId(lotId);
		List<MaterialActuals> matActuals = matInspLot.getMatActuals();
		List<MaterialChars> matChars = matInspLot.getMaterial().getMatChars();
		
		List<CombinedList> combinedList = matInspLotService.getCombinedListOfMatActsAndChars(matChars, matActuals);
		
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
		
		return "redirect:/user/search-insp-lots";
	}

	@GetMapping("/search-insp-lots")
	public String search(Model model) {
		model.addAttribute("search", new Search());
//		model.addAttribute("matInspLots", matInspLotService.getAll());
//		model.addAttribute("materials", matService.getAllMaterials());
//		model.addAttribute("plants", plantService.getAllPlants());
		return "search-form";
	}
	
	@PostMapping("/inspection-lot-results")
	public String postMethodName( Search search, Model model) {
		
		
		List<MaterialInspLot> matInspLots = matInspLotService.getSearchBasedResults(search);
		
		model.addAttribute("matInspLots", matInspLots);
		model.addAttribute("matInspLotService",matInspLotService);
		
		return "view-insp-lots-search";
	}
	
	@GetMapping(path = "/search-lotId")
	public String searchBasedOnLotId(@RequestParam("lotId") Integer lotId,Model model) {
		
		MaterialInspLot matInspLot = matInspLotService.getByLotId(lotId);
		System.out.println(matInspLot);
		List<MaterialInspLot> matInspLots = new ArrayList<>();
		if(matInspLot!=null) {
			matInspLots.add(matInspLot);
			model.addAttribute("matInspLots", matInspLots);
			model.addAttribute("matInspLotService",matInspLotService);
		}else model.addAttribute("message", "No Records Found");
		return "view-insp-lots-search";
	}
	
	
}
;