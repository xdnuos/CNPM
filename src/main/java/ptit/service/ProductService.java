package ptit.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ptit.entity.Product;

public interface ProductService {
 Product findById(Long id);
 void save(Product product);
 Page<Product> findPaginated(Pageable pageable);
}
