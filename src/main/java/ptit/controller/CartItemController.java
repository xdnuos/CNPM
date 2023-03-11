package ptit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import ptit.entity.CartItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import ptit.service.CartItemServiceImpl;

@Controller
public class CartItemController {
	@Autowired
	private CartItemServiceImpl cartItemService=new CartItemServiceImpl();

	
	
	
	@RequestMapping( "/AddCart/{id}")
	public String AddCart(HttpServletRequest request ,HttpSession session ,@PathVariable long id)
	{
		HashMap<Long ,CartItem> cart= (HashMap<Long,CartItem>)session.getAttribute("Cart");
		if(cart==null) {
			cart=  new HashMap<Long ,CartItem>();
		}
		cart= cartItemService.AddCart(id,cart);
		session.setAttribute("Cart",cart);
		// tong soluong
		session.setAttribute("TotalQuantyCart",cartItemService.TotalQuanty(cart));
		//togn gia
		session.setAttribute("TotalPriceCart",cartItemService.TotalPrice(cart));
		//tra ve trang chu index
		return "redirect:"+request.getHeader("Referer");
	//	return "redirect:/chi-tiet-san-pham/"+id;
	}
	
	
	@RequestMapping( "DeleteCart/{id}")
	public String DeleteCart(HttpServletRequest request ,HttpSession session ,@PathVariable long id)
	{
		HashMap<Long ,CartItem> cart= (HashMap<Long,CartItem>)session.getAttribute("Cart");
		if(cart==null) {
			cart=  new HashMap<Long ,CartItem>();
		}
		cart= cartItemService.DeleteCart(id,cart);
		session.setAttribute("Cart",cart);
		// tong soluong
		session.setAttribute("TotalQuantyCart",cartItemService.TotalQuanty(cart));
		//togn gia
		session.setAttribute("TotalPriceCart",cartItemService.TotalPrice(cart));
		//tra ve trang chu index
		return "redirect:"+request.getHeader("Referer");
	//	return "redirect:/chi-tiet-san-pham/"+id;
	}
	
	@RequestMapping( "EditCart/{id}/{quanty}")
	public String EditCart(HttpServletRequest request ,HttpSession session ,@PathVariable long id,@PathVariable int quanty)
	{
		HashMap<Long ,CartItem> cart= (HashMap<Long,CartItem>)session.getAttribute("Cart");
		if(cart==null) {
			cart=  new HashMap<Long ,CartItem>();
		}
		cart= cartItemService.EditCart(id,quanty,cart);
		session.setAttribute("Cart",cart);
		//  t  o g so luong
		session.setAttribute("TotalQuantyCart",cartItemService.TotalQuanty(cart));
		//togn gia
		session.setAttribute("TotalPriceCart",cartItemService.TotalPrice(cart));
		//tra ve trang chu index
		return "redirect:"+request.getHeader("Referer");
	//	return "redirect:/chi-tiet-san-pham/"+id;
	}
	

	
	
	
	
	
}