package ptit.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import ptit.entity.Category;
import ptit.entity.Image;
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
        
        Product product = new Product();
        model.addAttribute("product", product);
        
		return "admin/addproduct";
	}  
	
	public byte[] saveImage(MultipartFile onefile) {
		byte[] image = null;
		try {
			image = onefile.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
    @PostMapping(value = "admin/addproduct")
    public String addProduct(@Valid Product product,BindingResult result, Model model,
    		 @RequestParam(value = "category") int categoryId,
    		 @RequestParam(value = "manu") int manuId,
    		 @RequestParam("images") MultipartFile[] listImages,
    		 SessionStatus status) {
    	
	  if (result.hasErrors()) {
		    return "admin/addproduct";
		  }
    	Category newCategory = new Category();
    	Manufacturer newManufacturer = new Manufacturer();
    	
    	newCategory = categoryService.findByCategoryID(categoryId);
    	newManufacturer = manufacturerService.findByManufacturerID(manuId);
    	
    	if(listImages != null) {
    		try {
    			List<Image> listImage = new ArrayList<>();
    		    Arrays.asList(listImages).stream().forEach(file -> {
    		        byte[] image = saveImage(file);
    	            if (image != null && image.length > 0) {
    	            	Image newImage = new Image();
    	            	newImage.setImages(image);
    	            	newImage.setImageName(file.getOriginalFilename());
    	            	newImage.setProduct(product);
    	            	listImage.add(newImage);
    	            }
    		      });
    		    product.setListImages(listImage);
    		    } catch (Exception e) {
		    }
    	}
    	
    	product.setStatus("disable");
    	product.setCategory(newCategory);
    	product.setManufacturers(newManufacturer);
        productService.save(product);
    	status.setComplete();
        return "redirect:/admin/product";
    }
}
