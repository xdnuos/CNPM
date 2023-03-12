package ptit.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ptit.entity.Account;
import ptit.entity.Cart;
import ptit.entity.CartItem;
import ptit.entity.Customer;
import ptit.entity.Order;
import ptit.entity.OrderItem;
import ptit.repository.AccountDAO;
import ptit.repository.CustomerDAO;
import ptit.repository.OrderDAO;

@Service
public class OrderService {
	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	CustomerDAO customerDAO;
	
	@Autowired
	AccountDAO accountDAO;

	public void saveCart2Order(Cart cart,String note,String payment) {
   	 Order order = new Order();
   	 Optional<Customer> newCustomer = customerDAO.findById(cart.getCustomer().getCustomerID());
   	 if(newCustomer.isPresent()) {
   		 order.setCustomer(newCustomer.get());
   	 }else {
   		 order.setCustomer(cart.getCustomer());
   	 }
   	 order.setNote(note);
   	 Calendar date = Calendar.getInstance();	
   	 order.setOrderDate(date);
   	 cart.getCartItems().forEach((element)->{
		OrderItem orderItem = new OrderItem();
		orderItem.setProduct(element.getProduct());
		orderItem.setAmount(element.getAmount());
		orderItem.setQuantity(element.getQuantity());
   		order.addOrderItem(orderItem);
   	 });
   	 //get admin id
	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	 String login = authentication.getName();
	 Account account = accountDAO.findByEmail(login);
	
	 order.setStaff(account.getStaff());
   	 order.setTotal(cart.getAmountTotal());
   	 order.setPayment(payment);
   	 order.setAdress(cart.getCustomer().getAddress());
   	 order.setFullname(cart.getCustomer().getFullname());
   	 order.setPhone(cart.getCustomer().getPhone());
   	 orderDAO.save(order);
	}
	
	private List<OrderItem> cartItem2OrderItem(List<CartItem> cartItems){
		List<OrderItem> orderItems = new ArrayList<>();
		
		cartItems.forEach((element) -> {
				OrderItem orderItem = new OrderItem();
				orderItem.setProduct(element.getProduct());
				orderItem.setAmount(element.getAmount());
				orderItem.setQuantity(element.getQuantity());
				orderItems.add(orderItem);
			});
		
		return orderItems;
	}
	
	public Page<Order> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Order> list;
        List<Order> allOrder = orderDAO.findAll();
        
        int listOrderSize = allOrder.size();
        if (listOrderSize < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listOrderSize);
            list = allOrder.subList(startItem, toIndex);
        }

        Page<Order> orderPage
                = new PageImpl<Order>(list, PageRequest.of(currentPage, pageSize), listOrderSize);

        return orderPage;
    }
	
	public Order findById(Long id) {
		if(orderDAO.findById(id).isPresent()) {
			return orderDAO.findById(id).get();
		}
		return null;
	}
	
	public List<Order> findByTimeDate(String start, String end){
		return orderDAO.findByTime(string2Calendar(start), string2Calendar(end));
	}
	public Calendar string2Calendar(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime dateTime = LocalDate.parse(date, formatter).atStartOfDay();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		return calendar;
	}
	public List<Order> findByTime(Calendar start,Calendar end ){
		return orderDAO.findByTime(start, end);
	}
	
    public Page<Order> convertListToPage(List<Order> productList, int pageNumber, int pageSize) {
        // Tạo trang từ danh sách
        Page<Order> page = new PageImpl<>(productList.subList(pageNumber * pageSize - pageSize, Math.min(pageNumber * pageSize, productList.size())),
                PageRequest.of(pageNumber, pageSize), productList.size());

        return page;
    }
}
