package ptit.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
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
	private List<CartItem> cartItems = new ArrayList<CartItem>();

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
	
    private CartItem findLineByCode(Long code) {
        for (CartItem line : this.cartItems) {
            if (line.getProduct().getProductID().equals(code)) {
                return line;
            }
        }
        return null;
    }
    
	public void addProduct(Product product, int quantity) {
		CartItem line = this.findLineByCode(product.getProductID());
        if (line == null) {
            line = new CartItem();
            line.setQuantity(0);
            line.setProduct(product);
            this.cartItems.add(line);
        }
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartItems.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
	}
	
    public void removeProduct(Product product) {
        CartItem line = this.findLineByCode(product.getProductID());
        if (line != null) {
            this.cartItems.remove(line);
        }
    }

    public int getQuantityTotal() {
        int quantity = 0;
        for (CartItem line : this.cartItems) {
            quantity += line.getQuantity();
        }
        return quantity;
    }

    public double getAmountTotal() {
        double total = 0;
        for (CartItem line : this.cartItems) {
            total += line.getAmount();
        }
        return total;
    }

    public void updateQuantity(Cart cartForm) {
        if (cartForm != null) {
            List<CartItem> lines = cartForm.getCartItems();
            for (CartItem line : lines) {
                this.updateProduct(line.getProduct().getProductID(), line.getQuantity());
            }
        }
    }
    
    public void updateProduct(Long code, int quantity) {
        CartItem line = this.findLineByCode(code);

        if (line != null) {
            if (quantity <= 0) {
                this.cartItems.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }
    
    public boolean isEmpty() {
        return this.cartItems.isEmpty();
    }
}