package ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ptit.entity.Category;

public interface CategoriesDAO extends JpaRepository<Category, Integer> {
	Category findByCategoryID(int CategoryID);
	
	List<Category> findAll();

    @Query("SELECT c FROM Category c ORDER BY c.left ASC")
    List<Category> findAlll();
}
