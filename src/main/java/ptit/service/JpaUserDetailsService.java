package ptit.service;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ptit.entity.Account;
import ptit.repository.AccountDAO;
import ptit.entity.CustomUserDetails;

//import ptit.repository.PermissionDAO;
@Service
public class JpaUserDetailsService implements UserDetailsService {
	@Autowired
    private AccountService accountService;
	
//	@Autowired
//    private PermissionDAO permissionService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountService.findByEmail(username);
		
		if(account == null) {
			throw new UsernameNotFoundException("Account not found");
		}return new CustomUserDetails(account);
	}	
////	List<String> roleName = this.permissionService.findByName(roleName);
////	
//	List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//	GrantedAuthority authority = new SimpleGrantedAuthority("Admin");	
//    
//	grantList.add(authority);
////
////    // ROLE_EMPLOYEE, ROLE_MANAGER,....
////	if(roleName != null) {
////		for(String role : roleName) {
////			GrantedAuthority authority = new SimpleGrantedAuthority(role);	
////		    grantList.add(authority);
////		}
////	}
//    boolean enabled = account.getStatus();
//    boolean accountNonExpired = true;
//    boolean credentialsNonExpired = true;
//    boolean accountNonLocked = true;
//
//    UserDetails userDetails = (UserDetails) new User(account.getEmail(), 
//            account.getPassword(), enabled, accountNonExpired, 
//            credentialsNonExpired, accountNonLocked, grantList);
//
//    return userDetails;
//	}

}
