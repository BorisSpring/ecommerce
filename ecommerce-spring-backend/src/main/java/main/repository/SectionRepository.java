package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.entity.Product;
import main.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer>{

	Section findBySectionName(String sectionName);
	
	
	Section findByItemsProductContains(Product product);
}
