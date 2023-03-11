package ptit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ptit.entity.Customer;

import ptit.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class CustomerController
{
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping( "/customer")
    public String customer(ModelMap model) {
        model.addAttribute("customer", customerService.findAll());
        return "customer";
    }
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer){
		
		// save customer to database
		customerService.save(customer);
		return"redirect:/customer";
	}
	
	@GetMapping("/updateCustomer/{CustomerID}")
	public String updateCustomer(@PathVariable(value ="customerID") Long customerID, ModelMap model) {
		// get customer frp, the service
		Customer customer = customerService.findById(customerID);
		// set customer as a model attribute 
		model.addAttribute("customer", customer);
		
		return"updateCustomer";
	}
	
}