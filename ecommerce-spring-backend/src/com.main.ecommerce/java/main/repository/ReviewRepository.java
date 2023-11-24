package main.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import main.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {


    Page<Review>  findAllByOrderByIdDesc (PageRequest pageRequest);
}
