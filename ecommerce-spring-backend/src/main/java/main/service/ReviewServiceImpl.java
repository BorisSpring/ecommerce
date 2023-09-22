package main.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import main.dto.ReviewDTO;
import main.entity.Product;
import main.entity.Review;
import main.entity.User;
import main.exception.ReviewException;
import main.repository.ProductRepository;
import main.repository.ReviewRepository;
import main.requests.RewiewRequest;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

	private UserService userService;
	private ProductService productService;
	private ReviewRepository reviewRepo;
	private ProductRepository productRepository;
	
	

	public ReviewServiceImpl(UserService userService, ProductService productService, ReviewRepository reviewRepo,
			ProductRepository productRepository) {
		this.userService = userService;
		this.productService = productService;
		this.reviewRepo = reviewRepo;
		this.productRepository = productRepository;
	}

	@Override
	public Review findRewiewById(int id) throws ReviewException {
		 
		Optional<Review> opt = reviewRepo.findById(id);
		
		if(!opt.isPresent()) {
			throw new ReviewException("Review with id " + id + " not found!");
		}
		
		return opt.get();
	}

	@Transactional
	@Override
	public int addReview(RewiewRequest req) throws ReviewException {
		
		User user = userService.findUserById(req.getUserId());
		Product product = productService.findProductById(req.getProductId());
		
		Review review = new Review();
		review.setCreatedAt(LocalDateTime.now());
		review.setProduct(product);
		review.setUser(user);
		review.setReview(req.getContent());

		product.setNumOfRewies(product.getNumOfRewies() + 1);
		
		Product updatedProduct = productRepository.save(product);
		
		Review savedReview = reviewRepo.save(review);
		
		if(savedReview == null || updatedProduct == null) {
			throw new ReviewException("Failed to add review!");
		}
		return savedReview.getId();
	}

	@Transactional
	@Override
	public void updateReview(RewiewRequest req) throws ReviewException {
		
		Review review = findRewiewById(req.getReviewId());
		
		
		User user = userService.findUserById(req.getUserId());
		Product product = productService.findProductById(req.getProductId());
		
		if( (review.getUser().getId() != req.getUserId()) || (review.getProduct().getId() != req.getProductId())) {
			throw new ReviewException("Request is not related to this Review");
		}
		
		review.setReview(req.getContent());
		Review updatedReview = reviewRepo.save(review);
		
		if(updatedReview == null) {
			throw new ReviewException("Failed to update review!");
		}
	}

	@Transactional
	@Override
	public void deleteReview(int id) throws ReviewException {
		
		if(reviewRepo.existsById(id)) {
			reviewRepo.deleteById(id);
		}else {
			throw new ReviewException("Review doesnt exists");
		}
	}

	@Override
	public Page<ReviewDTO> findAll(int pageNumber, String sortBy) {
		
		pageNumber-=1;
		
		 java.util.List<ReviewDTO> allReviews = reviewRepo.findAllDTO();
		 
		 
		 if(allReviews.size() <= 20) {
			 return new PageImpl<>(allReviews);
		 }
		 
		 int totalPages = (int) Math.ceil((double)allReviews.size() / 20); 
		 int beginIndex = pageNumber * 20;
		 int endIndex;
		 
		 
		 Sort sort;
		 
		 if(sortBy == "ASC") {
			 sort = Sort.by(Sort.Order.asc("createdAt"));
		 }else if(sortBy == "DESC") {
			 sort = Sort.by(Sort.Order.desc("createdAt"));
		 }else {
			 sort = Sort.by(Sort.Order.asc("id"));
		 }
		 
		 
		 
		 if((pageNumber + 1) == totalPages) {
			 endIndex = allReviews.size();
		 }else {
			 endIndex = beginIndex + 20;
		 }
		 
		 PageRequest pageable = PageRequest.of(pageNumber, 20, sort);
		 
		 PageImpl<ReviewDTO> page = new PageImpl<>(allReviews.subList(beginIndex, endIndex), pageable, allReviews.size());
		
		 page.forEach(c ->{
			 LocalDateTime localDateTime = c.getCreatedAt();
			 Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

		 });
		 
		 return page;
	}



}
