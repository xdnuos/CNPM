package ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ptit.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Long> {
	List<Product> findAll();
	
	@Query("from Product as p where p.status = :status")
	List<Product> findAllByStatus(@Param("status") String status);
}
