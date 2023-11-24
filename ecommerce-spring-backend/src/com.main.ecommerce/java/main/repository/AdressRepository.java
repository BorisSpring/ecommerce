package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import main.domain.Adress;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Integer>{
	
	Optional<Adress> findByAdressIgnoreCaseAndCityIgnoreCaseAndCountryIgnoreCaseAndPostalCodeIgnoreCaseAndStateIgnoreCaseAndUserId(String adress, String city, String country, String postalCode, String state, UUID userId);
}
