package ptit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ptit.entity.Account;
import ptit.entity.Permission;
import ptit.repository.AccountDAO;
import ptit.repository.PermissionDAO;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private PermissionDAO permissionDAO;
	
	
	public AccountServiceImpl(AccountDAO AccountDAO) {
		this.accountDAO = AccountDAO;
	}
	@Override
	public List<Permission> findAllPermission(){
		return (List<Permission>)permissionDAO.findAll();
	}
	
	@Override
	public List<Account> findAll() {
		return this.accountDAO.findAll();
	}

	@Override
	public Account findByEmail(String email) {
		return accountDAO.findByEmail(email);
	}
	@Override
	public void save(Account account) {
		this.accountDAO.save(account);
		
	}
	@Override
    public Boolean checkEmail(String email) {
    	if(this.accountDAO.checkEmail(email)==0) {
    		return true;
    	}
    	return false;
    }

}
