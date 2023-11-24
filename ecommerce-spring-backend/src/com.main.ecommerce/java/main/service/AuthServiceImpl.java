package main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.domain.Cart;
import main.domain.User;
import main.exception.CartException;
import main.exception.UserException;
import main.repository.AuthorityRepository;
import main.repository.CartRepository;
import main.repository.UserRepository;
import main.requests.CreateUserRequest;
import main.requests.LoginRequest;
import main.requests.UpdatePasswordRequest;
import main.response.AuthResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private  final CartRepository cartRepository;
    private final UserService userService;


    @Transactional
    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Invalid credentials");

        return  AuthResponse.builder()
                .isAuth(true)
                .jwt(tokenProvider.generateToken(user))
                .build();
    }

    @Transactional
    @Override
    public AuthResponse registerUser(CreateUserRequest createUserRequest) throws CartException {

        System.out.println("saving user");
         userRepository.findByEmail(createUserRequest.getEmail())
                 .ifPresent(user -> {
            throw new UserException("There is alerdy user with same email adress!");
        });


        User savedUser = userRepository.save(User.builder()
                                        .firstName(createUserRequest.getFirstName())
                                        .lastName(createUserRequest.getLastName())
                                        .password(passwordEncoder.encode(createUserRequest.getPassword()))
                                        .birth(createUserRequest.getBirth())
                                        .email(createUserRequest.getEmail())
                                        .authority(authorityRepository.findByAuthority("user").orElseThrow(() -> new UserException("No authority were found!")))
                                        .build());

        if(savedUser == null)
            throw new UserException("Fail create user account!");

        Cart savedUserCart = cartRepository.save(Cart.builder()
                .user(savedUser)
                .build());

        if(savedUserCart == null)
            throw new CartException("Fail to create user cart!");

        return  AuthResponse.builder()
                            .isAuth(true)
                            .jwt(tokenProvider.generateToken(savedUser))
                            .build();
    }

    @Transactional
    @Override
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest, String jwt) {
        User user = userService.findUserFromJwt(jwt);

        if(passwordEncoder.matches(user.getPassword(), updatePasswordRequest.getOldPassword())){
            if(updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getRepeatedPassword())){
                user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
                userRepository.save(user);
            }else{
                throw new UserException("Password must match!");
            }
        }else{
            throw new BadCredentialsException("Invalid credentials received!");
        }
    }


}
