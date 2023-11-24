package main.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import main.domain.*;
import main.exception.ResourceNotFoundException;
import main.mappers.ProductMapper;
import main.model.ProductDTO;
import main.model.ProductPageList;
import main.repository.SectionItemRepository;
import main.requests.ProductSearchRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import main.model.ProductDetailsDTO;
import main.exception.ProductException;
import main.repository.ProductRepository;
import main.requests.CreateProductRequest;

@Getter
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements  ProductService  {

	private final ProductRepository productRepository;
	private final SectionItemService sectionItemService;

	private final SectionItemRepository sectionItemRepository;
	private final ProductMapper productMapper;

	@Value("${upload.dir}")
	private String uploadDir;

	@Transactional
	@Override
	public void createProduct(CreateProductRequest req) throws ProductException, IOException {
		SectionItems productSectionItem = sectionItemService.findById(req.getSectionItemId());

		List<String> imageNames = new ArrayList<>();

		Path uploadPath = Paths.get(uploadDir);

		if(!Files.exists(uploadPath))
			Files.createDirectories(uploadPath);

		req.getImageFiles().forEach(multipartFile -> {
			if(multipartFile != null && !multipartFile.isEmpty() &&
					multipartFile.getOriginalFilename().endsWith(".jpg") ||
					multipartFile.getOriginalFilename().endsWith(".png")){
				String imageName = UUID.randomUUID() + multipartFile.getOriginalFilename();
				try {
					Files.copy(multipartFile.getInputStream(), uploadPath.resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
					imageNames.add(imageName);
				} catch (IOException e) {
					throw new ProductException("Fail to upload product images!");
				}
			}
		});

		productRepository.save(Product.builder()
				.brand(req.getBrand())
				.title(req.getTitle())
				.name(req.getName())
				.price(req.getPrice())
				.discountPrecent(req.getDiscountPrecent() == null ? 0 : req.getDiscountPrecent())
				.discountPrice(req.getDiscountPrecent() == null ? req.getPrice() : (req.getPrice() - (req.getPrice() * (req.getDiscountPrecent()) / 100)))
				.highlights(req.getHighlights())
				.colors(req.getColors())
				.description(req.getDescription())
				.sectionItem(productSectionItem)
				.sizes(req.getSizes())
				.imageUrl(imageNames)
				.quantity(req.getSizes().stream().mapToInt(Size::getQuantity).sum())
				.build());
	}

	@Transactional
	@Override
	public void deleteProduct(int productId) throws ProductException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " doesnt exists!"));

		Path path = Paths.get(uploadDir);

		product.getImageUrl().forEach(imageName -> {
			Path imagePath = path.resolve(imageName);
			if(Files.exists(imagePath)) {
				try {
					Files.delete(imagePath);
				} catch (IOException e) {
					throw new ProductException("Fail to delete image!");
				}
			}
		});
		productRepository.deleteById(productId);
	}

	@Override
	public Product findProductById(int productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product with productId " + productId + " doesnt exists!"));
	}

	@Override
	public ProductDetailsDTO findProductDetails(int productId) {
		return productMapper.productToProductDetailsDTO(findProductById(productId));
	}

	@Override
	public ProductPageList findAllProducts(PageRequest pageRequest) {
		Page<Product> all = productRepository.findAll(pageRequest);
		return new ProductPageList(all.getContent(), pageRequest, all.getTotalElements());
	}


	@Override
	public Page<ProductDTO> findProductByQuery(String searchQuery, ProductSearchRequest productSearchRequest) {
		PageRequest pageRequest = PageRequest.of((productSearchRequest.getPage() - 1), productSearchRequest.getPageSize());

		Double min = null;
		Double max = null;

		if(!productSearchRequest.getPrice().isEmpty()){
			List<Double> pricesRanges = productSearchRequest.getPrice().stream()
					.flatMap(s -> Arrays.stream(s.split("-")))
					.map(Double::parseDouble)
					.sorted()
					.collect(Collectors.toList());
			min = pricesRanges.get(0);
			max = pricesRanges.get(pricesRanges.size()-1);
		}

		Integer discountRange = null;

		if(!productSearchRequest.getDiscountRange().isEmpty()){
			discountRange = productSearchRequest.getDiscountRange().stream().max(Comparator.naturalOrder()).get();
		}

		System.out.println(productSearchRequest.getSize().toString());
		return 	productRepository.findFilteredProducts(
						productSearchRequest.getColor().isEmpty() ? null : productSearchRequest.getColor(),
						productSearchRequest.getCategory() == null ? null : productSearchRequest.getCategory(),
						productSearchRequest.getColor().isEmpty() ? null : productSearchRequest.getColor(),
						min,
						max,
						productSearchRequest.getSort() == null ? null : productSearchRequest.getSort(),
						discountRange,
						productSearchRequest.getSize().isEmpty() ? null : productSearchRequest.getSize(),
						productSearchRequest.getSize().isEmpty()? null : productSearchRequest.getSize(),
						pageRequest)
						.map(productMapper::productToDTO);
	}


	@Override
	public List<ProductDTO> findSimilarProducts(String itemName, int limit) {
		List<Product> products = productRepository.findFirst20BySectionItemItemNameOrderByCreatedAtDesc(itemName);

		 if(products.size() <= limit)
			 return products.stream().map(productMapper::productToDTO).collect(Collectors.toList());

		return products.subList(0, limit).stream().map(productMapper::productToDTO).collect(Collectors.toList());
	}


	@Override
	public List<ProductDTO> get10ProductsWithSectionId(Integer sectionItemId) {
		return  productRepository.findTop10BySectionItemId(sectionItemId).stream().map(productMapper::productToDTO).collect(Collectors.toList());
	}


}
