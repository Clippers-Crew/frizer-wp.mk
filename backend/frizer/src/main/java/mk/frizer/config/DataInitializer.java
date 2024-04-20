package mk.frizer.config;

import jakarta.annotation.PostConstruct;

import mk.frizer.model.*;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.BusinessOwnerRepository;
import mk.frizer.repository.SalonRepository;
import mk.frizer.service.BaseUserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component

public class DataInitializer {
    private final BaseUserService baseUserService;
//    private final BusinessOwnerRepository businessOwnerRepository;
//    private final SalonRepository salonRepository;
    public DataInitializer(BaseUserService baseUserService){//, BusinessOwnerRepository businessOwnerRepository, SalonRepository salonRepository) {
        this.baseUserService = baseUserService;
//        this.businessOwnerRepository = businessOwnerRepository;
//        this.salonRepository = salonRepository;
    }
    @PostConstruct
    public void init(){
//        BaseUser user = new BaseUser("dario@email.com","password","FirstName","LastName","phoneNumber",Role.ROLE_ADMIN);
//        baseUserRepository.save(user);
//
//        BusinessOwner businessOwner = new BusinessOwner("dario1@email.com","password","FirstName","LastName","phoneNumber1",Role.ROLE_ADMIN, new ArrayList<Salon>());
//        businessOwnerRepository.save(businessOwner);
//        Salon salon = new Salon("Kaj Shekspiro","Frizerski salon za mazhi","Kej 1vi Maj,Prilep","078695467",new ArrayList<Employee>(),new ArrayList<SalonTreatment>(),businessOwner);
//        Salon salon1 = new Salon("Krc krc","Berber","doma","broj",new ArrayList<Employee>(),new ArrayList<SalonTreatment>(),businessOwner);
//        salonRepository.save(salon);
//        salonRepository.save(salon1);
//
//        businessOwner.getSalonList().add(salon);
//        businessOwner.getSalonList().add(salon1);
//        businessOwnerRepository.save(businessOwner);


        //TODO test
//        System.out.println(baseUserService.getBaseUsers());
//        Optional<BaseUser> b = baseUserService.getBaseUserById((long)1);
//        System.out.println(b);
//        //belja
//        try {
//            System.out.println(baseUserService.getBaseUserById(3L));
//        }
//        catch(UserNotFoundException exception){
//            System.out.println("user not found thanks");
//        }
//        System.out.println(baseUserService.createBaseUser("dario4@email.com", "passowrd", "first", "name", "phonenumbersszcx", Role.ROLE_USER));
//        System.out.println(baseUserService.updateBaseUser(1L,"mario12@email.com", "passowrd", "first", "name", "phonenumberss12", Role.ROLE_ADMIN));
//        System.out.println(baseUserService.deleteBaseUserById(3L));
    }
}
