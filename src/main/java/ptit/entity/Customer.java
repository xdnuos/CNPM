package ptit.entity;

import java.io.Serializable;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name="customer")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerID;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "accountID")
	private Account account;
	
	private String adress;

	private Date birth;

	private String fullname;

	private String phone;

	private boolean sex;

	//bi-directional many-to-one association to Cart
	@OneToMany(fetch = FetchType.LAZY, mappedBy="customer")
	private List<Cart> carts;

	//bi-directional many-to-one association to Order
	@OneToMany(fetch = FetchType.LAZY, mappedBy="customer")
	private List<Order> orders;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Customer() {
	}

	public long getCustomerID() {
		return this.customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public Object getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Object getBirth() {
		return this.birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Object getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean getSex() {
		return this.sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public List<Cart> getCarts() {
		return this.carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public Cart addCart(Cart cart) {
		getCarts().add(cart);
		cart.setCustomer(this);

		return cart;
	}

	public Cart removeCart(Cart cart) {
		getCarts().remove(cart);
		cart.setCustomer(null);

		return cart;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setCustomer(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setCustomer(null);

		return order;
	}

}