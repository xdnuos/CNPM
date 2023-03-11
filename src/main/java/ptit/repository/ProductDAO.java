package ptit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ptit.entity.BestProduct;
import ptit.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Long> {
	
	@Query("from Product as p where p.status = :status")
	List<Product> findAllByStatus(@Param("status") Boolean status);
	
	@Query("Select NEW ptit.entity.BestProduct (p.productID, COUNT(o.amount)) from OrderItem as o, Product as p WHERE o.product.productID = p.productID GROUP BY p.productID ORDER BY COUNT(o.amount) DESC")
	List<BestProduct> findBestProduct();
	
	// tim danh sach product theo cetegory
		 @Query("FROM Product WHERE categoryID = :categoryID")
		    List<Product> findProductCategoryALL(long categoryID);
		
		 
	// tim 1 san pham theo ten cua no
		 @Query("from Product as p where p.name = :name")
		Product findProductByName(@Param("name") String name);

	@Query("select p from Product p")
	Page<Product>findWithPageble(Pageable pageable);
	
	@Query("from Product as p where p.manufacturer.manufacturerID = :manufactor")
	Page<Product>findByManufactor(@Param("manufactor") int manufactor,Pageable pageable);
	
	@Query("select p from Product p join p.category c where c.categoryID = :category")
	Page<Product>findByCategory(@Param("category") int category,Pageable pageable);
	
	@Query("select p from Product p join p.category c where c.categoryID = :category and p.manufacturer.manufacturerID = :manufactor")
	Page<Product>findByCateManu(@Param("category") int category,@Param("manufactor") int manufactor,Pageable pageable);
	
	@Query("from Product as p where p.name LIKE %:name%")
	List<Product> searchByName(@Param("name") String name);
}
