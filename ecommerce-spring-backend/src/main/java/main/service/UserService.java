package main.service;

import main.entity.User;
import main.exception.UserException;


public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserById(int id) throws UserException;
	
	public User updateUser(User user);
}
