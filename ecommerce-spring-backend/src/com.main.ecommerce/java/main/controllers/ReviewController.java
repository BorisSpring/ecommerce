package main.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import main.exception.ReviewException;
import main.model.ReviewDTO;
import main.requests.CreateReviewRequest;
import main.requests.UpdateReviewRequest;
import main.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public void addReviewHandler(@Valid @RequestBody CreateReviewRequest createReviewRequestreq,
                                 @RequestHeader("Authorization") String jwt) throws ReviewException{
       reviewService.addReview(createReviewRequestreq,jwt);
	}

	@PutMapping
    @ResponseStatus(HttpStatus.OK)
	public void updateReviewHandler(@Valid @RequestBody UpdateReviewRequest updateReviewRequest,
                                    @RequestHeader("Authorization") String jwt ) throws ReviewException{
        reviewService.updateReview(updateReviewRequest, jwt);
	}

	@DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteReviewHandler(@Positive(message = "Review id must be greather then zero!") @RequestParam int id,
                                    @RequestHeader("Authorization") String jwt) throws ReviewException{
        reviewService.deleteReview(id, jwt);
	}

	@GetMapping
	public ResponseEntity<Page<ReviewDTO>> findAllReviewsHandler(@Positive(message = "Page number must be greather then zero!")@RequestParam(defaultValue = "1") Integer pageNumber,
                                                                 @RequestParam(defaultValue = "id") String sortBy ){
		return ResponseEntity.ok(reviewService.findAll(pageNumber,sortBy));
	}
}
