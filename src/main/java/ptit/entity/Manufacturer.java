package ptit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name="manufacturer")
public class Manufacturer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "manufacturerID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int manufacturerID;
	
	@Nonnull
	@Column(name = "name", length = 100)
	private String name;
	
	@ManyToMany(mappedBy = "manufacturers", cascade = CascadeType.ALL)
    private Collection<Product> productmanu = new ArrayList<>();

	public int getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(int manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Product> getProducts() {
		return productmanu;
	}

	public void setProducts(Collection<Product> products) {
		this.productmanu = products;
	}
}
