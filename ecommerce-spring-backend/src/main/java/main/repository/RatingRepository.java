package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.dto.RatingDTO;
import main.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>{

	List<Rating> findByProductId(int productId);

	@Query("SELECT NEW main.dto.RatingDTO(r.id,r.rating, u.email, p.title) FROM Rating r JOIN r.user u JOIN r.product p")
	List<RatingDTO> findAllRatings();
}
