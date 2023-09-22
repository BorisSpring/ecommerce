package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.dto.ReviewDTO;
import main.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByProductId(int productId);

	
	@Query("SELECT new main.dto.ReviewDTO(r.id, u.email, r.review, r.createdAt, p.title) FROM Review r JOIN r.user u JOIN r.product p ")
	List<ReviewDTO> findAllDTO();

}
