package ptit.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.entity.Category;
import ptit.entity.Manufacturer;
import ptit.entity.Product;
import ptit.repository.CategoriesDAO;
import ptit.repository.ManufactureDAO;
import ptit.service.ProductService;

@Controller
public class AdminIndex {
	@Autowired
	CategoriesDAO categoriesDAO;
	
	@Autowired
	ManufactureDAO manufactureDAO;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/admin")
	public String index(Model model,
        @RequestParam("page") Optional<Integer> page,
        @RequestParam("size") Optional<Integer> size) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(5);

    Page<Product> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

    model.addAttribute("productPage", productPage);

    int totalPages = productPage.getTotalPages();
    if (totalPages > 0) {
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
    }
	return "admin/index";
	}
//	category
	@GetMapping(value = "/admin/addCategory")
	public String Category(Model model) {
		List<Category> categories = categoriesDAO.findAll();
		model.addAttribute("categories",categories);
		return "admin/addCategory";
	}
	
	@PostMapping(value = "/admin/addCategory")
	public String addCategory(Model model,
			@RequestParam("name") String name) {
		Category newcategory = new Category();
		newcategory.setTitle(name);
		categoriesDAO.save(newcategory);
		return "redirect:/admin/addCategory";
	}
	
	@GetMapping(value = "/admin/editCategory")
	public String editCategory(Model model,
			@RequestParam("id") int id) {
		Category category =categoriesDAO.findByCategoryID(id);
		model.addAttribute("category",category);
		return "admin/editCategory";
	}
	@PostMapping(value = "/admin/editCategory")
	public String saveCategory(Category category, Model model,
			@RequestParam("id") int id) {
		category.setCategoryID(id);
		categoriesDAO.save(category);
		return "redirect:/admin/addCategory";
	}
	
	
//	manufactor---------------------------------------------
	@GetMapping(value = "/admin/addManufactor")
	public String manufactor(Model model) {
		List<Manufacturer> manufacturers = manufactureDAO.findAll();
		model.addAttribute("manufacturers",manufacturers);
		return "admin/addManufactor";
	}
	
	@PostMapping(value = "/admin/addManufactor")
	public String addManufactor(Model model,
			@RequestParam("name") String name) {
		Manufacturer newManufacturer = new Manufacturer();
		newManufacturer.setName(name);
		manufactureDAO.save(newManufacturer);
		return "redirect:/admin/addManufactor";
	}
	
	@GetMapping(value = "/admin/editManufactor")
	public String editManufactor(Model model,
			@RequestParam("id") int id) {
		Manufacturer manufacturer =manufactureDAO.findByManufacturerID(id);
		model.addAttribute("manufacturer",manufacturer);
		return "admin/editManufactor";
	}
	@PostMapping(value = "/admin/editManufactor")
	public String saveManufactor(Manufacturer manufacturer, Model model,
			@RequestParam("id") int id) {
		manufacturer.setManufacturerID(id);
		manufactureDAO.save(manufacturer);
		return "redirect:/admin/addManufactor";
	}
	
}
