package ptit.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import ptit.config.CustomAccessDeniedHandler;
import ptit.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http.csrf().disable();

       // Requires login with role ROLE_EMPLOYEE or ROLE_MANAGER.
       // If not, it will redirect to /admin/login.
       http.authorizeRequests().antMatchers("/admin","/admin/product", "/admin/order","/admin/customer")//
             .access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')");
       // Pages only for MANAGER
       http.authorizeRequests().antMatchers("/admin/staff","/admin/statistic","/admin/addstaff","/admin/editstaff","/admin/deletestaff").access("hasRole('ROLE_MANAGER')")

       // When user login, role XX.
       // But access to the page requires the YY role,
       // An AccessDeniedException will be thrown.
       .and()
       .exceptionHandling()
       .accessDeniedHandler(accessDeniedHandler);

       // Configuration for Login Form.
       http.authorizeRequests().and().formLogin()//

             //
             .loginProcessingUrl("/j_spring_security_check") // Submit URL
             .loginPage("/login")//
             .defaultSuccessUrl("/admin")//
             .failureUrl("/login?error=true")//
             .usernameParameter("email")//
             .passwordParameter("password")

             // Configuration for the Logout page.
             // (After logout, go to home page)
             .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");

    }
    
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
	
    @Autowired
    private DataSource dataSource;
}
