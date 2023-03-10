package ptit.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the orderItem database table.
 * 
 */
@Entity
@Table(name="orderItem")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderItemID;

	private Double amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productID")
	private Product product;

	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="orderID")
	private Order order;

	public OrderItem() {
	}

	public long getOrderItemID() {
		return this.orderItemID;
	}

	public void setOrderItemID(long orderItemID) {
		this.orderItemID = orderItemID;
	}

	public Double getPrice() {
		return this.amount;
	}

	public void setPrice(Double price) {
		this.amount = price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}