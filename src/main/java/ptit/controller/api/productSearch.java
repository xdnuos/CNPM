package ptit.controller.api;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ptit.DTO.ProductDto;
import ptit.service.ProductService;

@RestController
public class productSearch {
    @Autowired
    private ProductService productService;
    
    @GetMapping("/admin/stock/search")
    public List<ProductDto> searchProducts(@RequestParam("keyword") String keyword) {
        List<ProductDto> searchResults = productService.searchProducts(keyword);
        return searchResults;
    }

}
