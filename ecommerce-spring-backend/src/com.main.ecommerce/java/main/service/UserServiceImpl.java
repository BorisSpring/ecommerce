package main.service;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import main.exception.ResourceNotFoundException;
import main.requests.UpdatePasswordRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import main.domain.User;
import main.exception.UserException;
import main.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService   {

	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;

    @Override
    public User findUserById(UUID id) throws UserException {
        return  userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid user id received!"));
    }

    @Override
    public User findUserFromJwt(String jwt) {
        return  userRepository.findUserByEmail(tokenProvider.getEmailFromToken(jwt))
                .orElseThrow(() -> new BadCredentialsException("Invalid token received!"));
    }

}
