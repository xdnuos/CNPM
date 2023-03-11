package ptit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import ptit.entity.CartItem;

import ptit.repository.CartItemDAO;



@Service
public class CartItemServiceImpl   implements CartItemService {
	@Autowired
	 private CartItemDAO cartItemDAO;

	@Override
	public HashMap<Long, CartItem> AddCart(long id, HashMap<Long, CartItem> cart) {
		
		return cartItemDAO.AddCart(id,cart);
	}

	@Override
	public HashMap<Long, CartItem> EditCart(long id, int quanty, HashMap<Long, CartItem> cart) {
		
		return cartItemDAO.EditCart(id,quanty,cart);
	}

	@Override
	public HashMap<Long, CartItem> DeleteCart(long id, HashMap<Long, CartItem> cart) {
		
		return cartItemDAO.DeleteCart(id,cart);
	}

	@Override
	public int TotalQuanty(HashMap<Long, CartItem> cart) {
		
		return cartItemDAO.TotalQuanty(cart);
	}

	@Override
	public double TotalPrice(HashMap<Long, CartItem> cart) {
		
		return cartItemDAO.TotalPrice(cart);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
