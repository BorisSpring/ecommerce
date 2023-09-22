package main.service;


import org.springframework.data.domain.Page;

import main.entity.Review;
import main.exception.ReviewException;
import main.requests.RewiewRequest;

public interface ReviewService {
	
	public Review findRewiewById(int id) throws ReviewException;
	
	public int addReview(RewiewRequest req) throws ReviewException;
	
	public void updateReview(RewiewRequest req) throws ReviewException;
	
	public void deleteReview(int id) throws ReviewException;

	public Page<main.dto.ReviewDTO> findAll(int pageNumber, String sortBy);

}
