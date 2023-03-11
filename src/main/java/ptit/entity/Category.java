package ptit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name="category")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "categoryID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int categoryID;
	
	@Column(name = "title", length = 100)
	private String title;
	
    @ManyToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Collection<Product> productcate = new ArrayList<>();

	public Category() {
		super();
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Product> getProducts() {
		return productcate;
	}

	public void setProducts(Collection<Product> products) {
		this.productcate = products;
	}

	public Collection<Product> getProductcate() {
		return productcate;
	}

	public void setProductcate(Collection<Product> productcate) {
		this.productcate = productcate;
	}
	
}
