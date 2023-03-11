package ptit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	
	@OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private List<Product> product = new ArrayList<>();

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

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}
}
