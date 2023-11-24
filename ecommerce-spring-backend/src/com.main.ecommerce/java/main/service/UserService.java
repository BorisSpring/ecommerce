package main.service;

import main.domain.User;
import main.exception.UserException;
import main.requests.UpdatePasswordRequest;

import java.util.UUID;


public interface UserService {

	User findUserById(UUID id) throws UserException;

    User findUserFromJwt(String jwt);
}
