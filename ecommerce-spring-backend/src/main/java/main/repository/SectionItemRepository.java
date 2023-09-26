package main.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import main.entity.Product;
import main.entity.SectionItems;

@Repository
public interface SectionItemRepository extends JpaRepository<SectionItems, Integer>{

	 SectionItems findByItemName(String itemName);
	 
	 @Query("SELECT p FROM Product p WHERE p.sectionItem.id = :sectionItemId")
	 List<Product> findProductsBySectionItemId(@Param("sectionItemId") int sectionItemId, Pageable pageable);
}
