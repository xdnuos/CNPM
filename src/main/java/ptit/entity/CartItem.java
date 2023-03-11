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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cartItemID;

	private BigDecimal price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productID")
	private Product product;

	private int quantity;

	//bi-directional many-to-one association to Cart
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cartID")
	private Cart cart;

	public CartItem() {
	}
	
	public CartItem(long cartItemID, BigDecimal price, ptit.entity.Product product, int quantity, Cart cart) {
		
		this.cartItemID = cartItemID;
		this.price = price;
		this.product = product;
		this.quantity = quantity;
		this.cart = cart;
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

	public Product getProduct() {
		return this.product;
	}

	public void Product(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return this.cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
    public double getAmount() {
        return this.product.getPrice().intValue() * this.quantity;
    }
}