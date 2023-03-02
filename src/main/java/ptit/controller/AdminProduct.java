package ptit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminProduct {
	@RequestMapping(value = "/admin/product")
	public String index() {
		return "admin/product";
	}
	
	@RequestMapping(value = "/admin/addproduct")
	public String addProduct() {
		return "admin/addproduct";
	}
}
