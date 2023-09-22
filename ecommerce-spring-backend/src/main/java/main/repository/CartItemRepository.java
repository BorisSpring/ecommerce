package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import main.entity.Cart;
import main.entity.CartItem;
import main.entity.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

	
	@Query("SELECT ci FROM CartItem ci WHERE ci.cart =:cart AND ci.size =:size AND ci.product =:product AND ci.user.id =:userId ")
	CartItem isCartItemExist(@Param("cart")Cart cart,@Param("product") Product product, @Param("size")String size,@Param("userId") int userId);

	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.user.id =:userId")
	public void deleteAllByUserId(@Param("userId")int userId);

}
