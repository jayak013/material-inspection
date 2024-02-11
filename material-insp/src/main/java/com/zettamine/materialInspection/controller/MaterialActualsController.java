package com.zettamine.materialInspection.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zettamine.materialInspection.entities.MaterialActuals;
import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.MaterialInspLot;
import com.zettamine.materialInspection.entities.User;
import com.zettamine.materialInspection.service.MaterialActualsService;
import com.zettamine.materialInspection.service.MaterialCharsService;
import com.zettamine.materialInspection.service.MaterialInspLotService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class MaterialActualsController {
	
	private MaterialActualsService matActualsService;
	
	private MaterialInspLotService matInspServiceLot;
	
	private MaterialCharsService matCharsService;


	public MaterialActualsController(MaterialActualsService matActualsService, MaterialInspLotService matInspServiceLot,
			MaterialCharsService matCharsService) {
		super();
		this.matActualsService = matActualsService;
		this.matInspServiceLot = matInspServiceLot;
		this.matCharsService = matCharsService;
	}

	@GetMapping("/add-material-actuals/{lotId}")
	public String addMaterialActuals(@PathVariable Integer lotId ,Model model,HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		
		MaterialActuals materialActuals = new MaterialActuals();
		MaterialInspLot matInspLot = matInspServiceLot.getByLotId(lotId);
		
		List<MaterialChars> matChars = matInspServiceLot.getMatCharsForActuals(lotId);
		
		materialActuals.setMatInsplot(matInspLot);		
		
		if(matChars.size()==0) {
			return "redirect:/user/save-material-insp-lot/"+matInspLot.getLotId();
		}
		
		model.addAttribute("matActuals", materialActuals);
		model.addAttribute("matCharsForActuals", matChars);
		
		return "add-actuals";
	}
	
	@PostMapping("/save-material-actuals")
	public String saveMaterialActuals(@RequestParam("charId") Integer charId ,
			@RequestParam("lotId") Integer lotId,@RequestParam("minMes") Double minMes, @RequestParam("maxMes") Double maxMes
			,Model model) {
		MaterialInspLot inspLot = matInspServiceLot.getByLotId(lotId);
		MaterialChars matChars = matCharsService.getByCharId(charId);
		MaterialActuals materialActuals = new MaterialActuals();
		materialActuals.setMatInsplot(inspLot);
		materialActuals.setMatChars(matChars);
		materialActuals.setMinMes(minMes);
		materialActuals.setMaxMes(maxMes);
		
		MaterialActuals materialActualsSaved = matActualsService.addMaterialActuals(materialActuals);
		
		return "redirect:/user/add-material-actuals/"+lotId;
	}
	
	
}
