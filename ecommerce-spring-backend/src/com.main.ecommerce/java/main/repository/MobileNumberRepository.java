package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.domain.MobileNumber;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MobileNumberRepository extends JpaRepository<MobileNumber, Integer> {

	
    Optional<MobileNumber> findByNumberAndUserId(String mobileNumber, UUID id);
}
