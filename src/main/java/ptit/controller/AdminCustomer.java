package ptit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;
import ptit.entity.Account;
import ptit.entity.Customer;
import ptit.service.AccountService;
import ptit.service.CustomerSerivce;


@Controller
public class AdminCustomer {
	@Autowired
	private CustomerSerivce customerSerivce;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
//	@RequestMapping("/login")
//	public String login() {
//		return"login";
//	}
//	@RequestMapping("/register")
//	public String register() {
//		return"register";
//	}
//	@GetMapping("/register")
//	public String showRegistrationForm(Model model) {
//		model.addAttribute("customers", new Customer());
//	    model.addAttribute("account", new Account());
//	    model.addAttribute("ERROR","");
//	    return "register";
//	}
//	@PostMapping("/checkregister")
//	public String processRegister(@ModelAttribute("customers") Customer customer,Account account, SessionStatus status, ModelMap model) {
//	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	    String encodedPassword = passwordEncoder.encode(account.getPassword());
//	    account.setPassword(encodedPassword);
//	    //account.setPermission(getName = "Customer");
//	    if(accountService.checkEmail(account.getEmail())) {
//	    	accountService.save(account);
//	    	customerSerivce.save(customer);
//	    	status.setComplete();
//	    } else {
//	    	model.addAttribute("ERROR", "Email đã tồn tại, vui lòng sử dụng email khác");
//	    	return "register";
//	    }
//	    
//	    return "login";
//	}
// 
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("email");
//		return"login";
//	}
//	@RequestMapping(value = "/admin/customer")
//    public String staff(ModelMap model) {
//		List<Customer> customer = customerSerivce.findAll();
//        model.addAttribute("customers", customer);
//        return "admin/customer";
//    }
	
//	@GetMapping("/admin/sortcustomer")
//	public String sortStaff(ModelMap model, @RequestParam("field") Optional<String> field) {
//		Sort sort=Sort.by(Direction.ASC, field.orElse("staffID"));
//		List<Customer> ls = customerSerivce.findAll(sort);
//		model.addAttribute("staffs", ls);
//		return"admin/customer";
//	}
}
