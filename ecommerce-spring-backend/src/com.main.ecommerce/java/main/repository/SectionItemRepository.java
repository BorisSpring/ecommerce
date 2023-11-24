package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import main.domain.SectionItems;

@Repository
public interface SectionItemRepository extends JpaRepository<SectionItems, Integer>{


	boolean existsByIdAndSectionId(Integer sectionItemId, Integer sectionId);

	boolean existsBySectionSectionNameAndSectionId(String sectionItemName, Integer sectionId);
}
