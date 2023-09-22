package main.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dto.ReviewDTO;
import main.exception.ReviewException;
import main.requests.RewiewRequest;
import main.service.ReviewService;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

	private ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	

	@PostMapping("")
	public ResponseEntity<Integer> addReviewHandler(@RequestBody RewiewRequest req) throws ReviewException{
		
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addReview(req));
	}
	
	@PostMapping("/updateReview")
	public ResponseEntity<?> updateReviewHandler(@RequestBody RewiewRequest req) throws ReviewException{
		
		reviewService.updateReview(req);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> deleteReviewHandler(@RequestParam int id) throws ReviewException{
		
		reviewService.deleteReview(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("")
	public ResponseEntity<Page<ReviewDTO>> findAllReviewsHandler(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "id") String sortByDate ){
		
		return ResponseEntity.status(HttpStatus.OK).body(reviewService.findAll(page, sortByDate));
	}
}
