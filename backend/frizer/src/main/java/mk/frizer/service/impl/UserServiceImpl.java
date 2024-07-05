package mk.frizer.service.impl;

import mk.frizer.model.BaseUser;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import mk.frizer.service.UserService;
import mk.frizer.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BaseUser register(String email, String password,String repeatPassword, String firstName, String lastName, String phoneNumber,Role role) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new InvalidUsernameOrPasswordException();
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if(this.userRepository.findByEmail(email).isPresent()) {
            throw new UsernameAlreadyExistsException(email);
        }

        BaseUser user = new BaseUser(email,
                passwordEncoder.encode(password),
                firstName,
                lastName,
                phoneNumber,
                role
        );

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
    }

}
