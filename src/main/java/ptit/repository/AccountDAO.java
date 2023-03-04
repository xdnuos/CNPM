package ptit.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ptit.entity.Account;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long> {

	@Query("from Account as u where u.email = :email")
	Optional<Account> findByEmail(@Param("email") String email);
	
//	@Query("Select count(*) from Account as u where u.email = :email")
//	Integer checkEmail(@Param("email") String email);
	
	List<Account> findAll();

	
	
}