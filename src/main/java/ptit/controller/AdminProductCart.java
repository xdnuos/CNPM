package ptit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptit.entity.Order;
import ptit.entity.OrderItem;
import jakarta.servlet.http.HttpServletRequest;
import ptit.entity.Cart;
import ptit.entity.Customer;
import ptit.entity.Product;
import ptit.repository.OrderDAO;
import ptit.service.OrderService;
import ptit.service.ProductService;
import ptit.utils.Utils;

@Controller
public class AdminProductCart {
		@Autowired
		ProductService productService;
		
		@Autowired
		OrderService orderService;
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
	   // POST: Save customer information.
	   @PostMapping(value = { "/admin/continuteOrder" })
	   public String shoppingCartCustomerSave(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute("customer") Customer customer, //
	         BindingResult result, //
	         final RedirectAttributes redirectAttributes,
	         @RequestParam(value = "gender", defaultValue = "male") String gender) {

	      Cart cart = Utils.getCartInSession(request);
	      customer.setSex(gender);
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
			   @RequestParam(value = "note", defaultValue = "") String note,
			   @RequestParam(value = "payment", defaultValue = "") String payment) {
	      Cart cart = Utils.getCartInSession(request);

	      if (cart.isEmpty()) {
	         return "redirect:/admin";
	      }
	      
	      try {
	    	  Customer newCustomer = cart.getCustomer();
	    	  orderService.saveCart2Order(cart, newCustomer, note,"live");
	      } catch (Exception e) {
	         return "redirect:/admin/orderConfirmation";
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
