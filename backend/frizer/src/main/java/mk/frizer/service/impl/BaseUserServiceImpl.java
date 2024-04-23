package mk.frizer.service.impl;

import mk.frizer.model.BaseUser;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.service.BaseUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaseUserServiceImpl implements BaseUserService {
    private final BaseUserRepository baseUserRepository;

    public BaseUserServiceImpl(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    @Override
    public List<BaseUser> getBaseUsers() {
        return baseUserRepository.findAll();
    }

    @Override
    public Optional<BaseUser> getBaseUserById(Long id) {
        BaseUser user = baseUserRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return Optional.of(user);
    }

    @Override
    public Optional<BaseUser> createBaseUser(String email, String password, String firstName, String lastName, String phoneNumber) {
        BaseUser user = new BaseUser(email, password, firstName, lastName, phoneNumber);
        return Optional.of(baseUserRepository.save(user));
    }

    @Override
    public Optional<BaseUser> updateBaseUser(Long id, String email, String password, String firstName, String lastName, String phoneNumber, Role role) {
        BaseUser user = getBaseUserById(id).get();

        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setRole(role);

        return Optional.of(baseUserRepository.save(user));
    }

    @Override
    public Optional<BaseUser> deleteBaseUserById(Long id) {
        //try catch?
        BaseUser user = getBaseUserById(id).get();
        baseUserRepository.deleteById(id);
        return Optional.of(user);
    }
}
