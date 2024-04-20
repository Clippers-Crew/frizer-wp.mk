package mk.frizer.service.impl;

import mk.frizer.model.BaseUser;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Salon;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.BusinessOwnerRepository;
import mk.frizer.service.BusinessOwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessOwnerServiceImpl implements BusinessOwnerService {
    private final BusinessOwnerRepository businessOwnerRepository;

    public BusinessOwnerServiceImpl(BusinessOwnerRepository businessOwnerRepository) {
        this.businessOwnerRepository = businessOwnerRepository;
    }

    @Override
    public List<BusinessOwner> getBusinessOwners() {
        return businessOwnerRepository.findAll();
    }

    @Override
    public Optional<BusinessOwner> getBusinessOwnerById(Long id) {
        BusinessOwner user = businessOwnerRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return Optional.of(user);
    }

    @Override
    public Optional<BusinessOwner> createBusinessOwner(String email, String password, String firstName, String lastName, String phoneNumber, Role role) {
        BusinessOwner user = new BusinessOwner(email, password, firstName, lastName, phoneNumber, role);
        return Optional.of(businessOwnerRepository.save(user));
    }

    @Override
    public Optional<BusinessOwner> updateBusinessOwner(Long id, String email, String password, String firstName, String lastName, String phoneNumber, Role role, List<Salon> salonList) {
        BusinessOwner user = getBusinessOwnerById(id).get();

        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setRole(role);
        user.setSalonList(salonList);

        return Optional.of(businessOwnerRepository.save(user));
    }

    @Override
    public Optional<BusinessOwner> deleteBusinessOwnerById(Long id) {
        //try catch?
        BusinessOwner user = getBusinessOwnerById(id).get();
        businessOwnerRepository.deleteById(id);
        return Optional.of(user);
    }

    //TODO listen for salon created event
}
