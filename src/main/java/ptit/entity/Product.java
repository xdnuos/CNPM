package ptit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Size(min = 4, max = 100,message = "Tên sản phẩm phài từ 1-100 kí tự")
    @Column(name = "name", length = 100)
    @NotEmpty(message = "Vui lòng nhập tên sản phẩm")
    private String name;
    
    @Digits(integer = 9, fraction = 3)
    @Column(name = "price")
    @Min(value=1, message="Giá sản phẩm không hợp lệ") 
    private BigDecimal price;
    
    @Size(min = 20, max = 300, message="Độ dài mô tả từ 20-300 kí tự ")
    @Column(name = "description", length = 300)
    private String description;
    
    @Column(name = "quantity")
    @Min(value=1, message="Số lượng sản phẩm không hợp lệ") 
    private int quantity;
    
    @Column(name="status")
    private boolean status;
    
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "product_manufactor",
	joinColumns = @JoinColumn(name = "productID"),
	inverseJoinColumns = @JoinColumn(name = "manufacturerID"))
    private List<Manufacturer> manufacturers = new ArrayList<>();
    
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "product_category",
    joinColumns = @JoinColumn(name = "productID"),
    inverseJoinColumns = @JoinColumn(name = "categoryID"))
    private List<Category> category = new ArrayList<>();
    
//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
//    private List<ListImage> listImages = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> oderItem = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItem = new ArrayList<>();

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

	public List<Manufacturer> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(Manufacturer newManufacturer) {
		List<Manufacturer> listManu = new ArrayList<>();
		listManu.add(newManufacturer);
		this.manufacturers = listManu;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(Category newCategory) {
		List<Category> listCate = new ArrayList<>();
		listCate.add(newCategory);
		this.category = listCate;
	}

//	public List<ListImage> getListImages() {
//		return listImages;
//	}
//
//	public void setListImages(List<ListImage> listImages) {
//		this.listImages = listImages;
//	}

	public List<OrderItem> getOderItem() {
		return oderItem;
	}

	public void setOderItem(List<OrderItem> oderItem) {
		this.oderItem = oderItem;
	}

	public List<CartItem> getCartItem() {
		return cartItem;
	}

	public void setCartItem(List<CartItem> cartItem) {
		this.cartItem = cartItem;
	}
}
