package ptit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ptit.service.JpaUserDetailsService;




public class SecurityConfig {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Autowired
//    private DataSource dataSource;
	
	@Bean
    public UserDetailsService userDetailsService() {
        return new JpaUserDetailsService();
    }
 
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    	auth.userDetailsService(jpaUserDetailsService).passwordEncoder(appConfig.passwordEncoder());
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    			.authorizeHttpRequests((authz) -> authz
    					.requestMatchers("/**","/images/**/","/fonts/**","/js/**").permitAll()
						//.requestMatchers("/admin/**").hasRole("Admin")
						//.requestMatchers("/staff/**").hasRole("USER")
						.anyRequest().authenticated())
//               Login 
						.formLogin(form -> form
						.loginPage("/login").permitAll()
						.loginProcessingUrl("/j_spring_security_check") // Submit URL
						.defaultSuccessUrl("/admin")//
						.failureUrl("/login?error=fail")//
						
						.usernameParameter("email")//
						.passwordParameter("password"))
//				Logout Page.
						.logout().logoutUrl("/logout").logoutSuccessUrl("/");
					
//		Remember Me.
//		http.authorizeHttpRequests().and() //
//				.rememberMe().tokenRepository(this.persistentTokenRepository()) //
//				.tokenValiditySeconds(1 * 24 * 60 * 60); // 24h	
						//.httpBasic();
        return http.build();
    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//    	return (web) -> web.ignoring().requestMatchers();
//    }
//    @Bean
//	public PersistentTokenRepository persistentTokenRepository() {
//		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
//		db.setDataSource(dataSource);
//		return db;
//	}
}