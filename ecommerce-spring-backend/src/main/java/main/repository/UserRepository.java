package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findUserById(int id);
	
	User findUserByEmail(String email);

	boolean existsByEmail(String email);
}
