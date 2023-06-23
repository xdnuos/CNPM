package ptit.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Sort;

import ptit.entity.Cart;
import ptit.entity.Category;
import ptit.entity.Manufacturer;
import ptit.entity.Product;
import ptit.repository.CategoriesDAO;
import ptit.repository.ManufactureDAO;
import ptit.repository.ProductDAO;
import ptit.service.ProductService;
import ptit.utils.Utils;

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
        @RequestParam(value="manufactor",defaultValue = "-1") int manufactor,
        @RequestParam(value = "Smessage",required = false) String Smessage,
        @RequestParam(value = "Fmessage",required = false) String Fmessage) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(8);
    
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
	model.addAttribute("Smessage",Smessage);
	model.addAttribute("Fmessage",Fmessage);
	return "admin/index";
	}
	
//	Them sp vao gio hang
   @GetMapping(value ={"/admin/addProductToOrder"})
   public String listProductHandler(HttpServletRequest request, Model model, //
         @RequestParam(defaultValue = "") Long code,RedirectAttributes attributes) {
	  
      Product product = null;
      if (code != null) {
         product = productService.findById(code);
      }
      if (product != null) {
         Cart cart = Utils.getCartInSession(request);
//		   kiem tra sl sp trong gio hang, neu sl sp trong gio lon hon trong kho thi bao het sp
		  int buyItemQuantity = cart.getItemQuantity(product);
		  if(buyItemQuantity > product.getQuantity()) {
			  attributes.addAttribute("Fmessage","Product "+ product.getName() +" out of stock!");
			  return "redirect:/admin";
		  } else {
			  cart.addProduct(product,1);
		  }
      }
      attributes.addAttribute("Smessage","Add product "+ product.getName() +" to cart complete!");
      return "redirect:/admin";
   }
   
	@PostMapping("/admin")
	public String findProduct(Model model,@RequestParam("search") String text) {
		List<Product> products = productDAO.searchByName(text,true);
		Page<Product> page = productService.convertListToPage(products, 1, 5);
		model.addAttribute("productPage",page);
		return "admin/index";
	}
	
}
