package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.entity.SectionItems;

@Repository
public interface SectionItemRepository extends JpaRepository<SectionItems, Integer>{

	 SectionItems findByItemName(String itemName);
	 
}
