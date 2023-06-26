package ptit.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.entity.Category;
import ptit.repository.CategoriesDAO;

@Controller
public class AdminCategory {
	@Autowired
	CategoriesDAO categoriesDAO;
//	category
	@GetMapping(value = "/admin/addCategory")
	public String Category(Model model) {
		List<Category> categories = categoriesDAO.findAll();
		model.addAttribute("categories",categories);
		return "admin/addCategory";
	}
	
	@PostMapping(value = "/admin/addCategory")
	public String addCategory(Model model,
			@RequestParam String name) {
		Category newcategory = new Category();
		newcategory.setTitle(name);
		categoriesDAO.save(newcategory);
		return "redirect:/admin/addCategory";
	}
	
	@GetMapping(value = "/admin/editCategory")
	public String editCategory(Model model,
			@RequestParam int id) {
		Category category =categoriesDAO.findByCategoryID(id);
		model.addAttribute("category",category);
		return "admin/editCategory";
	}
	@PostMapping(value = "/admin/editCategory")
	public String saveCategory(Category category, Model model,
			@RequestParam int id) {
		category.setCategoryID(id);
		categoriesDAO.save(category);
		return "redirect:/admin/addCategory";
	}
}
