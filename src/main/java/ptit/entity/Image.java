package ptit.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="listImage")
public class Image implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long listImageID;
	
	@Lob
	@Column(name = "images",length = Integer.MAX_VALUE, nullable = true)
	private byte[] images;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productID")
    private Product product;
	
	private String imageName;
	
	public Image() {
		super();
	}

	public Long getListImageID() {
		return listImageID;
	}

	public void setListImageID(Long listImageID) {
		this.listImageID = listImageID;
	}

	public byte[] getImages() {
		return images;
	}

	public void setImages(byte[] images) {
		this.images = images;
	}


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
    
	private String imageBase64;

	public String getImageBase64() {
	    return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
	    this.imageBase64 = imageBase64;
	}
}
