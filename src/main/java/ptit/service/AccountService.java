package ptit.service;

import java.util.List;
import java.util.Optional;

import ptit.entity.Account;
import ptit.entity.Permission;

public interface AccountService {
	
	Boolean checkEmail(String email);
	
	Account findByEmail(String email);
	
	List<Account> findAll();
	
	void save (Account account);

	List<Permission> findAllPermission();
	
	Account findById(Long id);
}
