package ptit.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name="order")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long orderID;

	private Object adress;

	private Object fullname;

	private Object note;

	private Timestamp orderDate;

	private String payment;

	private String phone;

	private long staffID;

	private boolean status;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customerID")
	private Customer customer;

	//bi-directional many-to-one association to OrderItem
	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItems;

	public Order() {
	}

	public long getOrderID() {
		return this.orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public Object getAdress() {
		return this.adress;
	}

	public void setAdress(Object adress) {
		this.adress = adress;
	}

	public Object getFullname() {
		return this.fullname;
	}

	public void setFullname(Object fullname) {
		this.fullname = fullname;
	}

	public Object getNote() {
		return this.note;
	}

	public void setNote(Object note) {
		this.note = note;
	}

	public Timestamp getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getStaffID() {
		return this.staffID;
	}

	public void setStaffID(long staffID) {
		this.staffID = staffID;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderItem addOrderItem(OrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setOrder(this);

		return orderItem;
	}

	public OrderItem removeOrderItem(OrderItem orderItem) {
		getOrderItems().remove(orderItem);
		orderItem.setOrder(null);

		return orderItem;
	}

}