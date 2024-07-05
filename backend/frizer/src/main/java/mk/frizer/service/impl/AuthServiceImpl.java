package mk.frizer.service.impl;

import mk.frizer.model.BaseUser;
import mk.frizer.model.exceptions.InvalidArgumentsException;
import mk.frizer.model.exceptions.InvalidUsernameOrPasswordException;
import mk.frizer.repository.UserRepository;
import mk.frizer.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public BaseUser login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        return userRepository.findByEmailAndPassword(username, password)
                .orElseThrow(InvalidUsernameOrPasswordException::new);
    }

    @Override
    public List<BaseUser> findAll() {
        return userRepository.findAll();
    }
}

