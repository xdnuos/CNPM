package ptit.controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import ptit.entity.Account;
import ptit.entity.Customer;
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
		model.addAttribute("customers", new Customer());
	    model.addAttribute("account", new Account());
	    model.addAttribute("ERROR","");
	    return "register";
	}
	@PostMapping("/checkregister")
	public String processRegister(@ModelAttribute("customers") Customer customer,Account account, SessionStatus status, ModelMap model) {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(account.getPassword());
	    account.setPassword(encodedPassword);
	    //account.setPermission(getName = "Customer");
	    if(accountService.checkEmail(account.getEmail())) {
	    	accountService.save(account);
	    	customerSerivce.save(customer);
	    	status.setComplete();
	    } else {
	    	model.addAttribute("ERROR", "Email đã tồn tại, vui lòng sử dụng email khác");
	    	return "register";
	    }
	    
	    return "login";
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
