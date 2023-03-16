package ptit.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ptit.entity.Customer;


public interface CustomerDAO extends JpaRepository<Customer, Long>{
	@Query("from Customer as c where c.phone = :phone")
	Customer findByPhone(@Param("phone") String phone);
	
//	@Query("from Customer as c where c.account.status != 0")
//	List<Customer> findActive();

	@Query("Select count(*) from Customer as c where c.phone = :phone")
	Integer checkPhone(@Param("phone") String phone);
}
