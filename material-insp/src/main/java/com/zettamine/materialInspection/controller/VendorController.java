package com.zettamine.materialInspection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.zettamine.materialInspection.entities.Vendor;
import com.zettamine.materialInspection.service.VendorService;

@Controller
@RequestMapping("/user")
public class VendorController {
	
	private VendorService vendorService;
	
	
	@Autowired
	public VendorController(VendorService vendorService) {
		super();
		this.vendorService = vendorService;
	}


	@GetMapping("/add-vendor")
	public String vendorPage(Model model) {
		model.addAttribute("vendor", new Vendor());
		return "add-vendor-page";
	}
	
	@PostMapping("/save-vendor")
	public String saveVendor(Vendor vendor,Model model) {
		Vendor savedVendor = vendorService.addVendor(vendor);
		
		if(savedVendor!=null) {
			model.addAttribute("message", "Vendor is added or updated successfully");
			return "add-vendor-page";
		}
		
		model.addAttribute("message", "failed to add the vendor");
		return "add-vendor-page";
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("vendor", new Vendor());
		return "home";
	}
	
	@GetMapping("/show-vendors")
	public String showAllVendors(Model model) {
		List<Vendor> listOfVendors = vendorService.getAllVendors();
		model.addAttribute("vendors", listOfVendors);
		return "view-vendors";
	}
	
	@GetMapping("/delete-vendor/{id}")
	public RedirectView deleteVendor(@PathVariable("id") Integer id, Model model) {
		vendorService.removeVendor(id);
		return new RedirectView("/zettamine/user/show-vendors");
	}
	

	@GetMapping("/update/{id}")
	public String vendorPageUpdate(@PathVariable("id") Integer id,Model model) {
		Vendor vendor = vendorService.getVendorById(id);
		model.addAttribute("vendor", vendor);
		return "add-vendor-page";
	}
	
}
