package main.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import main.domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>{


//	@Query("SELECT NEW main.model.RatingDTO(r.id,r.rating, u.email, p.title) FROM Rating r JOIN r.user u JOIN r.product p")
//	List<RatingDTO> findAllRatings();
}
