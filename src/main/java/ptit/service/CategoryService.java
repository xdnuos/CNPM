package ptit.service;

import java.util.List;
import ptit.entity.Category;

public interface CategoryService {
	List<Category> findAll();
	
	Category findByCategoryID(int CategoryID);
	
	void save(Category category);

	List<Category> findAlll();
}
