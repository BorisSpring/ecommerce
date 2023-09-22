package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.entity.Adress;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Integer>{
	
	Adress findByAdressAndCityAndCountryAndPostalCodeAndStateAndUserId(String adress, String city, String country, String postalCode, String state, int id);
}
