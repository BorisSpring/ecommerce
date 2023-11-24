package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import main.domain.Section;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer>{


	boolean existsByCategoryIdAndSectionName(Integer categoryId, String sectionName);

	boolean existsByIdAndCategoryId(Integer sectionId, Integer categoryId);

	List<Section> findByCategoryId(Integer categoryId);
}
