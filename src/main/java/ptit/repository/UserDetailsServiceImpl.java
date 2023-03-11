package ptit.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ptit.config.MyUserDetails;
import ptit.entity.Account;
 
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private AccountDAO accountDAO;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Account user = accountDAO.findByEmail(username);
         
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new MyUserDetails(user);
    }
 
}
