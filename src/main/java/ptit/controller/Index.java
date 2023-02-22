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
	
	@RequestMapping("/product")
	public String product() {
	    return "product";
	}
	
	@RequestMapping("/cart")
	public String cart() {
	    return "cart";
	}
	
	@RequestMapping(value = "/contact")
    public String contact() {
        return "contact";
    }
	
	@RequestMapping(value = "/user")
    public String user() {
        return "user";
    }
}
