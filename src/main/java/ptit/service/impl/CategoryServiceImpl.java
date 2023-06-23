package ptit.service.impl;

import java.util.List;
import ptit.entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.repository.CategoriesDAO;
import ptit.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoriesDAO categoriesDAO;

	@Override
	public Category findByCategoryID(int CategoryID) {
		// TODO Auto-generated method stub
		return this.categoriesDAO.findByCategoryID(CategoryID);
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return this.categoriesDAO.findAll();
	}

	@Override
	public void save(Category category) {
		this.categoriesDAO.save(category);
	}
}
