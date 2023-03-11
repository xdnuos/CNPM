package ptit.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ptit.entity.Product;

public interface ProductService {
 Product findById(Long id);
 void save(Product product);
 Page<Product> findPaginated(Pageable pageable);
 List<Product> findAll();
 Page<Product> findAll(Pageable pageable);
 List<Product> findProductCategoryALL(long categoryID);
 Product findProductByName(String name);
 Page<Product> convertListToPage(List<Product> productList, int pageNumber, int pageSize);
 
}
