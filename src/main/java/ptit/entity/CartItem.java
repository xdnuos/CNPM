package ptit.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the cartItem database table.
 * 
 */
@Entity
@Table(name="cartItem")
public class CartItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long cartItemID;

	private BigDecimal price;

	private long productID;

	private long quantity;

	//bi-directional many-to-one association to Cart
	@ManyToOne
	@JoinColumn(name="cartID")
	private Cart cart;

	public CartItem() {
	}

	public long getCartItemID() {
		return this.cartItemID;
	}

	public void setCartItemID(long cartItemID) {
		this.cartItemID = cartItemID;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public long getProductID() {
		return this.productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return this.cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

}