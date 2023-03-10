package ptit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.entity.Cart;
import ptit.entity.CartItem;
import ptit.entity.Customer;
import ptit.entity.Order;
import ptit.entity.OrderItem;
import ptit.repository.CustomerDAO;
import ptit.repository.OrderDAO;

@Service
public class OrderService {
	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	CustomerDAO customerDAO;

	public void saveCart2Order(Cart cart,String note,String payment) {
   	 Order order = new Order();
   	 Optional<Customer> newCustomer = customerDAO.findById(cart.getCustomer().getCustomerID());
   	 if(newCustomer.isPresent()) {
   		 order.setCustomer(newCustomer.get());
   	 }else {
   		 order.setCustomer(cart.getCustomer());
   	 }
   	 order.setNote(note);
//   	 order.setOrderDate(null);
   	 cart.getCartItems().forEach((element)->{
		OrderItem orderItem = new OrderItem();
		orderItem.setProduct(element.getProduct());
		orderItem.setPrice(element.getAmount());
		orderItem.setQuantity(element.getQuantity());
   		order.addOrderItem(orderItem);
   	 });
   	 
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
				orderItem.setPrice(element.getAmount());
				orderItem.setQuantity(element.getQuantity());
				orderItems.add(orderItem);
			});
		
		return orderItems;
	}
}
