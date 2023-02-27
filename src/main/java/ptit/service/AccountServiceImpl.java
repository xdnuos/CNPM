package ptit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ptit.entity.Account;
import ptit.repository.AccountDAO;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDAO;

	public AccountServiceImpl(AccountDAO AccountDAO) {
		this.accountDAO = AccountDAO;
	}

	@Override
	public List<Account> findAll() {
		return this.accountDAO.findAll();
	}

	@Override
	public void save(Account account) {
		this.accountDAO.save(account);

		
	}

	@Override
	public Optional<Account> findByEmail(String email) {
		return accountDAO.findByEmail(email);
	}

	@Override
	public boolean checkLogin(String email, String password) {
		Optional<Account> optionalAccount = findByEmail(email);
		if (optionalAccount.isPresent() && optionalAccount.get().getPassword().equals(password)) {
			return true;

		}
		return false;
	}
//	@Override
//    public Boolean checkEmail(String email) {
//    	if(this.accountDAO.checkEmail(email)==0) {
//    		return true;
//    	}
//    	return false;
//    }

}
