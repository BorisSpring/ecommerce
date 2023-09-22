package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	public List<Order> findByUserId(int userId);

	@Query("SELECT sum(o.totalPrice) FROM Order o WHERE o.orderStatus NOT IN ('PENDING', 'CANCELED', 'RETURNED')")
	public Integer getSum();
}
