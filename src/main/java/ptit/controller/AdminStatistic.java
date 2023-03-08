package ptit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.model.Model;

@Controller
public class AdminStatistic {
	@GetMapping(value="/admin/statistic")
	public String index(Model model, @RequestParam(value = "by", defaultValue = "day") String by) {
		
		return "admin/statistic";
	}
}
