package ptit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import ptit.entity.Account;
import ptit.entity.Permission;
import ptit.entity.Staff;
import ptit.service.AccountService;
import ptit.service.PermissionService;
import ptit.service.StaffService;

@Controller
public class AdminStaff {	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value = "/admin/staff")
    public String staff(ModelMap model) {
        model.addAttribute("staffs", staffService.findAll());
        return "admin/staff";
    }
	@GetMapping("/admin/addstaff")
	public String addstaff(ModelMap model) {
		List<Permission> permissions = permissionService.findAll();
		model.addAttribute("permissions", permissions);
		
		Staff staff = new Staff();
		model.addAttribute("staffs", staff);
		model.addAttribute("message", "Staff Add");
		return"admin/addOrUpdate";
	}
	@PostMapping("/admin/saveOrUpdate")
	public String saveStaff(@Valid @ModelAttribute("staffs") Staff staff, BindingResult result,ModelMap model,
			Account account){
		
		if(staff.getStaffID()!= null && staff.getStaffID()> 0) {
			model.addAttribute("message", "Update success!");
		}else {
			model.addAttribute("message", "Add success!");
		}
		
		if (result.hasErrors()) {
		    return "admin/addOrUpdate";
		  }
		// save staff to database
		staffService.save(staff);
		return"redirect:/admin/staff";
	}
	@GetMapping("/admin/updatestaff/{staffID}")
	public String updateStaff(@PathVariable(value ="staffID") Long staffID, ModelMap model
			 ) {
		List<Permission> permissions = permissionService.findAll();
		Optional<Staff> staff = staffService.findById(staffID);
		
		// set staff as a model attribute 
		model.addAttribute("staffs", staff);
		model.addAttribute("permissions", permissions);
		model.addAttribute("message", "Staff Update");
		return"admin/addOrUpdate";
	}
	@GetMapping("/admin/deletestaff/{staffID}")
	public String deleteStaff(@PathVariable(value ="staffID") Long staffID) {
		this.staffService.deleteById(staffID);
		return"redirect:/admin/staff";
	}
	@GetMapping("/admin/sortstaff")
	public String sortStaff(ModelMap model, @RequestParam("field") Optional<String> field) {
		Sort sort=Sort.by(Direction.ASC, field.orElse("staffID"));
		List<Staff> ls = staffService.findAll(sort);
		model.addAttribute("staffs", ls);
		return"admin/staff";
	}
//	@GetMapping("/sortstaff/page")
//	public String paginate(ModelMap model, @RequestParam("p") Optional<Integer> p) {
//		Pageable pageable = PageRequest.of(p.orElse(0), 5);
//		Page<Staff> page = staffService.findAll(pageable);
//		model.addAttribute("staff", page);
//		return"staff";
//	}
	
}
