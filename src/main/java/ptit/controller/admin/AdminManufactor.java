package ptit.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.entity.Manufacturer;
import ptit.repository.ManufactureDAO;

public class AdminManufactor {
	@Autowired
	ManufactureDAO manufactureDAO;
	
//	manufactor---------------------------------------------
	@GetMapping(value = "/admin/addManufactor")
	public String manufactor(Model model) {
		List<Manufacturer> manufacturers = manufactureDAO.findAll();
		model.addAttribute("manufacturers",manufacturers);
		return "admin/addManufactor";
	}
	
	@PostMapping(value = "/admin/addManufactor")
	public String addManufactor(Model model,
			@RequestParam String name) {
		Manufacturer newManufacturer = new Manufacturer();
		newManufacturer.setName(name);
		manufactureDAO.save(newManufacturer);
		return "redirect:/admin/addManufactor";
	}
	
	@GetMapping(value = "/admin/editManufactor")
	public String editManufactor(Model model,
			@RequestParam int id) {
		Manufacturer manufacturer =manufactureDAO.findByManufacturerID(id);
		model.addAttribute("manufacturer",manufacturer);
		return "admin/editManufactor";
	}
	@PostMapping(value = "/admin/editManufactor")
	public String saveManufactor(Manufacturer manufacturer, Model model,
			@RequestParam int id) {
		manufacturer.setManufacturerID(id);
		manufactureDAO.save(manufacturer);
		return "redirect:/admin/addManufactor";
	}
}
