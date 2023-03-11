package ptit.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ptit.entity.Category;
import ptit.entity.Image;
import ptit.entity.Manufacturer;
import ptit.entity.Product;
import ptit.service.CategoryService;
import ptit.service.ImageService;
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
	@Autowired
	private ImageService imageService;

	@GetMapping(value = "/admin/product")
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
		return "admin/product";
	}
	
	@GetMapping(value = "/admin/addproduct")
	public ModelAndView addProduct(Model model) {
		List<Category> categories =categoryService.findAll();
		List<Manufacturer> manufacturers =manufacturerService.findAll();
		Product product = new Product();
		
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", product);
        mav.addObject("categories", categories);
        mav.addObject("manufacturers", manufacturers);
        
		return mav;
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
    		 @RequestParam("images") MultipartFile[] listImages,
    		 SessionStatus status) {
    	
	  if (result.hasErrors()) {
		    return "admin/addproduct";
		  }
    	
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
    	
    	product.setStatus(true);
        productService.save(product);
    	status.setComplete();
        return "redirect:/admin/product";
    }
    
    @GetMapping(value = { "/productImage" })
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
          @RequestParam("code") Long code) throws IOException {
       Product product = null;
       if (code != null) {
          product = this.productService.findById(code);
          try {
        	  byte[] image = product.getListImages().get(0).getImages();
        	  if (image != null) {
                  response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
                  response.getOutputStream().write(image);
               }
          } catch(Exception e) {
        	  
          }
       }
       response.getOutputStream().close();
    }
    
	@GetMapping(value = "/admin/editproduct{id}" )
	public ModelAndView editProduct(Model model,
			@RequestParam("id") Long id,
			RedirectAttributes redirectAttributes) {
		List<Category> categories =categoryService.findAll();
		List<Manufacturer> manufacturers =manufacturerService.findAll();
		Product product = productService.findById(id);
		List<Image> images = product.getListImages();
		images.forEach((i) -> 
			i.setImageBase64(convertImage(i.getImages()))
		);
		
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", product);
        mav.addObject("categories", categories);
        mav.addObject("manufacturers", manufacturers);
        mav.addObject("images", images);
        List<Integer> listImageID = new ArrayList<Integer>();
        model.addAttribute(listImageID);
        
        redirectAttributes.addAttribute("id", id);
		return mav;
	}  
	
    @PostMapping(value = "admin/editproduct")
    public String editProduct(@Valid Product product,BindingResult result, Model model,
    		 @RequestParam("images") MultipartFile[] listImages,
    		 @RequestParam("id") long productID,
    		 SessionStatus status) {
    	
	  if (result.hasErrors()) {
		    return "admin/editproduct";
		  }
    	
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
    	product.setProductID(productID);
        productService.save(product);
    	status.setComplete();
        return "redirect:/admin/product";
    }
	
	public String convertImage(byte[] image) {
		String imageBase64 = Base64.getEncoder().encodeToString(image);
		return imageBase64;
	}
	
	@GetMapping(value ="/admin/deleteImage")
	public void deleteImage(@RequestParam("id") Long id) {
		if(id!=null) {
			imageService.deleteByID(id);
		}
	}
	@GetMapping(value ="/admin/deleteProduct")
	public String deleteProduct(@RequestParam("id") Long id) {
		Product product = productService.findById(id);
		product.setProductID(id);
		product.setStatus(false);
		productService.save(product);
		return "redirect:/admin/product";
	}
}
