package ptit.service;

import java.util.List;

import ptit.entity.Customer;


public interface CustomerService {
	  
	List<Customer> findAll();

	Customer findById(Long customerID);
	
	void save(Customer customer);
}
