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

	private BigDecimal price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productID")
	private Product product;

	private int quantity;

	//bi-directional many-to-one association to Order
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

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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