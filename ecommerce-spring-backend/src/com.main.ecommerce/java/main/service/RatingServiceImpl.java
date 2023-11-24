package main.service;


import lombok.RequiredArgsConstructor;
import main.exception.ResourceNotFoundException;
import main.mappers.RatingMapper;
import main.model.RatingDTO;
import main.requests.UpdateRatingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import main.domain.Product;
import main.domain.Rating;
import main.domain.User;
import main.exception.RatingException;
import main.exception.UserException;
import main.repository.ProductRepository;
import main.repository.RatingRepository;
import main.requests.CreateRatingRequest;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService   {

	private final UserService userService;
	private final ProductService productService;
	private final RatingRepository ratingRepo;
	private final ProductRepository productRepo;

	private final RatingMapper ratingMapper;

	@Transactional
	@Override
	public void addProductRating(CreateRatingRequest req, String jwt) throws UserException {

		User user = userService.findUserFromJwt(jwt);
		Product product = productService.findProductById(req.getProductId());

		 ratingRepo.save(Rating.builder()
						.product(product)
						.user(user)
						.rating(req.getRating())
						.build());

		product.setNumOfRating(( product.getNumOfRating() + 1 ));

		productRepo.save(product);
	}

	@Transactional
	@Override
	public void deleteRating(int id, String jwt) throws RatingException {
		User requestUser = userService.findUserFromJwt(jwt);
		Rating rating = ratingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rating with id " + id + " doesnt exists!"));

		if(requestUser.getAuthority().getAuthority().equals("admin") || rating.getUser().getId().equals(requestUser.getId())){
			ratingRepo.deleteById(id);
		}else {
			throw new RatingException("You are not related to thsi rating!");
		}
	}

	@Transactional
	@Override
	public void updateRating(UpdateRatingRequest req, String jwt) throws UserException, RatingException {
		User requestUser = userService.findUserFromJwt(jwt);
		Rating rating = findRatingById(req.getRatingId());

		if(!rating.getUser().getId().equals(requestUser.getId())) {
			throw new UserException("User is not related to this rating");
		}
		rating.setRating(req.getRating());
		ratingRepo.save(rating);
	}

	@Override
	public Rating findRatingById(int id) throws RatingException {
		return ratingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rating with id " + id + " doesnt exists!"));
	}


	@Override
	public Page<RatingDTO> findAllRatings(int pageNumber){
		return ratingRepo.findAll(PageRequest.of((pageNumber - 1), 20)).map(ratingMapper::ratingToDto);
	}

}
