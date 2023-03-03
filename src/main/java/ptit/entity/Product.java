package ptit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import jakarta.validation.constraints.*;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name="product")
public class Product implements Serializable{
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
    private Collection<Manufacturer> manufacturers;
    
    @ManyToMany(mappedBy = "products")
    private Collection<Category> categories;
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Collection<ListImage> listImages;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Collection<OrderItem> oderItem;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Collection<CartItem> cartItem;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Product(Long productID, @Size(min = 1, max = 100) String name,
			@Digits(integer = 9, fraction = 3) BigDecimal price, @Size(min = 20, max = 300) String description,
			int quantity, boolean status, Collection<Manufacturer> manufacturers, Collection<Category> categories,
			Collection<ListImage> listImages) {
		super();
		this.productID = productID;
		this.name = name;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.status = status;
		this.manufacturers = manufacturers;
		this.categories = categories;
		this.listImages = listImages;
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

	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

	public Collection<ListImage> getListImages() {
		return listImages;
	}

	public void setListImages(Collection<ListImage> listImages) {
		this.listImages = listImages;
	}
	
	
}
