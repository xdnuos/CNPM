package ptit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import ptit.entity.Cart;
import ptit.entity.Product;
import ptit.service.ProductService;
import ptit.utils.Utils;

@Controller
public class AdminProductCart {
		@Autowired
		ProductService productService;
		
	   @GetMapping(value ={"/admin/addProductToOrder"})
	   public String listProductHandler(HttpServletRequest request, Model model, //
	         @RequestParam(value = "code", defaultValue = "") Long code) {

	      Product product = null;
	      if (code != null) {
	         product = productService.findById(code);
	      }
	      if (product != null) {
	         Cart cart = Utils.getCartInSession(request);
	         cart.addProduct(product,1);
	      }

	      return "redirect:/admin/cart";
	   }
	   
	   @GetMapping(value ={"/admin/cartRemoveProduct"})
	   public String removeProductHandler(HttpServletRequest request, Model model, //
	         @RequestParam(value = "code", defaultValue = "") Long code) {
	      Product product = null;
	      if (code != null) {
	         product = productService.findById(code);
	      }
	      if (product != null) {

	         Cart cart = Utils.getCartInSession(request);

	         cart.removeProduct(product);

	      }
	      return "redirect:/admin/cart";
	   }
	   @GetMapping(value = { "/admin/cart" })
	   public String shoppingCartHandler(HttpServletRequest request, Model model) {
		   Cart myCart = Utils.getCartInSession(request);

	      model.addAttribute("cartForm", myCart);
	      return "/admin/cart";
	   }
	   @PostMapping(value = { "/admin/cart" })
	   public String shoppingCartUpdateQty(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute("cartForm") Cart cartForm) {

	      Cart cartInfo = Utils.getCartInSession(request);
	      cartInfo.updateQuantity(cartForm);

	      return "redirect:/admin/cart";
	   }
}
