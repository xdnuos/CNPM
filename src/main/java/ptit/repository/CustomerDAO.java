package ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ptit.entity.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Long>{
	Customer save(Customer customer);
	
	List<Customer> findAll();
}
