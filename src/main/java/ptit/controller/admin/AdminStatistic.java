package ptit.controller.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.entity.BestProduct;
import ptit.entity.Customer;
import ptit.entity.Order;
import ptit.entity.Product;
import ptit.repository.CustomerDAO;
import ptit.repository.OrderDAO;
import ptit.repository.ProductDAO;

@Controller
public class AdminStatistic {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	CustomerDAO customerDAO;
	
	@GetMapping(value="/admin/statistic")
	public String index(Model model) {
		String item1 = "a",item2 ="b",item3 ="c",item4="d",item5="e",item6="f";
		int value1=1,value2=2,value3=3,value4=4,value5=5,value6=6;
		Long totalProduct,totalCustomer;
		int incomeDay=0, incomeMonth=0,incomeQuater=0,incomeYear=0;
		
		totalProduct = productDAO.count();//ok
		totalCustomer = customerDAO.count();//ok
		
		Calendar curent = Calendar.getInstance();
		
		curent.add(Calendar.DATE, 0);
		Calendar curentDate = toCalendar(curent.getTime());
		
		curent.add(Calendar.DATE, -1);
		Calendar day = toCalendar(curent.getTime());
		
		curent.add(Calendar.MONTH, -1);
		Calendar month = toCalendar(curent.getTime());
		
		curent.add(Calendar.MONTH, -4);
		Calendar quater = toCalendar(curent.getTime());
		
		curent.add(Calendar.YEAR, -1);
		Calendar year = toCalendar(curent.getTime());
		
		//day
		List<Order> ordersDay = orderDAO.findByTime(curentDate,day);
		incomeDay = total(ordersDay);//ok
		
		//month
		List<Order> ordersMonth = orderDAO.findByTime(curentDate,month);
		incomeMonth = total(ordersMonth);//ok
		
		//quater
		List<Order> ordersQuater = orderDAO.findByTime(curentDate,quater);
		incomeQuater = total(ordersQuater);//ok
		
		//year
		List<Order> ordersYear = orderDAO.findByTime(curentDate,year);
		incomeYear = total(ordersYear);//ok
		//----------------------------------------------------
		model.addAttribute("totalProduct",totalProduct);
		model.addAttribute("totalCustomer",totalCustomer);
		model.addAttribute("income",incomeDay);
		//------------------------------------------------------
		model.addAttribute("incomeDay",incomeDay);
		model.addAttribute("incomeMonth",incomeMonth);
		model.addAttribute("incomeQuater",incomeQuater);
		model.addAttribute("incomeYear",incomeYear);
		
		ArrayList<String> items = new ArrayList<String>();
		ArrayList<Long> values = new ArrayList<Long>();
		
		List<BestProduct> bestProducts = productDAO.findBestProduct();
		for(int i=0;i<bestProducts.size();i++) {
			Product newwwProduct = productDAO.findById(bestProducts.get(i).getProductID()).get();
			items.add(newwwProduct.getName());
			values.add(bestProducts.get(i).getCount());
			if(i>5) {
				break;
			}
		}
		//------------------------------------------------------
//		String[] items = {item1,item2,item3,item4,item5,item6};
		model.addAttribute("items", items);
		//------------------------------------------------------
//		int[] values = {value1,value2,value3,value4,value5,value6};
		model.addAttribute("values", values);
		return "admin/statistic";
	}
	
	public static Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
		}
	public int total(List<Order> ordersDay) {
		int income=0;
		if(!ordersDay.isEmpty()) {
			for(int i=0;i<ordersDay.size();i++) {
				income+=ordersDay.get(i).getTotal().intValue();
			}
		}
		return income;
	}
}
