package main.service;


import org.springframework.data.domain.Page;

import main.dto.RatingDTO;
import main.entity.Rating;
import main.exception.RatingException;
import main.exception.UserException;
import main.requests.RatingRequest;

public interface RatingService {

	public void addProductRating(RatingRequest req) throws RatingException, UserException;
	
	public void deleteRating(int ratingId) throws RatingException;
	
	public void updateRating(RatingRequest req) throws UserException , RatingException;
	
	public Rating findRatingById(int id) throws RatingException;

	public Page<RatingDTO> findAllRatings(int pageNumber);

}
