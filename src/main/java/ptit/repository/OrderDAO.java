package ptit.repository;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ptit.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Long>{
	@Query("from Order as o where o.customer.customerID =:id")
	List<Order> findByUser(@Param("id") Long id);
	
	@Query("from Order as o where o.orderDate between :end and :start")
	List<Order> findByTime(@Param("start") Calendar start, @Param("end") Calendar end);
}
