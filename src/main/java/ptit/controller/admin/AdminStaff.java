package ptit.controller.admin;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String staff(ModelMap model,
    		@RequestParam(value = "message",defaultValue = "") String message,
    		@RequestParam(value = "search",defaultValue = "") String search) {
		if (!search.equals("")) {
			model.addAttribute("staffs", staffService.findStaff(search));
		}else {
			model.addAttribute("staffs", staffService.findAll());
		}
		model.addAttribute("search",search);
        model.addAttribute("message",message);
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
			Account account, ModelMap model, SessionStatus stas,RedirectAttributes redirectAttributes ){

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
	    String encodedPassword = passwordEncoder.encode(staff.getAccount().getPassword());
	    staff.getAccount().setPassword(encodedPassword);
		Calendar calendar = Calendar.getInstance();
		staff.getAccount().setCreate_date(calendar);
		staff.getAccount().setStatus(true);
		staffService.save(staff);
		stas.setComplete();
		
		redirectAttributes.addAttribute("message","Create account complete!");
		return "redirect:/admin/staff";
	}
	@GetMapping("/admin/editstaff")
	public String updateStaff(@RequestParam(value = "staffid") Long staffID, ModelMap model) {
		List<Permission> permissions = permissionService.findAll();
		model.addAttribute("permissions", permissions);
		Staff staff = staffService.findById(staffID).get();

		model.addAttribute("staff", staff);
		return "admin/editStaff";
	}
	@PostMapping("/admin/editstaff")
	public String editStaff(@Valid Staff staff, BindingResult result,ModelMap model,SessionStatus stas, RedirectAttributes attributes){
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
		
		stas.setComplete();
		
		attributes.addAttribute("message","Change information success!");
		return "redirect:/admin/staff";
	}
	@GetMapping("/admin/deletestaff/{staffID}")
	public String deleteStaff(@PathVariable Long staffID, SessionStatus stas, Model model,RedirectAttributes attributes) {
		Staff staff = staffService.findById(staffID).get();
		
		if(staff.getAccount().getPermission().getName().equals("ROLE_MANAGER")) {
			attributes.addAttribute("message","Cannot disable administrator account");
			return"redirect:/admin/staff";
		}
		if(staff.getAccount().getStatus() | staff.getAccount().getStatus()==null) {
			staff.getAccount().setStatus(false);
			
			attributes.addAttribute("message","Account deactivation successful");
		} else {
			staff.getAccount().setStatus(true);
			attributes.addAttribute("message","Account activation successful");
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
	
	@GetMapping("/resetPasswordStaff")
	public String resetPasswordStaff(@RequestParam("id") long id, RedirectAttributes attributes) {
		Account account = accountService.findById(id);
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode("123456");
	    account.setPassword(encodedPassword);
	    accountService.save(account);
	    
	    attributes.addAttribute("message","Reset password for "+account.getStaff().getFullname()+" complete! Password default is '123456'");
        return "redirect:/admin/staff";
	}
	
}
