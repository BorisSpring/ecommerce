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
			+ "AND (:subColorList IS NULL or EXISTS (SELECT 1 FROM p.colors c WHERE c IN :color)) "
			+ "AND (:subListSizes IS NULL or EXISTS(SELECT 1 from p.sizes s WHERE s in :sizes)) "
			+ "AND (:minPrice IS NULL OR p.price >= :minPrice) "
			+ "AND (:maxPrice IS NULL OR p.price <= :maxPrice) "
			+ " AND (:discountRange IS NULL OR p.discountPrecent >=:discountRange)"
			+ "ORDER BY "
			+ " CASE WHEN :sort = 'high-to-low' THEN p.discountPrice END DESC,"
			+ " CASE WHEN :sort = 'low-to-high' THEN p.discountPrice END ASC, "
			+ " CASE WHEN :sort = 'newest' THEN p.createdAt END DESC,  "
			+ " CASE WHEN :sort = 'best-rating' THEN (SELECT AVG(r.rating) FROM p.raitings r)  END DESC"
			)
	public List<Product> filterProducts(
			@Param("subColorList")List<String> subColorList, @Param("category")String category,
			@Param("color") List<String> color, @Param("minPrice")Integer minPrice, @Param("maxPrice")Integer maxPrice,
			@Param("sort")String sort, @Param("discountRange")Integer discountRange, @Param("sizes") List<String> sizesSub, @Param("subListSizes") List<String> sizes);
	
	
	
	 List<Product> findFirst20BySectionItemItemName(String itemName);



	 @Query("SELECT p FROM Product p WHERE p.title LIKE %:searchQuery% "
	 		+ " AND (:minPrice IS NULL or p.price >= :minPrice) "
	 		+ " AND (:maxPrice IS NULL or p.price <= :maxPrice) "
	 		+ " AND (:discountAbove IS NULL or p.discountPrecent >= :discountAbove) "
	 		+ " AND (:subColorList IS NULL or EXISTS (SELECT 1 FROM p.colors c WHERE c in :colors)) "
	 		+ " AND (:subSizesList IS NULL or EXISTS (SELECT 1 FROM p.sizes s WHERE s in :sizes))"
	 		+ " ORDER BY "
	 		+ " CASE WHEN :sort = 'high-to-low' THEN p.discountPrice END DESC , "
	 		+ " CASE WHEN :sort = 'low-to-high' THEN p.discountPrice END ASC , "
	 		+ " CASE WHEN :sort = 'newest' THEN p.createdAt END DESC , "
	 		+ " CASE WHEN :sort = 'best-rating' THEN (SELECT AVG(r.rating) FROM p.raitings r) END DESC ")
	 public  List<Product> findSearchedProducts(
			 @Param("minPrice")Integer minPrice,@Param("maxPrice") Integer maxPrice,
			 @Param("discountAbove") Integer discountAbove,@Param("colors") List<String> colors,
			 @Param("sizes")List<String> sizes, @Param("searchQuery") String searchQuery,
			 @Param("subSizesList")List<String> subSizes, @Param("subColorList")List<String> subColorList,
			 @Param("sort")String sort);


	 
	

}
