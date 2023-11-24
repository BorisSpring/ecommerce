package main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.domain.Review;
import main.domain.User;
import main.exception.ResourceNotFoundException;
import main.exception.ReviewException;
import main.mappers.ReviewMapper;
import main.model.ReviewDTO;
import main.repository.ReviewRepository;
import main.requests.CreateReviewRequest;
import main.requests.UpdateReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService   {

	private final UserService userService;
	private  final ProductService productService;
	private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

	@Override
	public Review findRewiewById(int id)   {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review with id " + id + " doesnt exists!"));
	}

    @Transactional
    @Override
    public void addReview(CreateReviewRequest req, String jwt)   {
        User requestUser = userService.findUserFromJwt(jwt);
        reviewRepository.save(Review.builder()
                        .review(req.getContent())
                        .product(productService.findProductById(req.getProductId()))
                        .user(requestUser)
                        .build());
    }

    @Transactional
    @Override
    public void updateReview(UpdateReviewRequest updateReviewRequest, String jwt) throws ReviewException {
        User requestUser = userService.findUserFromJwt(jwt);
        Review reviewToUpdate = findRewiewById(updateReviewRequest.getReviewId());

        if(requestUser.getAuthority().getAuthority().equals("admin") || requestUser.getId().equals(reviewToUpdate.getUser().getId())){
            reviewRepository.save(Review.builder()
                            .review(updateReviewRequest.getReview())
                            .build());
        }else{
            throw new ReviewException("Cannot update review! Your not realted to this review!");
        }
    }

    @Transactional
    @Override
    public void deleteReview(int id, String jwt) throws ReviewException {
        User requestUser = userService.findUserFromJwt(jwt);
        Review review = findRewiewById(id);

        if(requestUser.getAuthority().getAuthority().equals("admin") || requestUser.getId().equals(review.getUser().getId())){
            reviewRepository.deleteById(id);
        }else{
            throw new ReviewException("User is not related to this review!");
        }
    }

    @Override
    public Page<ReviewDTO> findAll(int pageNumber, String sortBy) {
        Page<Review> reviewPageList = null;
        String [] sortOptions = null;
        PageRequest pageRequest = null;

        if(sortOptions == null){
            pageRequest = PageRequest.of((pageNumber - 1), 20);
            reviewPageList = reviewRepository.findAll(pageRequest);
        }else{
            sortOptions = sortBy.split("-");
            pageRequest = PageRequest.of((pageNumber - 1), 20, sortOptions[1].equals("asc") ? Sort.by(sortOptions[0]).ascending() : Sort.by(sortOptions[0]).descending());
            reviewPageList = reviewRepository.findAllByOrderByIdDesc(pageRequest);
        }

        return  reviewPageList.map(reviewMapper::reviewToDto);
    }

}
