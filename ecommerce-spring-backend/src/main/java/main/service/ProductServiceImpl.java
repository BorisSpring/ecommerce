package main.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import main.dto.ProductDetailsDTO;
import main.entity.Category;
import main.entity.Product;
import main.entity.Section;
import main.entity.SectionItems;
import main.exception.ProductException;
import main.repository.CategoryRepository;
import main.repository.ProductRepository;
import main.repository.SectionItemRepository;
import main.repository.SectionRepository;
import main.requests.CreateProductRequest;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepo;
	private CategoryRepository categoryRepo;
	private SectionRepository sectionRepo;
	private SectionItemRepository sectionItemRepo;
	

	

	public ProductServiceImpl(ProductRepository productRepo, CategoryRepository categoryRepo,
			SectionRepository sectionRepo, SectionItemRepository sectionItemRepo) {
		this.productRepo = productRepo;
		this.categoryRepo = categoryRepo;
		this.sectionRepo = sectionRepo;
		this.sectionItemRepo = sectionItemRepo;
	}

	@Transactional
	@Override
	public Product createProduct(CreateProductRequest req) throws ProductException {
		
		Product product;
		
		if(req.getId() > 0 ) {
			product =  findProductById(req.getId());
		}else {
			product = new Product();
		}
		
		Category topCategory = categoryRepo.findByCategoryName(req.getTopLevelCategory());
		
		if(topCategory == null) {
			Category category = new Category();
			category.setCategoryName(req.getTopLevelCategory());
			
			Category savedCategory = categoryRepo.save(category);
			
			
		}
		Section section = sectionRepo.findBySectionName(req.getSecondLevelCategory());
		
		if(section == null) {

			Section newSection = new Section();
			newSection.setSectionName(req.getSecondLevelCategory());
			
			if(topCategory != null) {				
				newSection.setCategory(topCategory);
			}else {
				Category category = categoryRepo.findByCategoryName(req.getTopLevelCategory());
				newSection.setCategory(category);
			}
			Section savedSection = sectionRepo.save(newSection);

		}

		int totalQuantity = req.getSizes().stream().mapToInt( s -> s.getQuantity()).sum();
		product.setTitle(req.getTitle());
		product.setColors(req.getColor());
		product.setDescription(req.getDescription());
		
		if(req.getDiscountPrecent() > 0) {
			product.setDiscountPrecent(req.getDiscountPrecent());
			product.setDiscountPrice((req.getPrice() -(int)(req.getPrice() * ((double) req.getDiscountPrecent() / 100))));
		}else {
			product.setDiscountPrice(req.getPrice());
		}
		
		product.setName(req.getName());
		product.setHighlights(req.getHighlights());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSizes());
		product.setQuantity(totalQuantity);
		product.setCreatedAt(LocalDateTime.now());
		SectionItems sectionItem = sectionItemRepo.findByItemName(req.getThirdLevelCategory());
		
		if(sectionItem != null) {
			product.setSectionItem(sectionItem);
		}
		
		if(sectionItem == null) {
			Section oldSection = sectionRepo.findBySectionName(req.getSecondLevelCategory());
			SectionItems newSectionItem = new SectionItems();
			newSectionItem.setItemName(req.getThirdLevelCategory());
			newSectionItem.setSection(oldSection);
			SectionItems newItem = sectionItemRepo.save(newSectionItem);
			product.setSectionItem(newItem);
		}
			
			Product savedProduct = productRepo.save(product);
			if(savedProduct == null) {
				throw new ProductException("Failed to add product!");
			}
		return savedProduct;
	}
	
	@Transactional
	@Override
	public String deleteProduct(int productId) throws ProductException {
		
		
		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepo.delete(product);
		
		return "Product delted sucessfully";
	}
	
	


	@Override
	public Product findProductById(int id) {
			Optional<Product> opt = productRepo.findById(id);
		
		if(!opt.isPresent()) {
			throw new ProductException("Product with id" + id + " doesnt exists");
		}
		return opt.get();
	}

	


	@Override
	public Page<Product> findAllProductsByFiltersAndSectionItemName(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, String sort, String stock, Integer pageNumber, Integer pageSize, Integer discountRange) {
		
		pageNumber = pageNumber - 1;
		
		List<Product> listOfProducts = productRepo.filterProducts(category, colors, minPrice, maxPrice, sort, discountRange);

		
		
		if(colors != null && !colors.isEmpty()) {
			listOfProducts.stream().filter(product -> product.getColors().stream().anyMatch(color -> colors.contains(color))).collect(Collectors.toList());
		}
		
		if(stock != null) {
			
			if(stock.equalsIgnoreCase("in_stock")) {
				listOfProducts.stream().filter(product -> product.getQuantity() > 0).collect(Collectors.toList());
			}else {		
				listOfProducts.stream().filter(product -> product.getQuantity() == 0).collect(Collectors.toList());
			}
		}
		
		if(listOfProducts.size() <= pageSize) {
			return new PageImpl<>(listOfProducts);
		}
		
		PageRequest pageable = PageRequest.of(pageNumber, pageSize);
		
		int totalPages = (int) Math.ceil((double) listOfProducts.size() / pageSize);
		int beginIndex = pageNumber * pageSize;
		int endIndex;
		
		if((pageNumber+1) == totalPages) {
			endIndex = listOfProducts.size();
		}else {
			endIndex = beginIndex + pageSize;
		}
		
		List<Product> pageContent = listOfProducts.subList(beginIndex, endIndex);
		
		
	     return new PageImpl<>(pageContent, pageable, listOfProducts.size());

	}

	
	
	@Override
	public List<Product> findProductByQuery(String query) {
		return productRepo.findProductByQuery(query);
	}

	@Override
	public boolean existsById(int productId)  {
		return productRepo.existsById(productId);
	}

	

	public ProductDetailsDTO findProductDetails(int productId) {
		
		Product product = findProductById(productId);
		
		ProductDetailsDTO details = new ProductDetailsDTO();
		details.setCategoryName(product.getSectionItem().getSection().getCategory().getCategoryName());
		details.setProduct(product);
		details.setReviews(product.getReviews());
		details.setRatings(product.getRaitings());
		details.setSectionName(product.getSectionItem().getSection().getSectionName());
		
		ArrayList<String> userNames = new ArrayList<>();
		product.getReviews().stream().forEach(p -> userNames.add(p.getUser().getFirstName()));
		details.setUserNames(userNames);
		return details;
		
	}

	@Override
	public Page<Product> findAllProducts(Integer pageNumber, Integer pageSize) {

		pageNumber-=1;
		
		
		List<Product> allProducts = productRepo.findAll();
		
		
		PageRequest pageable = PageRequest.of(pageNumber, pageSize);
		
		int totalPages = (int) Math.ceil((double)allProducts.size() / pageSize);
		int beginIndex = pageNumber * pageSize;
		int endIndex;
		
		if( (pageNumber + 1) == totalPages) {
			endIndex = allProducts.size();
		}else {
			endIndex = beginIndex + pageSize;
		}
		
		
		return new PageImpl<Product>(allProducts.subList(beginIndex, endIndex), pageable, allProducts.size());
	}

	@Override
	public List<Product> findSimilarProducts(String itemName, int limit) {
		 
		List<Product> products = productRepo.findFirst20BySectionItemItemName(itemName);
		
		 if(products.size() <= limit) {
			 return products;
		 }
		 
		return products.subList(0, limit);
		 
	}


}
