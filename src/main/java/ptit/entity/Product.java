package ptit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.*;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name="product")
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "productID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productID;	
	
    @Nonnull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100)
    private String name;
    
    @Digits(integer = 9, fraction = 3)
    @Column(name = "price")
    private BigDecimal price;
    
    @Size(min = 20, max = 300)
    @Column(name = "description", length = 300)
    private String description;
    
    @Column(name = "quantity")
    private int quantity;
    
    @Column(name="status")
    private boolean status;
    
    @ManyToMany(mappedBy = "products")
    private Collection<Manufacturer> manufacturers = new ArrayList<>();
    
    @ManyToMany(mappedBy = "products")
    private Collection<Category> category = new ArrayList<>();
    
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
    private Collection<ListImage> listImages = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Collection<OrderItem> oderItem = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Collection<CartItem> cartItem = new ArrayList<>();

	public Product() {
		super();
	}

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Collection<Manufacturer> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(Collection<Manufacturer> manufacturers) {
		this.manufacturers = manufacturers;
	}

	public Collection<Category> getCategory() {
		return category;
	}

	public void setCategory(Collection<Category> category) {
		this.category = category;
	}

	public Collection<ListImage> getListImages() {
		return listImages;
	}

	public void setListImages(Collection<ListImage> listImages) {
		this.listImages = listImages;
	}

	public Collection<OrderItem> getOderItem() {
		return oderItem;
	}

	public void setOderItem(Collection<OrderItem> oderItem) {
		this.oderItem = oderItem;
	}

	public Collection<CartItem> getCartItem() {
		return cartItem;
	}

	public void setCartItem(Collection<CartItem> cartItem) {
		this.cartItem = cartItem;
	}
	
	
}
