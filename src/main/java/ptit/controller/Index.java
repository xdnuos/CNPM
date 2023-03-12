package ptit.controller;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import ptit.entity.Account;
import ptit.entity.Customer;
import ptit.entity.Permission;
import ptit.entity.Staff;
import ptit.repository.AccountDAO;
import ptit.repository.StaffDAO;
import ptit.service.AccountService;
import ptit.service.CustomerSerivce;

@Controller
public class Index {
	@Autowired
	AccountService accountService;
	@Autowired
	CustomerSerivce customerSerivce;
	@Autowired
	StaffDAO staffDAO;
	
	@GetMapping("/login")
	public String login(Model model,@RequestParam(value = "error",defaultValue = "false") String error) {
		if(error.equals("true")) {
			model.addAttribute("error",true);
		}
		return"/admin/login";
	}

//	@GetMapping("/register")
//	public String showRegistrationForm(Model model) {
//		Customer neCustomer = new Customer();
//		Account account = new Account();
//		neCustomer.setAccount(account);
//		model.addAttribute("customer", new Customer());
//	    model.addAttribute("ERROR","o");
//	    model.addAttribute("phoneError","o");
//	    return "register";
//	}
	
//	@PostMapping("/register")
//	public String processRegister(@Valid Customer customer,BindingResult result, SessionStatus status, ModelMap model) {
//		  if (result.hasErrors()) {
////			  System.out.print(result.getAllErrors());
//			    return "register";
//			  }
//		  
//		  if(!customerSerivce.checkPhone(customer.getPhone())) {
//		    	model.addAttribute("phoneError", "Số điện thoại đã tồn tại, vui lòng sử dụng số điện thoại khác");
//		    	return "register";
//		  }
//		  
//	    if(accountService.checkEmail(customer.getAccount().getEmail())) {
//	    	Permission permission = new Permission();
//	    	permission.setPermissionID((long) 3);
//	    	Calendar calendar = Calendar.getInstance();
//		    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		    String encodedPassword = passwordEncoder.encode(customer.getAccount().getPassword());
//		    customer.getAccount().setPassword(encodedPassword);
//		    customer.getAccount().setCreate_date(calendar);
//		    customer.getAccount().setStatus(true);
//		    customer.getAccount().setPermission(permission);
//	    	customerSerivce.save(customer);
//	    	status.setComplete();
//	    } else {
//	    	model.addAttribute("ERROR", "Email đã tồn tại, vui lòng sử dụng email khác");
//	    	return "register";
//	    }
//	    
//	    return "redirect:login";
//	}
 
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return"login";
	}
	
	@GetMapping("/403")
	public String page403(HttpSession session) {
		return"403";
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/admin/accountInfo")
	public String accountInfo(Model model, @RequestParam("id") String email,RedirectAttributes redirectAttributes) {
		Account account = accountService.findByEmail(email);
		model.addAttribute("account",account);
		return "/admin/accountInfo";
	}
	
	@PostMapping("/admin/accountInfo")
	public String saveAccountInfo(Account account, Model model, SessionStatus status, @RequestParam("bisss") @DateTimeFormat(pattern ="yyyy-MM-dd") Date birth,
			@RequestParam("newpassword") String newPassword) {
		Staff staff = staffDAO.findById(account.getStaff().getStaffID()).get();
		staff.setCccd(account.getStaff().getCccd());
//		staff.setBirth(account.getStaff().getBirth());
		staff.setFullname(account.getStaff().getFullname());
		staff.setPhone(account.getStaff().getPhone());
		staff.setSex(account.getStaff().getSex());
		staff.setBirth(birth);
		//nhung thu k thay doi
		account.setAccountID(account.getAccountID());
		account.setPermission(account.getPermission());
		account.setPassword(account.getPassword());
		account.setStatus(true);
		if(newPassword!="" && newPassword!=null) {
		    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		    String encodedPassword = passwordEncoder.encode(newPassword);
		    account.setPassword(encodedPassword);
		}
		//thay doi staff
		account.setStaff(staff);
		accountService.save(account);
		
		status.setComplete();
		return "redirect:/admin";
	}
}
