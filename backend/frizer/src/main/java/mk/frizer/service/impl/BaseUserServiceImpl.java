package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.model.BaseUser;
import mk.frizer.model.dto.BaseUserAddDTO;
import mk.frizer.model.dto.BaseUserUpdateDTO;
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
    @Transactional
    public Optional<BaseUser> createBaseUser(BaseUserAddDTO baseUserAddDTO) {
        //TODO encrypt password
        BaseUser user = new BaseUser(baseUserAddDTO.getEmail(), baseUserAddDTO.getPassword(), baseUserAddDTO.getFirstName(), baseUserAddDTO.getLastName(), baseUserAddDTO.getPhoneNumber());
        return Optional.of(baseUserRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<BaseUser> updateBaseUser(Long id, BaseUserUpdateDTO baseUserUpdateDTO) {
        BaseUser user = getBaseUserById(id).get();

        user.setFirstName(baseUserUpdateDTO.getFirstName());
        user.setLastName(baseUserUpdateDTO.getLastName());
        user.setPhoneNumber(baseUserUpdateDTO.getPhoneNumber());

        return Optional.of(baseUserRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<BaseUser> changeBaseUserPassword(Long id, String password) {
        BaseUser user = getBaseUserById(id).get();
        user.setPassword(password);
        return Optional.of(baseUserRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<BaseUser> deleteBaseUserById(Long id) {
        //try catch?
        BaseUser user = getBaseUserById(id).get();
        baseUserRepository.deleteById(id);
        return Optional.of(user);
    }
}
