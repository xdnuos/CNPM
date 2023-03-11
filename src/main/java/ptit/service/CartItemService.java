package ptit.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import ptit.entity.CartItem;
@Service
public interface CartItemService {

	 public HashMap<Long, CartItem> AddCart(long id, HashMap<Long, CartItem> cart);

	 public HashMap<Long, CartItem> EditCart(long id,int quanty, HashMap<Long, CartItem> cart);

	 public HashMap<Long, CartItem> DeleteCart(long id, HashMap<Long, CartItem> cart);
     
	 public int TotalQuanty(HashMap<Long,CartItem>cart);
	 
		public double TotalPrice(HashMap<Long,CartItem>cart) ;
	

}
