package ptit.repository;

import java.sql.Date;
import java.time.LocalDateTime;
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
	List<Order> findByTime(@Param("start") Calendar date, @Param("end") Calendar date2);
	
	@Query("from Order as o where o.orderDate between :end and :start")
	List<Order> findByTimeDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
	
	@Query("from Order as o where o.id LIKE %:searchValueParam% or o.staff.staffID LIKE %:searchValueParam% or o.customer LIKE %:searchValueParam%")
	List<Order> findOrder(@Param("searchValueParam") String searchValue);
}
