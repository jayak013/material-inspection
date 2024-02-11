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
import com.zettamine.materialInspection.entities.User;
import com.zettamine.materialInspection.service.MaterialCharsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/user")
public class MaterialCharController {
	
	private MaterialCharsService matCharsService;

	@Autowired
	public MaterialCharController(MaterialCharsService matCharsService) {
		super();
		this.matCharsService = matCharsService;
	}

	
	@GetMapping("/add-material-chars/{matid}")
	public String addMaterialDetails(@PathVariable("matid") String matId, Model model,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		System.out.println(user);
		Material m = new Material();
		m.setMaterialId(matId);
		MaterialChars materialChars = new MaterialChars();
		materialChars.setMaterial(m);
		model.addAttribute("matChars", materialChars);
		model.addAttribute("matid", matId);
		return "add-material-chars";
	}
	
	@PostMapping("/save-material-chars")
	public String saveMaterialChars(MaterialChars matChars,Model model) {
		MaterialChars materialChars = matCharsService.addMaterialChar(matChars);
		String uri = "/user/add-material-chars/"+matChars.getMaterial().getMaterialId();	
		return "redirect:"+uri;
	}
	
	@GetMapping("/update-material-chars/{charid}")
	public String updateMaterialDetails(@PathVariable("charid") Integer charId, Model model) {
		MaterialChars matChars =  matCharsService.getByCharId(charId);
		model.addAttribute("matChars", matChars);
		return "add-material-chars";
	}
	
	@GetMapping("/delete-material-chars/{matid}/{charid}")
	public RedirectView deleteMaterialDetails(@PathVariable("charid") Integer charId,@PathVariable("matid") String matId) {
		matCharsService.removeMatChars(charId);
		return new RedirectView("/zettamine/user/show-material-chars/"+matId);
	}
}
