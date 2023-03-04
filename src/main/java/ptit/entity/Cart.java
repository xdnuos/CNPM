package ptit.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the cart database table.
 * 
 */
@Entity
@Table(name="cart")
public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cartID;

	private String cookie;

	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp createDate;

	//bi-directional many-to-one association to Customer
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customerID")
	private Customer customer;

	//bi-directional many-to-one association to CartItem
	@OneToMany(fetch = FetchType.LAZY, mappedBy="cart")
	private List<CartItem> cartItems;

	public Cart() {
	}

	public long getCartID() {
		return this.cartID;
	}

	public void setCartID(long cartID) {
		this.cartID = cartID;
	}

	public String getCookie() {
		return this.cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CartItem> getCartItems() {
		return this.cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public CartItem addCartItem(CartItem cartItem) {
		getCartItems().add(cartItem);
		cartItem.setCart(this);

		return cartItem;
	}

	public CartItem removeCartItem(CartItem cartItem) {
		getCartItems().remove(cartItem);
		cartItem.setCart(null);

		return cartItem;
	}

}