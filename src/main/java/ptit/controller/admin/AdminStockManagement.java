package ptit.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminStockManagement {
    @GetMapping("/admin/stock")
    public String stock() {
    	return "admin/stock";
    }
}
