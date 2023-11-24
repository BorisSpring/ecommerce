package main.service;

import main.domain.User;


public interface TokenProvider {

	 String generateToken(User user);

	 String getEmailFromToken(String jwt) ;

}
