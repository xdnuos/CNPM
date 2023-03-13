package ptit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ptit.entity.Cart;
import ptit.entity.Customer;
import ptit.entity.Permission;
import ptit.entity.Product;
import ptit.repository.CustomerDAO;
import ptit.service.OrderService;
import ptit.service.ProductService;
import ptit.utils.Utils;

@Controller
public class AdminProductCart {
		@Autowired
		ProductService productService;
		
		@Autowired
		CustomerDAO customerDAO;
		
		@Autowired
		OrderService orderService;
	   @GetMapping(value ={"/admin/addProductToOrder"})
	   public String listProductHandler(HttpServletRequest request, Model model, //
	         @RequestParam(defaultValue = "") Long code) {

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
	         @RequestParam(defaultValue = "") Long code) {
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
	   public String shoppingCartHandler(HttpServletRequest request, Model model,@RequestParam(value = "message",required = false) String message) {
		   Cart myCart = Utils.getCartInSession(request);

	      model.addAttribute("cartForm", myCart);
	      model.addAttribute("message", message);
	      return "/admin/cart";
	   }
	   @PostMapping(value = { "/admin/cart" })
	   public String shoppingCartUpdateQty(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute Cart cartForm, RedirectAttributes attributes) {

	      Cart cartInfo = Utils.getCartInSession(request);
	      String status = cartInfo.updateQuantity(cartForm);
	      if(status.equals("ok")) {
	    	  return "redirect:/admin/cart";
	      }
	      attributes.addAttribute("message", "Product "+status+" exceed the quantity in stock");
	      return "redirect:/admin/cart";
	   }
	   
	   @GetMapping(value = { "/admin/continuteOrder" })
	   public String shoppingCartCustomerForm(HttpServletRequest request, Model model,Customer customer) {

		      Cart cartInfo = Utils.getCartInSession(request);

	    	  customer = cartInfo.getCustomer();
	    	  if(customer == null) {
	    		  customer = new Customer();
	    	  }
		      model.addAttribute(customer);
		      if (cartInfo.isEmpty()) {

		         return "redirect:/admin/cart";
		      }
		      return "/admin/continuteOrder";
	   }
	   
	   @GetMapping(value = { "/admin/checkCustomer" })
	   public String checkCustomer(HttpServletRequest request, Model model,Customer customer,@RequestParam String phone) {

		      Cart cartInfo = Utils.getCartInSession(request);

	    	  customer = customerDAO.findByPhone(phone);
	    	  if(customer == null) {
	    		  customer = new Customer();
	    		  customer.setPhone(phone);
	    	  }
	    	  
		      model.addAttribute(customer);
		      if (cartInfo.isEmpty()) {

		         return "redirect:/admin/cart";
		      }
		      return "/admin/continuteOrder";
	   }
	   // POST: Save customer information.
	   @PostMapping(value = { "/admin/continuteOrder" })
	   public String shoppingCartCustomerSave(@Valid Customer customer,BindingResult result,
			 HttpServletRequest request, //
	         Model model, //
	         @RequestParam(defaultValue = "male") String gender) {
		if (result.hasErrors()) {
		    return "admin/continuteOrder";
		  }
	      Cart cart = Utils.getCartInSession(request);
	      if(gender=="male") {
	    	  customer.setSex(false);
	      }
	      if(gender=="female") {
	    	  customer.setSex(true);
	      }
	      
	      cart.setCustomer(customer);
	      return "redirect:/admin/orderConfirmation";
	   }
	   
	   // GET: Show information to confirm.
	   @GetMapping(value = { "/admin/orderConfirmation" })
	   public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
	      Cart cart = Utils.getCartInSession(request);

	      if (cart == null || cart.isEmpty()) {

	         return "redirect:/admin";
	      }
	      model.addAttribute("myCart", cart);

	      return "/admin/orderConfirmation";
	   }
	   
	   // POST: Submit Cart (Save)
	   @PostMapping(value = { "/admin/orderConfirmation" })
	   public String shoppingCartConfirmationSave(HttpServletRequest request, Model model,
			   @RequestParam(defaultValue = "") String note,
			   @RequestParam(defaultValue = "") String payment) {
	      Cart cart = Utils.getCartInSession(request);

	      if (cart.isEmpty()) {
	         return "redirect:/admin";
	      }
	      
	      try {
	    	  orderService.saveCart2Order(cart, note,"live");
	      } catch (Exception e) {
	    	 return e.getMessage();
//	         return "redirect:/admin/orderConfirmation";
	      }

	      // Remove Cart from Session.
	      Utils.removeCartInSession(request);

	      // Store last cart.
	      Utils.storeLastOrderedCartInSession(request, cart);
	      return "redirect:/admin/orderFinalize";
	   }
	   
	   @GetMapping(value = { "admin/orderFinalize" })
	   public String shoppingCartFinalize(HttpServletRequest request, Model model) {

//	      Cart lastOrderedCart = Utils.getLastOrderedCartInSession(request);
//
//	      if (lastOrderedCart == null) {
//	         return "redirect:/shoppingCart";
//	      }
//	      model.addAttribute("lastOrderedCart", lastOrderedCart);
	      return "/admin/orderFinalize";
	   }
}
