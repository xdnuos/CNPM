package ptit.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Product saveProduct(Product product, int categoryId, int manuId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Page<Product> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> list;
        List<Product> allProduct = productDAO.findAllByStatus("active");
        
        int listProductSize = allProduct.size();
        if (listProductSize < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listProductSize);
            list = allProduct.subList(startItem, toIndex);
        }

        Page<Product> productPage
                = new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), listProductSize);

        return productPage;
    }
	
}
