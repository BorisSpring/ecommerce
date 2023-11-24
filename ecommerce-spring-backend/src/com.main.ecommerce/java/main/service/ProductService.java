package main.service;


import main.domain.Product;
import main.exception.ProductException;
import main.model.ProductDTO;
import main.model.ProductDetailsDTO;
import main.model.ProductPageList;
import main.requests.CreateProductRequest;
import main.requests.ProductSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.io.IOException;
import java.util.List;

public interface ProductService {

	
	 void createProduct(CreateProductRequest req) throws ProductException, IOException;
	
	 void deleteProduct(int productId) throws ProductException;
		
	 Product findProductById(int id);

	 ProductDetailsDTO findProductDetails(int productId);

	 ProductPageList findAllProducts(PageRequest pageRequest);

	Page<ProductDTO> findProductByQuery(String searchQuery, ProductSearchRequest productSearchRequest);

	List<ProductDTO> findSimilarProducts(String itemName, int limit);


	 List<ProductDTO> get10ProductsWithSectionId(Integer sectionItemNameId);


}
