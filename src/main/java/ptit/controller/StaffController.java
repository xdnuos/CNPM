package ptit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ptit.entity.Staff;
import ptit.service.StaffService;


@Controller
public class StaffController {
	@Autowired
	private StaffService staffService;
	
	
	
	@RequestMapping( "/staff")
    public String Staff(ModelMap model) {
        model.addAttribute("availableStaff", staffService.findAll());
        return "staff";
    }
//	@RequestMapping("/saveOrUpdate")
//	public String saveOrUpdate(ModelMap model, @ModelAttribute("STAFF") Staff staff) {
//		staffService.save(staff);
//		return"register-stff";
//	}
//	@RequestMapping("/list")
//	public String list(ModelMap model) {
//		staffService.findAll();
//		return"staff";
//	}
//	@PostMapping("/logout")
//	public String logout() {
//		return"login";
//	}
}
