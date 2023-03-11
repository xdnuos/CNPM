package ptit.controller;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.entity.Product;
import ptit.service.ProductService;




@Controller
public class ProductController  {
	
	@Autowired
	private ProductService productService;
	
	// tim toan bo san pham theo trang 
	@GetMapping("/productCategory/page")
	public String paginate(ModelMap model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = (Pageable) PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productService.findAll((org.springframework.data.domain.Pageable) pageable);
		model.addAttribute("product", page);
		return"index";
	}
	
	// tim toan bo san pham theo trang  category
	
	 @GetMapping("/index/{categoryID}")
	 public String getProductCategory(@PathVariable(value ="CategoryID") Long categoryID, ModelMap model) {
	 	// get product info
	 	List<Product> product =productService.findProductCategoryALL(categoryID);
	 	// set product  attribute 
	 	model.addAttribute("product",product);
	 	
	 	return"index";
	 }
	
	// tim toan bo san pham theo phan trang
	 @RequestMapping("/index")
	  public String listCustomer(Model model,
	      @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
	      @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
	      @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
	    Sort sortable = null;
	    if (sort.equals("ASC")) {
	      sortable = Sort.by("productID").ascending();
	    }
	    if (sort.equals("DESC")) {
	      sortable = Sort.by("productID").descending();
	    }
	    Pageable pageable = (Pageable) PageRequest.of(page, size, sortable);
	    
	    model.addAttribute("listCustomer", productService.findAll((org.springframework.data.domain.Pageable) pageable));
	    return "index";
	  }
	
	 @GetMapping("/product/{productID}")
	 public String getProduct(@PathVariable(value ="ProductID") Long productID, ModelMap model) {
	 	// get product info
	 	Product product =productService.findById(productID);
	 	// set product  attribute 
	 	model.addAttribute("product",product);
	 	
	 	return"product";
	 }
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

