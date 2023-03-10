package ptit.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index {
	
	
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
