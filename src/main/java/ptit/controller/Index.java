package ptit.controller;
import java.util.Calendar;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import ptit.entity.Account;
import ptit.entity.Customer;
import ptit.entity.Permission;
import ptit.service.AccountService;
import ptit.service.CustomerSerivce;

@Controller
public class Index {
	@Autowired
	AccountService accountService;
	@Autowired
	CustomerSerivce customerSerivce;
	
	@GetMapping("/login")
	public String login() {
		return"login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		Customer neCustomer = new Customer();
		Account account = new Account();
		neCustomer.setAccount(account);
		model.addAttribute("customer", new Customer());
	    model.addAttribute("ERROR","o");
	    model.addAttribute("phoneError","o");
	    return "register";
	}
	
	@PostMapping("/register")
	public String processRegister(@Valid Customer customer,BindingResult result, SessionStatus status, ModelMap model) {
		  if (result.hasErrors()) {
//			  System.out.print(result.getAllErrors());
			    return "register";
			  }
		  
		  if(!customerSerivce.checkPhone(customer.getPhone())) {
		    	model.addAttribute("phoneError", "Số điện thoại đã tồn tại, vui lòng sử dụng số điện thoại khác");
		    	return "register";
		  }
		  
	    if(accountService.checkEmail(customer.getAccount().getEmail())) {
	    	Permission permission = new Permission();
	    	permission.setPermissionID((long) 3);
	    	Calendar calendar = Calendar.getInstance();
		    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		    String encodedPassword = passwordEncoder.encode(customer.getAccount().getPassword());
		    customer.getAccount().setPassword(encodedPassword);
		    customer.getAccount().setCreate_date(calendar);
		    customer.getAccount().setStatus(true);
		    customer.getAccount().setPermission(permission);
	    	customerSerivce.save(customer);
	    	status.setComplete();
	    } else {
	    	model.addAttribute("ERROR", "Email đã tồn tại, vui lòng sử dụng email khác");
	    	return "register";
	    }
	    
	    return "redirect:login";
	}
 
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return"login";
	}
	
	@GetMapping("/403")
	public String page403(HttpSession session) {
		return"403";
	}
}
