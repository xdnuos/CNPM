package ptit.service;

import java.util.List;
import java.util.Optional;

import ptit.entity.Account;

public interface AccountService {
	boolean checkLogin(String email, String passwword);
	
//	Boolean checkEmail(String email);
	
	Optional<Account> findByEmail(String email);
	
	List<Account> findAll();
	
	void save (Account account);
}
