package ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ptit.entity.BestProduct;
import ptit.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Long> {
	List<Product> findAll();
	
	@Query("from Product as p where p.status = :status")
	List<Product> findAllByStatus(@Param("status") Boolean status);
	
	@Query("Select NEW ptit.entity.BestProduct (p.productID, COUNT(o.amount)) from OrderItem as o, Product as p WHERE o.product.productID = p.productID GROUP BY p.productID ORDER BY COUNT(o.amount) DESC")
	List<BestProduct> findBestProduct();
}
