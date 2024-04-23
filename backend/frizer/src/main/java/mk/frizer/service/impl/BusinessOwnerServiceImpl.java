package mk.frizer.service.impl;

import mk.frizer.model.BaseUser;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Salon;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.BusinessOwnerRepository;
import mk.frizer.repository.SalonRepository;
import mk.frizer.service.BusinessOwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessOwnerServiceImpl implements BusinessOwnerService {
    private final BusinessOwnerRepository businessOwnerRepository;
    private final SalonRepository salonRepository;
    private final BaseUserRepository baseUserRepository;

    public BusinessOwnerServiceImpl(BusinessOwnerRepository businessOwnerRepository, SalonRepository salonRepository, BaseUserRepository baseUserRepository) {
        this.businessOwnerRepository = businessOwnerRepository;
        this.salonRepository = salonRepository;
        this.baseUserRepository = baseUserRepository;
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
    public Optional<BusinessOwner> createBusinessOwner(Long baseUserId) {
        BaseUser baseUser = baseUserRepository.findById(baseUserId)
                .orElseThrow(UserNotFoundException::new);

        baseUser.setRole(Role.ROLE_OWNER);
        BusinessOwner user = new BusinessOwner(baseUser);
        baseUserRepository.save(baseUser);
        return Optional.of(businessOwnerRepository.save(user));
    }

    @Override
    public Optional<BusinessOwner> deleteBusinessOwnerById(Long id) {
        //try catch?
        BusinessOwner user = getBusinessOwnerById(id).get();
        businessOwnerRepository.deleteById(id);
        return Optional.of(user);
    }

    @Override
    public Optional<BusinessOwner> addSalonToBusinessOwner(Long businessOwnerId, Salon salon) {
        BusinessOwner businessOwner = getBusinessOwnerById(businessOwnerId).get();
        businessOwner.getSalonList().add(salon);
        businessOwnerRepository.save(businessOwner);
        return Optional.of(businessOwner);
    }

    //TODO listen for salon created event
}
