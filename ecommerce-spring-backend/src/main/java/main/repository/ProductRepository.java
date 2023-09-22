package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import main.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	
	
	

	
	@Query("SELECT p from Product p "
			+ " WHERE p.sectionItem.itemName = :category "
			+ "AND  (:color IS NULL OR :color MEMBER OF p.colors) "
			+ "AND (:minPrice IS NULL OR p.price >= :minPrice) "
			+ "AND (:maxPrice IS NULL OR p.price <= :maxPrice) "
			+ " AND (:discountRange IS NULL OR p.discountPrecent >=:discountRange)"
			+ "ORDER BY "
			+ " CASE WHEN :sort = 'price_low' THEN p.discountPrice END ASC,	 "
			+ " CASE WHEN :sort = 'price_high' THEN p.discountPrice END ASC "
			)
	public List<Product> filterProducts( @Param("category")String category, @Param("color") List<String> color, @Param("minPrice")Integer minPrice, @Param("maxPrice")Integer maxPrice, @Param("sort")String sort, @Param("discountRange")Integer discountRange);
	
	
	

	@Query("SELECT p FROM Product p WHERE p.title LIKE %:query%")
	public List<Product> findProductByQuery(@Param("query")String query);

	
	    List<Product> findFirst20BySectionItemItemName(String itemName);

}
