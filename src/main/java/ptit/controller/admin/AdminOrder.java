package ptit.controller.admin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.entity.Order;
import ptit.entity.Product;
import ptit.service.OrderService;

@Controller
public class AdminOrder {
	@Autowired
	OrderService orderService;
	
//	@GetMapping(value="/admin/order")
//	public String OrderIndex(Model model,
//            @RequestParam Optional<Integer> page,
//            @RequestParam Optional<Integer> size,
//            @RequestParam(value="startDate",defaultValue = "") String startDate,
//            @RequestParam(value="endDate",defaultValue = "") String endDate) {
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(15);
//
//        Page<Order> orderPage = orderService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
//
//        model.addAttribute("orderPage", orderPage);
//
//        int totalPages = orderPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        model.addAttribute("startDate", startDate);
//        model.addAttribute("endDate", endDate);
//		return "/admin/order";
//	}
	
	@GetMapping(value="/admin/orderDetail")
	public String orderDetail(Model model,
			@RequestParam("orderID") Long id) {
		Order order = orderService.findById(id);
		if(order!=null) {
			model.addAttribute("order",order);
		}else {
			return "404";
		}
		return "/admin/orderDetail";
	}
	
	@GetMapping(value="/admin/order")
	public String filterOrder(Model model,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
			@RequestParam(value="startDate",defaultValue = "1970-01-01") String start,
			@RequestParam(value="endDate",defaultValue = "2099-12-31") String end,
			@RequestParam(value="search",defaultValue = "") String search) throws ParseException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
		List<Order> order = orderService.findByTimeDate(end,start);
		if (!search.equals("")) {
			order = orderService.findOrder(search);
		}
		Page<Order> orderPage = orderService.convertListToPage(order, currentPage, pageSize);
		model.addAttribute("orderPage", orderPage);
	    int totalPages = orderPage.getTotalPages();
	    if (totalPages > 0) {
	    	List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                    .boxed()
	                    .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	    model.addAttribute("startDate", start);
	    model.addAttribute("endDate", end);
	    model.addAttribute("search", search);
		return "/admin/order";
	}
//	@PostMapping("/admin/order")
//	public String findOrder(Model model,@RequestParam("search") String text) {
//		List<Order> order = orderService.findByTimeDate(end,start);
//		Page<Order> orderPage = orderService.convertListToPage(order, 1, 5);
//		 model.addAttribute("orderPage", orderPage);
//	        int totalPages = orderPage.getTotalPages();
//	        if (totalPages > 0) {
//	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//	                    .boxed()
//	                    .collect(Collectors.toList());
//	            model.addAttribute("pageNumbers", pageNumbers);
//	        }
//			return "/admin/order";
//	}
}
