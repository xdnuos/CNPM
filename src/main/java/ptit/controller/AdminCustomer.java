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

@Controller
public class AdminCustomer {
	@Autowired
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
}
