package ptit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.entity.Cart;
import ptit.entity.CartItem;
import ptit.entity.Customer;
import ptit.entity.Order;
import ptit.entity.OrderItem;
import ptit.repository.OrderDAO;

@Service
public class OrderService {
	@Autowired
	OrderDAO orderDAO;

	public void saveCart2Order(Cart cart, Customer customer ,String note,String payment) {
   	 Order order = new Order();
   	 order.setAdress(cart.getCustomer().getAddress());
   	 order.setFullname(cart.getCustomer().getFullname());
   	 order.setNote(note);
//   	 order.setOrderDate(null);
   	 List<OrderItem> orderItems = cartItem2OrderItem(cart.getCartItems());
   	 order.setOrderItems(orderItems);
   	 order.setOrderItems(orderItems);
   	 order.setPayment(payment);
   	 if(customer!=null) {
   		order.setCustomer(customer);
   		order.setPhone(customer.getPhone());
   	 }
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
