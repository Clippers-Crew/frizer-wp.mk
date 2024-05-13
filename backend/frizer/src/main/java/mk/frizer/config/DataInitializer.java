package mk.frizer.config;

import jakarta.annotation.PostConstruct;

import mk.frizer.model.*;
import mk.frizer.model.dto.BaseUserAddDTO;
import mk.frizer.model.dto.ReviewAddDTO;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.TreatmentAddDTO;
import mk.frizer.service.*;
import org.springframework.stereotype.Component;

@Component

public class DataInitializer {
    private final BaseUserService baseUserService;
    private final BusinessOwnerService businessOwnerService;
    private final SalonService salonService;
    private final TreatmentService treatmentService;
    private final TagService tagService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final ReviewService reviewService;
    public DataInitializer(BaseUserService baseUserService, BusinessOwnerService businessOwnerService, SalonService salonService, TreatmentService treatmentService, TagService tagService, EmployeeService employeeService, CustomerService customerService, ReviewService reviewService){//, BusinessOwnerRepository businessOwnerRepository, SalonRepository salonRepository) {
        this.baseUserService = baseUserService;
        this.businessOwnerService = businessOwnerService;
        this.salonService = salonService;
        this.treatmentService = treatmentService;
        this.tagService = tagService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.reviewService = reviewService;
    }
    @PostConstruct
    public void init(){
//        baseUserService.createBaseUser(new BaseUserAddDTO("dario@email.com","password","FirstName","LastName","phoneNumber"));
//        baseUserService.createBaseUser(new BaseUserAddDTO("sanja@email.com","password","FirstName","LastName","numberPhone"));
//        baseUserService.createBaseUser(new BaseUserAddDTO("denis@email.com","password","FirstName","LastName","LycaMobile"));
//        baseUserService.createBaseUser(new BaseUserAddDTO("tajfun@email.com","password","FirstName","LastName","telelink"));
//        baseUserService.createBaseUser(new BaseUserAddDTO("salon@email.com","password","FirstName","LastName","kabelnet"));
//        baseUserService.createBaseUser(new BaseUserAddDTO("toni@email.com","password","FirstName","LastName","telekabel"));
//        BaseUser baseUser1 = baseUserService.getBaseUsers().get(0);
//        BaseUser baseUser2 = baseUserService.getBaseUsers().get(1);
//        BaseUser baseUser3 = baseUserService.getBaseUsers().get(2);
//        BaseUser baseUser4 = baseUserService.getBaseUsers().get(3);
//        BaseUser baseUser5 = baseUserService.getBaseUsers().get(4);
//        BaseUser baseUser6 = baseUserService.getBaseUsers().get(5);
//
//        businessOwnerService.createBusinessOwner(baseUser1.getId());
//        businessOwnerService.createBusinessOwner(baseUser2.getId());
//        businessOwnerService.createBusinessOwner(baseUser3.getId());
//        BusinessOwner businessOwner1 = businessOwnerService.getBusinessOwners().get(0);
//        BusinessOwner businessOwner2 = businessOwnerService.getBusinessOwners().get(1);
//        BusinessOwner businessOwner3 = businessOwnerService.getBusinessOwners().get(2);
//
//        salonService.createSalon(new SalonAddDTO("Krc krc","Berber","doma","broj", businessOwner1.getId()));
//        salonService.createSalon(new SalonAddDTO("Nenko","Berber","kaj komsiite","broj1", businessOwner1.getId()));
//        salonService.createSalon(new SalonAddDTO("Kaj Shekspiro","Frizerski salon za mazhi","prilep","broj2", businessOwner2.getId()));
//        salonService.createSalon(new SalonAddDTO("Frizerski salon Asim","Frizerski salon za mazhi","veles","broj3", businessOwner1.getId()));
//
//        Salon salon1 = salonService.getSalons().get(1);
//        Salon salon2 = salonService.getSalons().get(2);
//
//        treatmentService.createTreatment(new TreatmentAddDTO("mienje", salon1.getId(), 50.0));
//        treatmentService.createTreatment(new TreatmentAddDTO("pedikir", salon2.getId(), 500.0));
//
//        tagService.createTag("Mienje");
//        tagService.createTag("Sisanje");
//
//        Tag tag1 = tagService.getTags().get(0);
//        Tag tag2 = tagService.getTags().get(1);
//
//        salonService.addTagToSalon(salon1.getId(), tag1.getId());
//        salonService.addTagToSalon(salon1.getId(), tag2.getId());
//        salonService.addTagToSalon(salon2.getId(), tag2.getId());
//
//        employeeService.createEmployee(baseUser4.getId(), salon2.getId());
//        employeeService.createEmployee(baseUser5.getId(), salon2.getId());
//
//        customerService.createCustomer(baseUser6.getId());
//
//        Employee employee1 = employeeService.getEmployees().get(0);
//        Employee employee2 = employeeService.getEmployees().get(1);
//
//        Customer customer = customerService.getCustomers().get(0);
//
//        reviewService.createReviewForCustomer(new ReviewAddDTO(employee1.getId(), customer.getId(), 4.4, "Very nice customer"));
//        reviewService.createReviewForCustomer(new ReviewAddDTO(employee2.getId(), customer.getId(), 5.0, "The nicest customer"));
//        reviewService.createReviewForEmployee(new ReviewAddDTO(employee1.getId(), customer.getId(), 4.9, "Very nice employee"));
    }
}
