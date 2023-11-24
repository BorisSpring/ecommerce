package main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import main.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {



	List<Product> findFirst20BySectionItemItemNameOrderByCreatedAtDesc(String itemName);

	List<Product> findTop10BySectionItemId(Integer sectionItemId);
	
	@Query("SELECT p from Product p "
		+ " WHERE (:category IS NULL OR p.sectionItem.itemName = :category) "
		+ " AND (:subColorList IS NULL or EXISTS (SELECT 1 FROM p.colors c WHERE c IN :color)) "
		+ " AND (:subListSizes IS NULL or EXISTS(SELECT 1 FROM p.sizes s WHERE s.size IN :sizes)) "
		+ " AND (:minPrice IS NULL OR p.price >= :minPrice) "
		+ " AND (:maxPrice IS NULL OR p.price <= :maxPrice) "
		+ " AND (:discountRange IS NULL OR p.discountPrecent >=:discountRange)"
		+ " ORDER BY "
		+ " CASE WHEN :sort = 'high-to-low' THEN p.discountPrice END DESC,"
		+ " CASE WHEN :sort = 'low-to-high' THEN p.discountPrice END ASC, "
		+ " CASE WHEN :sort = 'newest' THEN p.createdAt END DESC,  "
		+ " CASE WHEN :sort = 'best-rating' THEN (SELECT AVG(r.rating) FROM p.raitings r)  END DESC")
	 Page<Product> findFilteredProducts(
			@Param("subColorList")List<String> subColorList,
			@Param("category")String category,
			@Param("color") List<String> color,
			@Param("minPrice")Double minPrice,
			@Param("maxPrice")Double maxPrice,
			@Param("sort")String sort,
			@Param("discountRange")Integer discountRange,
			@Param("sizes") List<String> sizes,
			@Param("subListSizes") List<String> subListSizes,
			PageRequest pageRequest);



}
