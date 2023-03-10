package ptit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ptit.entity.Customer;
import ptit.repository.CustomerDAO;

@Controller
public class AdminCustomer {
	@Autowired
	CustomerDAO customerDAO;
	
	@GetMapping(value = "/admin/customer")
	public String index(Model model) {
		
		List<Customer> customers = customerDAO.findAll();
		
		model.addAttribute("customers", customers);
		return "admin/customer";
	}
	
	@GetMapping(value = "/admin/disableCustomer")
	public String disableCustomer() {
		
		return "redirect:/admin/customer";
	}
}
