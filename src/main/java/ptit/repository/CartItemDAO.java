package ptit.repository;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ptit.entity.CartItem;
import ptit.entity.Product;
@Repository
public class CartItemDAO  {

	@Autowired
	ProductDAO productDAO;
	//them san pham vao gio hang
	
	public HashMap<Long, CartItem> AddCart( long id, HashMap<Long,CartItem> cart) {
		CartItem itemCart = new CartItem();
		Product product=  productDAO.findById(id).get();
		
		if(product != null && cart.containsKey(id)) {
			itemCart=cart.get(id);
			itemCart.setQuantity(itemCart.getQuantity()+1);
			itemCart.setPrice(itemCart.getQuantity()*itemCart.getProduct().getPrice());
		}
		else {
			itemCart.setProduct(product);
			itemCart.setQuantity(1);
			//tong gia
			itemCart.setPrice(product.getPrice());
		}
	cart.put(id,itemCart);
		return cart;
	}
	// chinh sua san pham trong gio (so luong )
	
	public HashMap<Long, CartItem> EditCart( long id,int quanty, HashMap<Long,CartItem> cart) {
		if (cart==null) {
			return cart;
		}
		CartItem itemCart = new CartItem();
		
		if(cart.containsKey(id)){
			itemCart=cart.get(id);
			itemCart.setQuantity(quanty);
			int  totalPrice=itemCart.getProduct().getPrice();
			itemCart.setPrice(totalPrice);
		}
		
	cart.put(id,itemCart);
		return cart;
	}
	// xoa 1 product khoi gio hang
		
		public HashMap<Long, CartItem> DeleteCart( long id,HashMap<Long,CartItem> cart) {
	        if (cart==null)
	        {
		     return cart;
	        }
			if(cart.containsKey(id)){
				cart.remove(id);
			}
			return cart;
		}
		// tong so luong product trong gio hang
		public int TotalQuanty(HashMap<Long,CartItem>cart) {
			
			int totalQuanty= 0;
			
			for( Map.Entry<Long,CartItem> itemCart : cart.entrySet()){
				totalQuanty+=itemCart.getValue().getQuantity();
				
			}
			
			return totalQuanty;
		}
		//tong tien all product trong gio hang
		public double TotalPrice(HashMap<Long,CartItem>cart) {
			double totalPrice=  0;
			 
			for( Map.Entry<Long,CartItem> itemCart : cart.entrySet()){
				itemCart.getValue().getPrice();
				totalPrice+=itemCart.getValue().getPrice();
				
			}
			
			return totalPrice;
		}
}
	
