package ptit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.entity.Staff;
import ptit.service.StaffService;


@Controller
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	
	
	
	@RequestMapping( "/staff")
    public String staff(ModelMap model) {
        model.addAttribute("staff", staffService.findAll());
        return "staff";
    }
	@GetMapping("/addStaff")
	public String addstaff(ModelMap model) {
		//create model atribute to form data
		Staff staff = new Staff();
		model.addAttribute("staff", staff);
		return"addStaff";
	}
	@PostMapping("/saveStaff")
	public String saveStaff(@ModelAttribute("staff") Staff staff){
		
		// save staff to database
		staffService.save(staff);
		return"redirect:/staff";
	}
	@GetMapping("/updateStaff/{staffID}")
	public String updateStaff(@PathVariable(value ="staffID") Long staffID, ModelMap model) {
		// get staff frp, the service
		Staff staff = staffService.findById(staffID);
		// set staff as a model attribute 
		model.addAttribute("staff", staff);
		
		return"updateStaff";
	}
	@GetMapping("/deleteStaff/{staffID}")
	public String deleteStaff(@PathVariable(value ="staffID") Long staffID) {
		this.staffService.deleteById(staffID);
		return"redirect:/staff";
	}
	@GetMapping("/sortstaff")
	public String sortStaff(ModelMap model, @RequestParam("field") Optional<String> field) {
		Sort sort=Sort.by(Direction.ASC, field.orElse("staffID"));
		List<Staff> ls = staffService.findAll(sort);
		model.addAttribute("staff", ls);
		return"staff";
	}
//	@GetMapping("/sortstaff/page")
//	public String paginate(ModelMap model, @RequestParam("p") Optional<Integer> p) {
//		Pageable pageable = PageRequest.of(p.orElse(0), 5);
//		Page<Staff> page = staffService.findAll(pageable);
//		model.addAttribute("staff", page);
//		return"staff";
//	}
}
