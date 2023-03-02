package ptit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminCustomer {
	@RequestMapping(value = "/admin/customer")
	public String index() {
		return "admin/customer";
	}
}
