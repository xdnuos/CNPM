package ptit.entity;

import java.util.Collection;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name="manufacturer")
public class Manufacturer {
	@Id
    @Column(name = "manufacturerID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long manufacturerID;
	
	@Nonnull
	@Column(name = "name", length = 100)
	private String name;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "productManufactor",
    joinColumns = @JoinColumn(name = "manufacturerID"),
    inverseJoinColumns = @JoinColumn(name = "productID"))
    private Collection<Product> products;

	public Manufacturer(Long manufacturerID, String name, Collection<Product> products) {
		super();
		this.manufacturerID = manufacturerID;
		this.name = name;
		this.products = products;
	}

	public Manufacturer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(Long manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	
}
