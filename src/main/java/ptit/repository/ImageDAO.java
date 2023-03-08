package ptit.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ptit.entity.Image;

public interface ImageDAO extends JpaRepository<Image, Long>{
	@Query("from Image as i where i.product.productID = :producID")
	List<Image> findByProductId(@Param("producID") Long id);
}
