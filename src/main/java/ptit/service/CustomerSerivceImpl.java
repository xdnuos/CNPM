package ptit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ptit.entity.Account;
import ptit.entity.Customer;
import ptit.repository.AccountDAO;
import ptit.repository.CustomerDAO;

@Service
public class CustomerSerivceImpl implements CustomerSerivce {

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public List<Account> findAllAccount(){
		return (List<Account>)accountDAO.findAll();
	}
    @Override
    public List<Customer> findAll() {
        return this.customerDAO.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerDAO.save(customer);
    }
    
	@Override
	public List<Customer> findAll(Sort sort) {
		return (List<Customer>)customerDAO.findAll(sort);
	}
	@Override
    public Boolean checkPhone(String phone) {
    	if(this.customerDAO.checkPhone(phone)==0) {
    		return true;
    	}
    	return false;
    }

}
