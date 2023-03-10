package ptit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ptit.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Long>{
	
}
