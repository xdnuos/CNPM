package ptit.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.entity.Customer;
import ptit.repository.CustomerDAO;
import ptit.service.CustomerService;


@Service
public  class CustomerServiceImpl  implements CustomerService {
@Autowired
 private CustomerDAO customerDAO;

@Override
public List<Customer> findAll() {
	return this.customerDAO.findAll();
}


@Override
public Customer findById(Long customerID) {
	Optional<Customer> optional = customerDAO.findById(customerID);
	Customer customer=null;
	if(optional.isPresent()) {
		customer = optional.get();
	}else {
		throw new RuntimeException("Customer not found for id :: "+customerID);
	}
	return customer;
}

@Override
public void save(Customer customer) {
	this.customerDAO.save(customer);
}
	
	
	
}
	

