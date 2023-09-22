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

import main.dto.RatingDTO;
import main.exception.RatingException;
import main.exception.UserException;
import main.requests.RatingRequest;
import main.service.RatingService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	private RatingService ratingService;
	
	
	public RatingController(RatingService ratingService) {
		this.ratingService = ratingService;
	}


	@PostMapping("")
	public ResponseEntity<?> addProductRatingHandler(@RequestBody RatingRequest req) throws UserException, RatingException{
			
		ratingService.addProductRating(req);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/updateRating")
	public ResponseEntity<?> updateRatingHandler(@RequestBody RatingRequest req) throws UserException, RatingException{
		
		ratingService.updateRating(req);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> deleteRatingHandler(@RequestParam Integer ratingId) throws RatingException{
		
		ratingService.deleteRating(ratingId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	@GetMapping("/findAll")
	public ResponseEntity<Page<RatingDTO>> findAllRatingsHandler(@RequestParam(defaultValue = "1") int  page){
		
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.findAllRatings(page));
	}
	

}
