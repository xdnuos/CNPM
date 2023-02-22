package ptit.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index {
	@RequestMapping(value = "/index-admin")
    public String index1() {
        return "index-admin";
	}
	

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
    
    @RequestMapping("/login")
	public String showLoginForm() {
	    return "login";
	}
    
    @RequestMapping("/register")
	public String showRegistrationForm() {
	    return "register";
	}
    
	@RequestMapping("/shop-product-grid-left-sidebar")
	public String showShop2() {
	    return "shop-product-grid-left-sidebar";
	}
	
	@RequestMapping("/shop-product-grid-fullwidth")
	public String showShop3() {
	    return "shop-product-grid-fullwidth";
	}
	
	@RequestMapping("/product")
	public String product() {
	    return "product";
	}
	
	@RequestMapping("/shop-cart")
	public String showShop_cart() {
	    return "shop-cart";
	}
	
	@RequestMapping(value = "/about")
    public String about() {
        return "about";
    }
}
