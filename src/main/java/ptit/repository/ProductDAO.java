package ptit.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

	@Query("select p from Product p where p.status = :status and p.quantity > 0")
	Page<Product>findWithPageble(Pageable pageable,@Param("status") Boolean status);
	
	@Query("from Product as p where p.manufacturer.manufacturerID = :manufactor and p.status = :status and p.quantity > 0")
	Page<Product>findByManufactor(@Param("manufactor") int manufactor,Pageable pageable,@Param("status") Boolean status);
	
	@Query("select p from Product p join p.category c where c.categoryID = :category and p.status = :status and p.quantity > 0")
	Page<Product>findByCategory(@Param("category") int category,Pageable pageable,@Param("status") Boolean status);
	
	@Query("select p from Product p join p.category c where c.categoryID = :category and p.manufacturer.manufacturerID = :manufactor and p.status = :status and p.quantity > 0")
	Page<Product>findByCateManu(@Param("category") int category,@Param("manufactor") int manufactor,Pageable pageable,@Param("status") Boolean status);
	
	@Query("from Product as p where p.name LIKE %:name% and p.status = :status and p.quantity > 0")
	Page<Product> searchByName(@Param("name") String name,Pageable pageable,@Param("status") Boolean status);
	
//	---------------------
	@Query("select p from Product p")
	Page<Product>findWithPagebleA(Pageable pageable);
	
	@Query("from Product as p where p.manufacturer.manufacturerID = :manufactor")
	Page<Product>findByManufactorA(@Param("manufactor") int manufactor,Pageable pageable);
	
	@Query("select p from Product p join p.category c where c.categoryID = :category")
	Page<Product>findByCategoryA(@Param("category") int category,Pageable pageable);
	
	@Query("select p from Product p join p.category c where c.categoryID = :category and p.manufacturer.manufacturerID = :manufactor")
	Page<Product>findByCateManuA(@Param("category") int category,@Param("manufactor") int manufactor,Pageable pageable);

	@Query("from Product as p where p.name LIKE %:name%")
	List<Product> searchByNameA(@Param("name") String name);
	
	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.quantity = :qty WHERE p.id = :id")
	Integer updateQty(@Param("qty") int qty,@Param("id") Long id);
	
	@Query("SELECT p.quantity FROM Product p WHERE p.id = :id")
	Integer getProductQty(@Param("id") Long id);
}
