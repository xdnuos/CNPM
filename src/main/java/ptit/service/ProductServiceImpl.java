package ptit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.entity.Product;
import ptit.repository.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public Product findById(Long id) {
		return this.productDAO.findById(id).orElse(null);
	}

	@Override
	public void save(Product product) {
		this.productDAO.save(product);
	}
	
}
