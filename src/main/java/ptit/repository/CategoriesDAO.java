package ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ptit.entity.Category;

public interface CategoriesDAO extends JpaRepository<Category, Long> {
	Category findByCategoryID(int CategoryID);
	
	List<Category> findAll();

}
