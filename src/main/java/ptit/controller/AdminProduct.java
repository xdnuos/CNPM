package ptit.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import ptit.entity.Category;
import ptit.entity.Manufacturer;
import ptit.entity.Product;
import ptit.service.CategoryService;
import ptit.service.ManufacturerService;
import ptit.service.ProductService;

@Controller
public class AdminProduct {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ManufacturerService manufacturerService;

	@GetMapping(value = "/admin/product")
	public String index() {
		return "admin/product";
	}
	
	@GetMapping(value = "/admin/addproduct")
	public String addProduct(Model model) {
		List<Category> categories =categoryService.findAll();
        model.addAttribute("categories", categories);
        
		List<Manufacturer> manufacturers =manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
		return "admin/addproduct";
	}  
	
    @PostMapping(value = "admin/addproduct")
    public String addProduct(Product product, Model model,
    		 @RequestParam(value = "name") String name,
    		 @RequestParam(value = "price") BigDecimal price,
    		 @RequestParam(value = "quantity") int quantity,
    		 @RequestParam(value = "desc") String desc,
    		 @RequestParam(value = "category") int categoryId,
    		 @RequestParam(value = "manu") int manuId,
    		 SessionStatus status) {
//    		 @RequestParam("image") MultipartFile image) {
//        try {
//            imgName = image.getOriginalFilename().replace(image.getOriginalFilename(), FilenameUtils.getBaseName(image.getOriginalFilename()).concat(currentDate) + "." + FilenameUtils.getExtension(image.getOriginalFilename())).toLowerCase();
//            File file = new File(this.getFolderUpload(), imgName);
//            image.transferTo(file);
//          } catch (Exception e) {
//            e.printStackTrace();
//          }
    	Category newCategory = new Category();
    	Manufacturer newManufacturer = new Manufacturer();
    	
    	newCategory = categoryService.findByCategoryID(categoryId);
    	newManufacturer = manufacturerService.findByManufacturerID(manuId);
    	
    	product.setCategory(newCategory);
    	product.setManufacturers(newManufacturer);
    	product.setName(name);
    	product.setPrice(price);
    	product.setQuantity(quantity);
    	product.setDescription(desc);
        productService.save(product);
    	status.setComplete();
        return "redirect:/admin/product";
    }
}
