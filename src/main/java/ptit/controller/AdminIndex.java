package ptit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminIndex {
	@RequestMapping(value = "/admin")
	public String index() {
		return "admin/index";
	}
}