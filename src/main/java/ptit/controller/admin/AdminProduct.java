package ptit.controller.admin;

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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import ptit.entity.Category;
import ptit.entity.Image;
import ptit.entity.Manufacturer;
import ptit.entity.Product;
import ptit.repository.CategoriesDAO;
import ptit.repository.ManufactureDAO;
import ptit.repository.ProductDAO;
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
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	CategoriesDAO categoriesDAO;
	
	@Autowired
	ManufactureDAO manufactureDAO;
	
	@GetMapping(value = "/admin/product")
	public String index(Model model,
	        @RequestParam Optional<Integer> page,
	        @RequestParam Optional<Integer> size,
	        @RequestParam(value="search",defaultValue = "") String search,
	        @RequestParam(value="name",defaultValue = "none") String name,
	        @RequestParam(value="price",defaultValue = "none") String price,
	        @RequestParam(value="category",defaultValue = "-1") int category,
	        @RequestParam(value="manufacturer",defaultValue = "-1") int manufactor,
	        @RequestParam(value = "message",required = false) String message) {
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

	    
	    Page<Product> productPage = productDAO.findWithPagebleA(pageable);
	    if(manufactor != -1 && category !=-1) {
	    	productPage = productDAO.findByCateManuA(category,manufactor,pageable);
	    } else 
	    if(manufactor != -1) {
	    	productPage = productDAO.findByManufactorA(manufactor,pageable);
	    } else 
	    if(category != -1) {
	    	productPage = productDAO.findByCategoryA(category,pageable);
	    }
	    if(!search.equals("")) {
	    	productPage = productDAO.searchByName(search,pageable,true);
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
		model.addAttribute("message",message);
		model.addAttribute("selectedManufacturer", manufactor);
		model.addAttribute("selectedCategory", category);
		model.addAttribute("price", price);
		model.addAttribute("name", name);
		model.addAttribute("search",search);
		return "admin/product";
	}
	@PostMapping("/admin/product")
	public String findProduct(Model model,@RequestParam("search") String text) {
		List<Product> products = productDAO.searchByNameA(text);
		Page<Product> page = productService.convertListToPage(products, 1, 5);
		model.addAttribute("productPage",page);
		//
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
    		 SessionStatus status, RedirectAttributes attributes) {
    	
	  if (result.hasErrors()) {
			List<Category> categories =categoryService.findAll();
			List<Manufacturer> manufacturers =manufacturerService.findAll();
			model.addAttribute("manufacturers", manufacturers);
			model.addAttribute("categories", categories);
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
    	attributes.addAttribute("message","Add new product complete");
        return "redirect:/admin/product";
    }
    
    @GetMapping(value = { "/productImage" })
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
          @RequestParam Long code) throws IOException {
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
			@RequestParam Long id,
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
    		 SessionStatus status,RedirectAttributes attributes) {
    	
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
    	product.setStatus(true);
    	product.setProductID(productID);
        productService.save(product);
    	status.setComplete();
    	attributes.addAttribute("message","Edit product complete");
        return "redirect:/admin/product";
    }
	
	public String convertImage(byte[] image) {
		String imageBase64 = Base64.getEncoder().encodeToString(image);
		return imageBase64;
	}
	
	@GetMapping(value ="/admin/deleteImage")
	public void deleteImage(@RequestParam Long id) {
		if(id!=null) {
			imageService.deleteByID(id);
		}
	}
	@GetMapping(value ="/admin/deleteProduct")
	public String deleteProduct(@RequestParam Long id,
			RedirectAttributes redirectAttributes) {
		Product product = productService.findById(id);
		product.setProductID(id);
		if(product.getStatus()) {
			product.setStatus(false);
			redirectAttributes.addAttribute("message","Disable product "+product.getName()+" complete!");
		}else {
			product.setStatus(true);
			redirectAttributes.addAttribute("message","Enable product "+product.getName()+" complete!");
		}
		productService.save(product);
		
		return "redirect:/admin/product";
	}
	
	@GetMapping(value = "/admin/productDetail")
	public ModelAndView productDetail(Model model,
			@RequestParam Long id,
			RedirectAttributes redirectAttributes) {
		Product product = productService.findById(id);
		List<Category> categories =product.getCategory();
		List<Image> images = product.getListImages();
		images.forEach((i) -> 
			i.setImageBase64(convertImage(i.getImages()))
		);
		
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", product);
        mav.addObject("images", images);
        List<Integer> listImageID = new ArrayList<Integer>();
        model.addAttribute(listImageID);
        model.addAttribute("categories",categories);
        
        redirectAttributes.addAttribute("id", id);
		return mav;
	}  
}
