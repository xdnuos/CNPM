package ptit.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
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
	
	@GetMapping("/admin/staff")
    public String staff(ModelMap model) {
        model.addAttribute("staffs", staffService.findAll());
        return "admin/staff";
    }
	@GetMapping("/admin/addstaff")
	public String addstaff(ModelMap model) {
		List<Permission> permissions = permissionService.findAll();
		Account account = new Account();
		Staff staff = new Staff();
		staff.setAccount(account);
		model.addAttribute("permissions", permissions);
		model.addAttribute("staff", staff);
		return"admin/addStaff";
	}
	@PostMapping("/admin/addstaff")
	public String saveStaff(@Valid Staff staff, BindingResult result,
			Account account, ModelMap model){

		if (result.hasErrors()) {
			model.addAttribute("staffID", staff.getStaffID());
			List<Permission> permissions = permissionService.findAll();
			model.addAttribute("permissions", permissions);
		    return "admin/addstaff";
		  }
		if(!accountService.checkEmail(staff.getAccount().getEmail())) {
			model.addAttribute("staffID", staff.getStaffID());
			model.addAttribute("emailerror", "Email already in use");
			List<Permission> permissions = permissionService.findAll();
			model.addAttribute("permissions", permissions);
		    return "admin/addstaff";
		}
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    account.setStatus(true);
	    String encodedPassword = passwordEncoder.encode(staff.getAccount().getPassword());
	    staff.getAccount().setPassword(encodedPassword);
		Calendar calendar = Calendar.getInstance();
		staff.getAccount().setCreate_date(calendar);
		staffService.save(staff);
		return "redirect:/admin/staff";
	}
	@GetMapping("/admin/editstaff")
	public String updateStaff(@RequestParam(value = "staffid") Long staffID, ModelMap model) {
		List<Permission> permissions = permissionService.findAll();
		model.addAttribute("permissions", permissions);
		Staff staff = staffService.findById(staffID).get();
		
		// set staff as a model attribute 
		model.addAttribute("staff", staff);
		model.addAttribute("message", "Staff Update");
		return "admin/editStaff";
	}
	@PostMapping("/admin/editstaff")
	public String editStaff(@Valid Staff staff, BindingResult result,ModelMap model){
		if (result.hasErrors()) {
			model.addAttribute("staffID", staff.getStaffID());
		    return "admin/updatestaff";
		  }
		Account account = accountService.findById(staff.getAccount().getAccountID());
	
		account.setPermission(staff.getAccount().getPermission());
		account.setEmail(staff.getAccount().getEmail());
		
		staff.setStaffID(staff.getStaffID());
		staff.setAccount(account);
//		staff.setBirth(birth);
		staffService.save(staff);
		return "redirect:/admin/staff";
	}
	@GetMapping("/admin/deletestaff/{staffID}")
	public String deleteStaff(@PathVariable Long staffID, SessionStatus stas, Model model) {
		Staff staff = staffService.findById(staffID).get();
		
		if(staff.getAccount().getPermission().getName().equals("ROLE_MANAGER")) {
			model.addAttribute("error","Không thể vô hiệu hóa tài khoản quản trị");
			return"redirect:/admin/staff";
		}
		if(staff.getAccount().getStatus()) {
			staff.getAccount().setStatus(false);
		} else {
			staff.getAccount().setStatus(true);
		}
		staffService.save(staff);
		stas.setComplete();
		return"redirect:/admin/staff";
	}
	@GetMapping("/admin/sortstaff")
	public String sortStaff(ModelMap model, @RequestParam Optional<String> field) {
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
