package main.service;


import main.model.ReviewDTO;
import main.requests.UpdateReviewRequest;
import org.springframework.data.domain.Page;
import main.domain.Review;
import main.exception.ReviewException;
import main.requests.CreateReviewRequest;

public interface ReviewService {
	
	 Review findRewiewById(int id) throws ReviewException;
	
	 void addReview(CreateReviewRequest req, String jwt) throws ReviewException;
	
	 void updateReview(UpdateReviewRequest updateReviewRequest, String jwt) throws ReviewException;
	
	 void deleteReview(int id, String jwt) throws ReviewException;

	 Page<ReviewDTO> findAll(int pageNumber, String sortBy);

}
