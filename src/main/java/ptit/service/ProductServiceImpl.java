package ptit.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.entity.Category;
import ptit.entity.Manufacturer;
import ptit.entity.Product;
import ptit.repository.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ManufacturerService manufacturerService;
	
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
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());
        newProduct.setDescription(product.getDescription());

//        newProduct.getCategory().addAll(product
//        		.getCategory()
//        		.stream()
//        		.map(v->{
//        			Category vv = categoryService.findByCategoryID(categoryId);
//        			vv.getProducts().add(newProduct);
//        			return vv;
//        		}).collect(Collectors.toList()));
        newProduct.getManufacturers().addAll(product
        		.getManufacturers()
        		.stream()
        		.map(c->{
        			Manufacturer manu = manufacturerService.findByManufacturerID(1000);
        			manu.getProducts().add(newProduct);
        			return manu;
        		}).collect(Collectors.toList()));
        return productDAO.save(newProduct);
	}
	
	
}
