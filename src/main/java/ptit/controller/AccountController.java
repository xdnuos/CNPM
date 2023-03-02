package ptit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;
import ptit.entity.Account;
import ptit.service.AccountService;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;
	

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
	@RequestMapping("/login")
	public String login() {
		return"login";
	}
	@RequestMapping( "/account")
    public String account(ModelMap model) {
        model.addAttribute("account", accountService.findAll());
        return "account";
    }
	@GetMapping("/checklogin")
	public String checkLogin( ModelMap model,@RequestParam("email")String email,
		@RequestParam("password")String password, HttpSession sesion) {
			if(accountService.checkLogin(email, password)) {
				sesion.setAttribute("email", email);
				model.addAttribute("account", accountService.findByEmail(email));
				return"index";
			}else {
				model.addAttribute("ERROR", "Account không tồn tại");
			}
			return"login";
	}
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
	    model.addAttribute("account", new Account());
	    model.addAttribute("ERROR","");
	    return "register";
	}
//	@GetMapping("/checkregister")
//	public String processRegister(Account account, SessionStatus status, Model model) {
//	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	    String encodedPassword = passwordEncoder.encode(account.getPassword());
//	    account.setPassword(encodedPassword);
//	    if(accountService.checkEmail(account.getEmail())) {
//	    	accountService.save(account);
//	    	status.setComplete();
//	    } else {
//	    	model.addAttribute("ERROR", "Email đã tồn tại, vui lòng sử dụng email khác");
//	    	return "register";
//	    }
//	    
//	    return "login";
//	}
 
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return"login";
	}
	
}
