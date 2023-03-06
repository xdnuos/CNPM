package ptit.service;


import ptit.entity.Product;

public interface ProductService {
 Product findById(Long id);
 void save(Product product);
}
