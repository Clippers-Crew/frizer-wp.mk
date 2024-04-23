package mk.frizer.config;

import jakarta.annotation.PostConstruct;

import mk.frizer.model.BaseUser;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.service.BaseUserService;
import mk.frizer.service.BusinessOwnerService;
import mk.frizer.service.SalonService;
import org.springframework.stereotype.Component;

@Component

public class DataInitializer {
    private final BaseUserService baseUserService;
    private final BusinessOwnerService businessOwnerService;
    private final SalonService salonService;
    public DataInitializer(BaseUserService baseUserService, BusinessOwnerService businessOwnerService, SalonService salonService){//, BusinessOwnerRepository businessOwnerRepository, SalonRepository salonRepository) {
        this.baseUserService = baseUserService;
        this.businessOwnerService = businessOwnerService;
        this.salonService = salonService;
    }
    @PostConstruct
    public void init(){
//        baseUserService.createBaseUser("dario@email.com","password","FirstName","LastName","phoneNumber");
//        baseUserService.createBaseUser("sanja@email.com","password","FirstName","LastName","numberPhone");
//        baseUserService.createBaseUser("denis@email.com","password","FirstName","LastName","LycaMobile");
//        BaseUser baseUser1 = baseUserService.getBaseUsers().get(0);
//        BaseUser baseUser2 = baseUserService.getBaseUsers().get(1);
//        BaseUser baseUser3 = baseUserService.getBaseUsers().get(2);
//
//        businessOwnerService.createBusinessOwner(baseUser1.getId());
//        businessOwnerService.createBusinessOwner(baseUser2.getId());
//        businessOwnerService.createBusinessOwner(baseUser3.getId());
//        BusinessOwner businessOwner1 = businessOwnerService.getBusinessOwners().get(0);
//        BusinessOwner businessOwner2 = businessOwnerService.getBusinessOwners().get(1);
//        BusinessOwner businessOwner3 = businessOwnerService.getBusinessOwners().get(2);
//
//        salonService.createSalon(new SalonDTO("Krc krc","Berber","doma","broj", businessOwner.getId()));
//        salonService.createSalon(new SalonAddDTO("Kaj Shekspiro","Frizerski salon za mazhi","prilep","broj2", businessOwner3.getId()));
//        salonService.createSalon(new SalonDTO("Frizerski salon Asim","Frizerski salon za mazhi","veles","broj3", businessOwner.getId()));
    }
}
