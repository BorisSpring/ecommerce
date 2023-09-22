package main.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import main.entity.Product;
import main.entity.Rating;
import main.entity.User;
import main.exception.RatingException;
import main.exception.UserException;
import main.repository.ProductRepository;
import main.repository.RatingRepository;
import main.requests.RatingRequest;

@Service
public class RatingServiceImpl implements RatingService {

	private UserService userService;
	private ProductService productService;
	private RatingRepository ratingRepo;
	private ProductRepository productRepo;

	

	public RatingServiceImpl(UserService userService, ProductService productService, RatingRepository ratingRepo,
			ProductRepository productRepo) {
		this.userService = userService;
		this.productService = productService;
		this.ratingRepo = ratingRepo;
		this.productRepo = productRepo;
	}

	@Transactional
	@Override
	public void addProductRating(RatingRequest req) throws RatingException, UserException {
		
		User user = userService.findUserById(req.getUserId());
		Product product = productService.findProductById(req.getProductId());
		
		Rating rating = new Rating();
		rating.setCreatedAt(LocalDateTime.now());
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		
		Rating savedRating = ratingRepo.save(rating);
		
		if(savedRating == null) {
			throw new RatingException("Failed to create rating!");
		}
		product.setNumOfRating(product.getNumOfRating()+1);
		
		productRepo.save(product);
	}

	@Transactional
	@Override
	public void deleteRating(int id) throws RatingException {
		
		if(ratingRepo.existsById(id) ){
			ratingRepo.deleteById(id);
		}else {
			throw new RatingException("Rating doesnt exists!");
		}
		
	}

	@Transactional
	@Override
	public void updateRating(RatingRequest req) throws UserException, RatingException {
		
		Rating rating = findRatingById(req.getRatingId());
		
		if(!(rating.getUser().getId() == req.getUserId())) {
			throw new UserException("User is not realted to this rating");
		}
		rating.setRating(req.getRating());
		
		Rating updatedRating = ratingRepo.save(rating);
		
		if(updatedRating == null) {
			throw new RatingException("Failed to update Rating");
		}
	}

	@Override
	public Rating findRatingById(int id) throws RatingException {
		
		Optional<Rating> rating = ratingRepo.findById(id);
		
		if(!rating.isPresent()) {
			throw new RatingException("Rating with id " + id + " doesnt exist!");
		}
		return rating.get();
	}


	public Page<main.dto.RatingDTO> findAllRatings(int pageNumber){
		
		
		pageNumber-=1;
		
		List<main.dto.RatingDTO> allRatings = ratingRepo.findAllRatings();
		
		if(allRatings.size() <= 20) {
			return new PageImpl<>(allRatings);
		}
		
		int totalPages = (int) Math.ceil((double) allRatings.size() / 20);
		int beginIndex = pageNumber * 20;
		int endIndex;
		
		if((pageNumber + 1) == totalPages) {
			endIndex = allRatings.size();
		}else {
			endIndex = beginIndex + 20;
		}
		
		PageRequest pageable = PageRequest.of(pageNumber, 20);
				
		return new PageImpl<>(allRatings.subList(beginIndex, endIndex), pageable, allRatings.size());
		
	}
	
}
