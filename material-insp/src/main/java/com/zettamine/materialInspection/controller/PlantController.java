package com.zettamine.materialInspection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.zettamine.materialInspection.entities.Plant;
import com.zettamine.materialInspection.entities.Vendor;
import com.zettamine.materialInspection.service.PlantService;

@Controller
@RequestMapping("/user")
public class PlantController {

	private PlantService plantService;

	public PlantController(PlantService plantService) {
		super();
		this.plantService = plantService;
	}

	@GetMapping("/add-plant")
	public String getMethodName(Model model) {
		model.addAttribute("plant", new Plant());
		return "add-plant-page";
	}

	@PostMapping("/save-plant")
	public String savePlant(Plant plant, Model model) {
		plant.setCity(plant.getCity().toUpperCase().trim());
		plant.setName(plant.getName().toUpperCase().trim());
		plant.setPlantId(plant.getPlantId().toUpperCase().trim());
		plant.setState(plant.getState().toUpperCase().trim());
		plantService.addPlant(plant);
		model.addAttribute("message", "Plant is added or updated successfully");
		return "add-plant-page";

	}

	@GetMapping("/show-plants")
	public String showAllVendors(Model model) {
		List<Plant> listOfPlants = plantService.getAllPlants();
		model.addAttribute("plants", listOfPlants);
		return "view-plants";
	}

	@GetMapping("/delete-plant/{id}")
	public RedirectView deleteVendor(@PathVariable("id") String id, Model model) {
		plantService.removePlant(id);
		return new RedirectView("/zettamine/user/show-plants");
	}

	@GetMapping("/update-plant/{id}")
	public String vendorPageUpdate(@PathVariable("id") String id, Model model) {
		Plant plant = plantService.getPlantById(id);
		model.addAttribute("plant", plant);
		return "add-plant-page";
	}
}
