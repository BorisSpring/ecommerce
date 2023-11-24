package main.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import main.model.ProductDTO;
import main.model.ProductPageList;
import main.requests.ProductSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import main.model.ProductDetailsDTO;
import main.requests.CreateProductRequest;
import main.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

	private final ProductService productService;


	@GetMapping("/{productId}")
	public ResponseEntity<ProductDetailsDTO> findProductById(@Positive(message = "Product id must be greather then zero!") @PathVariable("productId") Integer productId){
		return  ResponseEntity.ok(productService.findProductDetails(productId));
	}

	@DeleteMapping("/{productId}")
	public void deleteProductById(@Positive(message = "Product is must be greather then zero!") @PathVariable("productId") Integer productId){
		productService.deleteProduct(productId);
	}

	@GetMapping
	public ResponseEntity<ProductPageList> findAllProducts(@Positive(message = "Page number must be greather then zero!")@RequestParam(required = false, defaultValue = "12") Integer pageSize,
														   @Positive(message = "Page number must be greather then zero!")@RequestParam(required = false, defaultValue = "1") Integer pageNumber){
		return ResponseEntity.ok(productService.findAllProducts(PageRequest.of((--pageNumber), pageSize)));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void handleCreateProduct(@Valid @RequestBody CreateProductRequest createProductRequest) throws IOException {
		productService.createProduct(createProductRequest);
	}

	@GetMapping("/search/{searchQuery}")
	public ResponseEntity<Page<ProductDTO>> findSearchedProducts(@PathVariable("searchQuery") String searchQuery,@Valid @ModelAttribute ProductSearchRequest productSearchRequest){
		return ResponseEntity.status(HttpStatus.OK).body(productService.findProductByQuery(searchQuery, productSearchRequest));
	}

	@GetMapping("/similar/{itemName}/{numberOfElements}")
	public ResponseEntity<List<ProductDTO>> getSimilarProducts(@NotBlank(message = "Item name is required!") @Size(min = 3, max = 50, message = "Item name must be between 3 and 50 chars!") @PathVariable String itemName,
                                                            @Positive(message = "Number of elements must be positive!" )@PathVariable Integer numberOfElements){
        return ResponseEntity.ok(productService.findSimilarProducts(itemName,numberOfElements));
	}

}
