package ptit.entity;

import java.util.Collection;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name="category")
public class Category {
	@Id
    @Column(name = "categoryID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long manufacturerID;
	
	@Nonnull
	@Column(name = "title", length = 100)
	private String title;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "productCategory",
    joinColumns = @JoinColumn(name = "categoryID"),
    inverseJoinColumns = @JoinColumn(name = "productID"))
    private Collection<Product> products;

	public Category(Long manufacturerID, String title, Collection<Product> products) {
		super();
		this.manufacturerID = manufacturerID;
		this.title = title;
		this.products = products;
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(Long manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	
}
