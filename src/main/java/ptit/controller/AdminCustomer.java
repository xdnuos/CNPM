package ptit.controller;



import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.entity.Account;
import ptit.entity.Customer;
import ptit.entity.Order;
import ptit.repository.AccountDAO;
import ptit.repository.CustomerDAO;
import ptit.repository.OrderDAO;
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

//starthung
	CustomerDAO customerDAO;
	@Autowired
	AccountDAO accountDAO;
	@Autowired
	OrderDAO orderDAO;
	
	@GetMapping(value = "/admin/customer")
	public String index(Model model) {
		
		List<Customer> customers = customerDAO.findAll();
		
		model.addAttribute("customers", customers);
		return "admin/customer";
	}
	
	@GetMapping(value = "/admin/disableCustomer")
	public String disableCustomer(@RequestParam("id") String accountId, Model model) {
		List<Customer> customers = customerDAO.findAll();
		model.addAttribute("customers", customers);
		String message;
		if(accountId=="") {
			message ="This customer haven't account";
			model.addAttribute("message",message);
			return "admin/customer";
		}
		
		BigInteger id = new BigInteger(accountId);
		if(accountDAO.findById(id).isPresent()) {
			Account account = accountDAO.findById(id).get();
			if(account.getStatus().equals("active")) {
				account.setStatus("disable");
				message ="Disable complete";
			}else {
				account.setStatus("active");
				message ="Active complete";
			}
			
			accountDAO.save(account);
			
			model.addAttribute("message",message);
		}
		return "admin/customer";
	}
	
	@GetMapping(value = "/admin/viewOrder")
	public String viewOrder(Model model, @RequestParam("id") Long id) {
		
		List<Order> orders = orderDAO.findByUser(id);
		Customer customer = customerDAO.findById(id).get();
		model.addAttribute("orders", orders);
		model.addAttribute("customer", customer);
		return "admin/userOrder";
	}
  //endhung
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
