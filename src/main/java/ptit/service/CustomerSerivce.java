package ptit.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import ptit.entity.Account;
import ptit.entity.Customer;

public interface CustomerSerivce {
	List<Customer> findAll();
	
	Customer save(Customer customer);	
   
	List<Customer> findAll(Sort sort);

	List<Account> findAllAccount();
	
	Boolean checkPhone(String phone);
}
