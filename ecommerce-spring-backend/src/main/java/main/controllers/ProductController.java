package main.controllers;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import main.dto.ProductDetailsDTO;
import main.entity.Category;
import main.entity.Product;
import main.repository.CategoryRepository;
import main.requests.CreateProductRequest;
import main.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;
	private CategoryRepository categoryRepo;
	

	public ProductController(ProductService productService, CategoryRepository categoryRepo) {
		this.productService = productService;
		this.categoryRepo = categoryRepo;
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(@PathVariable("category") String category,
																	  @RequestParam(required = false) List<String> colors, 
																	  @RequestParam(required = false) List<String> size ,
																	  @RequestParam(required = false) List<String> price,
																	  @RequestParam(required = false) List<Integer> discountRange, 
																	  @RequestParam(required = false) String sort,  
																	  @RequestParam(required = false) String stock, 
																	  @RequestParam(defaultValue = "1") Integer page, 
																	  @RequestParam(defaultValue = "12") Integer pageSize){
		
		Integer minPrice = null;
		Integer maxPrice = null; 
		if(price != null) {
		    List<Integer> priceRanges= price.stream()
		    		.flatMap(priceRange -> Arrays.stream(priceRange.split("-")))
		    		.mapToInt(Integer::parseInt)
		    		.sorted()
		    		.boxed()
		    		.collect(Collectors.toList());
			
		    minPrice = priceRanges.get(0);
		    maxPrice = priceRanges.get(priceRanges.size()-1);
		}
		
		Integer discountAbove = null;
		if(discountRange != null) {			
			discountAbove = discountRange.stream().max(Integer::compareTo).orElse(null);
		}
		Page<Product> productsPage = productService.findAllProductsByFiltersAndSectionItemName(category, colors, size, minPrice, maxPrice, sort, stock, page, pageSize, discountAbove);
		
		return ResponseEntity.status(HttpStatus.OK).body(productsPage);
	}

	//DODATI OVO
	
	@GetMapping("/search/{query}")
	public ResponseEntity<List<Product>> findSearchedProducts(@PathVariable String query){
		
		List<Product> products = productService.findProductByQuery(query);
		
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
	
	@PostMapping("")
	public ResponseEntity<?> handleCreateProduct(@RequestBody CreateProductRequest req){
		
		Product createdProduct = productService.createProduct(req);
		URI location = UriComponentsBuilder.fromPath("/product/" + createdProduct.getId()).build().toUri();
		
				
		
		return ResponseEntity.created(location).build();
	}
			
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProductHandler(@PathVariable Integer id){
		
		productService.deleteProduct(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PostMapping("/findCategories")
	public ResponseEntity<List<Category>> findCategoriesHandler(){
			
			return ResponseEntity.status(HttpStatus.OK).body(categoryRepo.findAll());
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDetailsDTO> getProductDetails(@PathVariable int id){
		
		System.out.println("u metodi je");
		return ResponseEntity.status(HttpStatus.OK).body(productService.findProductDetails(id));
	}

	
	@PostMapping("/similar/{itemName}/{numberOfElements}")
	public ResponseEntity<List<Product>> getSimilarProducts(@PathVariable String itemName, @PathVariable int numberOfElements){

		
		return ResponseEntity.status(HttpStatus.OK).body(productService.findSimilarProducts(itemName, numberOfElements));
	}
	
	@GetMapping("/all")
	public ResponseEntity<Page<Product>> getAllProductsHandler(@RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue="12") Integer size){
		
		
		return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProducts(page, size));
	}


}
