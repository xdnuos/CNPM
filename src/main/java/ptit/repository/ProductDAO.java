package ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ptit.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Long> {
	List<Product> findAll();
}
