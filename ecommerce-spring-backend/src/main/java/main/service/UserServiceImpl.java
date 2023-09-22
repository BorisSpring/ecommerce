package main.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import main.config.TokenProvider;
import main.entity.User;
import main.exception.UserException;
import main.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepo;
	private TokenProvider tokenProvider;
	
		public UserServiceImpl(UserRepository userRepo, TokenProvider tokenProvider) {
		this.userRepo = userRepo;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		User user = userRepo.findUserByEmail(tokenProvider.getEmailFromToken(jwt));
		
		if(user == null) {
			throw new UserException("User doesnt exist!");
		}
		return user;
	}

	@Override
	public User findUserById(int id) throws UserException {
		
		Optional<User> opt = userRepo.findById(id);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new UserException("User with id " + id  + " doesnt exist" );
	}

	@Override
	public User updateUser(User user) {
		User updatedUser = userRepo.save(user);
		
		if(updatedUser == null) {
			throw new UserException("Fail to update user informations");
		}
		return updatedUser;
	}

}
