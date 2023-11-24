package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import main.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {


}
