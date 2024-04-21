package mk.frizer.service;

import mk.frizer.model.BaseUser;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Salon;
import mk.frizer.model.enums.Role;

import java.util.List;
import java.util.Optional;

public interface BusinessOwnerService {
    List<BusinessOwner> getBusinessOwners();
    Optional<BusinessOwner> getBusinessOwnerById(Long id);
    Optional<BusinessOwner> createBusinessOwner(String email, String password, String firstName, String lastName, String phoneNumber, Role role);
    Optional<BusinessOwner> updateBusinessOwner(Long id, String email, String password, String firstName, String lastName, String phoneNumber, Role role, List<Long> salonIds);
    Optional<BusinessOwner> deleteBusinessOwnerById(Long id);
//    TODO listen for created salon event and update salon list
//    Optional<BusinessOwner> addSalonToBusinessOwner();
}
