package main.service;

import java.util.List;

import org.springframework.data.domain.Page;

import main.dto.ProductDetailsDTO;
import main.entity.Product;
import main.exception.ProductException;
import main.requests.CreateProductRequest;

public interface ProductService {

	
	public Product createProduct(CreateProductRequest req) throws ProductException;
	
	public String deleteProduct(int productId) throws ProductException;
		
	public Product findProductById(int id);
	
	public List<Product> findProductByQuery(String query);
	
	
	public boolean existsById(int productId);
	
	
	public Page<Product> findAllProductsByFiltersAndSectionItemName(String sectionItem, List<String> colors, List<String> sizes, Integer minPrice ,Integer maxPrice, String sort, String stock, Integer pageNumber, Integer pageSize, Integer minDiscount);

	
	public ProductDetailsDTO findProductDetails(int productId);
	

	Page<Product> findAllProducts(Integer pageNumber, Integer pageSize);

	public List<Product> findSimilarProducts(String itemName, int limit);
}
