package ptit.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;
import ptit.entity.Category;
import ptit.entity.Manufacturer;
import ptit.entity.Product;
import ptit.repository.CategoriesDAO;
import ptit.repository.ManufactureDAO;
import ptit.repository.ProductDAO;
import ptit.service.ProductService;

@Controller
public class AdminIndex {
	@Autowired
	CategoriesDAO categoriesDAO;
	
	@Autowired
	ManufactureDAO manufactureDAO;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductDAO productDAO;
	@GetMapping("/admin")
	public String index(Model model,
        @RequestParam Optional<Integer> page,
        @RequestParam Optional<Integer> size,
        @RequestParam(value="name",defaultValue = "none") String name,
        @RequestParam(value="price",defaultValue = "none") String price,
        @RequestParam(value="category",defaultValue = "-1") int category,
        @RequestParam(value="manufactor",defaultValue = "-1") int manufactor) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(5);
    
    Sort sort = null;
    if (!name.equals("none")) {
        sort = name.equals("asc") ? Sort.by("name").ascending() : Sort.by("name").descending();
    }

    if (!price.equals("none")) {
        Sort priceSort = price.equals("asc") ? Sort.by("price").ascending() : Sort.by("price").descending();
        sort = sort == null ? priceSort : sort.and(priceSort);
    }

    Pageable pageable = sort == null ? PageRequest.of(currentPage - 1, pageSize) : PageRequest.of(currentPage - 1, pageSize, sort);

    
    Page<Product> productPage = productDAO.findWithPageble(pageable,true);
    if(manufactor != -1 && category !=-1) {
    	productPage = productDAO.findByCateManu(category,manufactor,pageable,true);
    } else 
    if(manufactor != -1) {
    	productPage = productDAO.findByManufactor(manufactor,pageable,true);
    } else 
    if(category != -1) {
    	productPage = productDAO.findByCategory(category,pageable,true);
    }
    model.addAttribute("productPage", productPage);

    int totalPages = productPage.getTotalPages();
    if (totalPages > 0) {
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
    }
	List<Category> categories = categoriesDAO.findAll();
	model.addAttribute("category",categories);
	List<Manufacturer> manufacturers = manufactureDAO.findAll();
	model.addAttribute("manufactor",manufacturers);
	return "admin/index";
	}
	
	
	@PostMapping("/admin")
	public String findProduct(Model model,@RequestParam("search") String text) {
		List<Product> products = productDAO.searchByName(text,true);
		Page<Product> page = productService.convertListToPage(products, 1, 5);
		model.addAttribute("productPage",page);
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
	
	
//	manufactor---------------------------------------------
	@GetMapping(value = "/admin/addManufactor")
	public String manufactor(Model model) {
		List<Manufacturer> manufacturers = manufactureDAO.findAll();
		model.addAttribute("manufacturers",manufacturers);
		return "admin/addManufactor";
	}
	
	@PostMapping(value = "/admin/addManufactor")
	public String addManufactor(Model model,
			@RequestParam String name) {
		Manufacturer newManufacturer = new Manufacturer();
		newManufacturer.setName(name);
		manufactureDAO.save(newManufacturer);
		return "redirect:/admin/addManufactor";
	}
	
	@GetMapping(value = "/admin/editManufactor")
	public String editManufactor(Model model,
			@RequestParam int id) {
		Manufacturer manufacturer =manufactureDAO.findByManufacturerID(id);
		model.addAttribute("manufacturer",manufacturer);
		return "admin/editManufactor";
	}
	@PostMapping(value = "/admin/editManufactor")
	public String saveManufactor(Manufacturer manufacturer, Model model,
			@RequestParam int id) {
		manufacturer.setManufacturerID(id);
		manufactureDAO.save(manufacturer);
		return "redirect:/admin/addManufactor";
	}
	
}
