package com.zettamine.materialInspection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.zettamine.materialInspection.entities.Material;
import com.zettamine.materialInspection.entities.MaterialChars;
import com.zettamine.materialInspection.entities.Plant;
import com.zettamine.materialInspection.service.MaterialService;

@Controller
@RequestMapping("/user")
public class MaterialController {
	
	private MaterialService matService;

	@Autowired
	public MaterialController(MaterialService matService) {
		super();
		this.matService = matService;
	}
	
	@GetMapping("/add-material")
	public String addMaterial(Model model) {
		model.addAttribute("material", new Material());
		return "add-material-page";
	}
	
	@PostMapping("/save-material")
	public String saveMaterial(Material material, Model model) {
		Material savedMaterial = matService.addMaterial(material);

		if (savedMaterial != null) {
			model.addAttribute("message", "Material is added or updated successfully");
			return "add-material-page";
		}

		model.addAttribute("message", "failed to add the vendor");
		return "add-material-page";
	}
	
	@GetMapping("/show-materials")
	public String showAllMaterials(Model model) {
		List<Material> listOfMaterials = matService.getAllMaterials();
		model.addAttribute("materials", listOfMaterials);
		return "view-materials";
	}
	
//	@PostMapping("/search-material/{id}")
//	public String showAllVendors(@RequestParam("id") Integer id,Model model) {
//		Material material = matService.getMaterialById(id);
//		model.addAttribute("materials",material );
//		return "view-materials";
//	}
	
	@GetMapping("/delete-material/{id}")
	public RedirectView deleteMaterial(@PathVariable("id") String id, Model model) {
		matService.removeMaterial(id);
		return new RedirectView("/zettamine/user/show-materials");
	}
	
	@GetMapping("/update-material/{id}")
	public String materialUpdate(@PathVariable("id") String id,Model model) {
		Material material = matService.getMaterialById(id);
		model.addAttribute("material", material);
		return "add-material-page";
	}
	
	@GetMapping("/show-material-chars/{matid}")
	public String showAllMaterialChars(@PathVariable("matid") String matId,Model model) {
		Material material = matService.getMaterialById(matId);
		model.addAttribute("material", material);
		return "view-material-chars";
	}

}
