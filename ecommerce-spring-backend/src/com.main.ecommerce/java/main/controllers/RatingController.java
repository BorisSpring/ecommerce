package main.controllers;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import main.exception.RatingException;
import main.exception.UserException;
import main.model.RatingDTO;
import main.requests.CreateRatingRequest;
import main.requests.UpdateRatingRequest;
import main.service.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@Validated
public class RatingController {

	private final RatingService ratingService;

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public void addProductRatingHandler(@Valid @RequestBody CreateRatingRequest createRatingRequest,
                                        @RequestHeader("Authorization") String jwt) throws RatingException {
        ratingService.addProductRating(createRatingRequest, jwt);
	}

	@PutMapping
    @ResponseStatus(HttpStatus.OK)
	public void updateRatingHandler(@Valid @RequestBody UpdateRatingRequest updateRatingRequest,
                                    @RequestHeader("Authorization") String jwt) throws UserException, RatingException{
		ratingService.updateRating(updateRatingRequest, jwt);
	}

	@DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRatingHandler(@Positive(message = "Rating is mus tbe greather then zero!") @RequestParam Integer ratingId,
                                    @RequestHeader("Authorization") String jwt) throws RatingException{
        ratingService.deleteRating(ratingId, jwt);
	}


	@GetMapping
	public ResponseEntity<Page<RatingDTO>> findAllRatingsHandler(@Positive(message = "Page number must be greather then zero!")@RequestParam(defaultValue = "1", required = false) Integer  pageNumber){
       return  ResponseEntity.ok(ratingService.findAllRatings(pageNumber));
	}
	

}
