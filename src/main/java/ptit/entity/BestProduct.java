package ptit.entity;

import java.io.Serializable;

public class BestProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long productID;
	private long count;
	public long getProductID() {
		return productID;
	}
	public void setProductID(long productID) {
		this.productID = productID;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}

	public BestProduct(long productID, long count) {
		super();
		this.productID = productID;
		this.count = count;
	}
	
	
}
