package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import main.domain.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	boolean existsByCategoryName(String categoryName);

}
