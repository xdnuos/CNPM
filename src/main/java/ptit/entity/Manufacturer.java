package ptit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="manufacturer")
public class Manufacturer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "manufacturerID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int manufacturerID;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
	@JsonManagedReference
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
