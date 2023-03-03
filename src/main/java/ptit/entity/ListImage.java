package ptit.entity;

import jakarta.persistence.*;

@Entity
@Table(name="listImage")
public class ListImage {
	
	@Id
	@GeneratedValue
	private Long listImageID;
	
	@Column(name = "images")
	private String images;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productID")
    private Product product;

	public ListImage(Long listImageID, String images, Long productID, Product product) {
		super();
		this.listImageID = listImageID;
		this.images = images;
		this.product = product;
	}

	public Long getListImageID() {
		return listImageID;
	}

	public void setListImageID(Long listImageID) {
		this.listImageID = listImageID;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
    
    
}
