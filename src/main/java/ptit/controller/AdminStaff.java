package ptit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminStaff {
	@RequestMapping(value = "/admin/staff")
	public String index() {
		return "admin/staff";
	}
}
