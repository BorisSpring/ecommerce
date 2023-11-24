package main.service;


import main.requests.UpdateRatingRequest;
import org.springframework.data.domain.Page;

import main.model.RatingDTO;
import main.domain.Rating;
import main.exception.RatingException;
import main.exception.UserException;
import main.requests.CreateRatingRequest;

public interface RatingService {

	 void addProductRating(CreateRatingRequest createRatingRequest, String jwt) throws RatingException, UserException;
	
	 void deleteRating(int ratingId, String jwt) throws RatingException;
	
	 void updateRating(UpdateRatingRequest updateRatingRequest, String jwt) throws UserException , RatingException;
	
	 Rating findRatingById(int id) throws RatingException;

	 Page<RatingDTO> findAllRatings(int pageNumber);

}
