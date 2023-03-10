package ptit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ptit.entity.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Long>{

}
