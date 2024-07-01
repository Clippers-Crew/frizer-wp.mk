package mk.frizer.config;

import jakarta.annotation.PostConstruct;

import mk.frizer.model.*;
import mk.frizer.model.dto.*;
import mk.frizer.repository.CityRepository;
import mk.frizer.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
    private final CityRepository cityRepository;

    public DataInitializer(BaseUserService baseUserService, BusinessOwnerService businessOwnerService, SalonService salonService, TreatmentService treatmentService, TagService tagService, EmployeeService employeeService, CustomerService customerService, ReviewService reviewService, CityRepository cityRepository){
        this.baseUserService = baseUserService;
        this.businessOwnerService = businessOwnerService;
        this.salonService = salonService;
        this.treatmentService = treatmentService;
        this.tagService = tagService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.reviewService = reviewService;
        this.cityRepository = cityRepository;
    }

    @PostConstruct
    public void init(){
        List<String> all_cities = Arrays.asList(
                "Цела Македонија", "Берово", "Битола", "Богданци", "Валандово", "Велес", "Виница", "Гевгелија",
                "Гостивар", "Дебар", "Делчево", "Демир Капија", "Демир Хисар", "Кавадарци",
                "Кичево", "Кочани", "Кратово", "Крива Паланка", "Крушево", "Куманово",
                "Македонски Брод", "Македонска Каменица", "Неготино", "Охрид", "Пехчево",
                "Прилеп", "Пробиштип", "Радовиш", "Ресен", "Свети Николе", "Скопје",
                "Струга", "Струмица", "Тетово", "Штип");

        if(cityRepository.findAll().isEmpty()) {
            for (String city : all_cities) {
                cityRepository.save(new City(city));
            }
        }


        boolean init = false;
        if(init){
            baseUserService.createBaseUser(new BaseUserAddDTO("dario@email.com","password","Dario","Delov","phoneNumber"));
            baseUserService.createBaseUser(new BaseUserAddDTO("sanja@email.com","password","Sanja","Petkova","numberPhone"));
            baseUserService.createBaseUser(new BaseUserAddDTO("denis@email.com","password","Denis","Ibraimi","LycaMobile"));
            baseUserService.createBaseUser(new BaseUserAddDTO("tajfun@email.com","password","Tajfun","Ventilator","telelink"));
            baseUserService.createBaseUser(new BaseUserAddDTO("salon@email.com","password","Salon","Biznis","kabelnet"));
            baseUserService.createBaseUser(new BaseUserAddDTO("toni@email.com","password","Toni","Tarabov","telekabel"));
            BaseUser baseUser1 = baseUserService.getBaseUsers().get(0);
            BaseUser baseUser2 = baseUserService.getBaseUsers().get(1);
            BaseUser baseUser3 = baseUserService.getBaseUsers().get(2);
            BaseUser baseUser4 = baseUserService.getBaseUsers().get(3);
            BaseUser baseUser5 = baseUserService.getBaseUsers().get(4);
            BaseUser baseUser6 = baseUserService.getBaseUsers().get(5);

            businessOwnerService.createBusinessOwner(baseUser1.getId());
            businessOwnerService.createBusinessOwner(baseUser2.getId());
            businessOwnerService.createBusinessOwner(baseUser3.getId());
            BusinessOwner businessOwner1 = businessOwnerService.getBusinessOwners().get(0);
            BusinessOwner businessOwner2 = businessOwnerService.getBusinessOwners().get(1);
            BusinessOwner businessOwner3 = businessOwnerService.getBusinessOwners().get(2);

            salonService.createSalon(new SalonAddDTO("Krc krc","Berber","doma", "Скопје", "broj", businessOwner1.getId(), (float) 4.2, (float) 42.0037876, (float) 21.9278854));
            salonService.createSalon(new SalonAddDTO("Nenko","Berber","kaj komsiite","Скопје", "broj1", businessOwner1.getId(), (float) 3.8,(float) 42.0586418, (float) 21.3176565));
            salonService.createSalon(new SalonAddDTO("Kaj Shekspiro","Frizerski salon za mazhi","prilep","Прилеп","broj2", businessOwner2.getId(), (float) 4.7,(float) 41.4360468, (float) 22.0048696));
            salonService.createSalon(new SalonAddDTO("Frizerski salon Asim","Frizerski salon za mazhi","veles","Велес", "broj3", businessOwner1.getId(), (float) 4.2,(float) 41.4676689, (float) 22.0844239));

            Salon salon1 = salonService.getSalons().get(1);
            Salon salon2 = salonService.getSalons().get(2);

            treatmentService.createTreatment(new TreatmentAddDTO("mienje", salon1.getId(), 50.0));
            treatmentService.createTreatment(new TreatmentAddDTO("pedikir", salon2.getId(), 500.0));

            Treatment treatment = treatmentService.getTreatments().get(1);

            tagService.createTag("Mienje");
            tagService.createTag("Sisanje");

            Tag tag1 = tagService.getTags().get(0);
            Tag tag2 = tagService.getTags().get(1);

            salonService.addTagToSalon(new TagAddDTO(tag1.getId(), salon1.getId()));
            salonService.addTagToSalon(new TagAddDTO(tag2.getId(), salon1.getId()));
            salonService.addTagToSalon(new TagAddDTO(tag2.getId(), salon2.getId()));

            employeeService.createEmployee(new EmployeeAddDTO(baseUser4.getId(), salon2.getId()));
            employeeService.createEmployee(new EmployeeAddDTO(baseUser5.getId(), salon2.getId()));

            customerService.createCustomer(baseUser6.getId());

            Employee employee1 = employeeService.getEmployees().get(0);
            Employee employee2 = employeeService.getEmployees().get(1);

            Customer customer = customerService.getCustomers().get(0);

            reviewService.createReviewForCustomer(new ReviewAddDTO(employee1.getId(), customer.getId(), 4.4, "Very nice customer"));
            reviewService.createReviewForCustomer(new ReviewAddDTO(employee2.getId(), customer.getId(), 5.0, "The nicest customer"));
            reviewService.createReviewForEmployee(new ReviewAddDTO(employee1.getId(), customer.getId(), 4.9, "Very nice employee"));

//            LocalDateTime dateFrom = LocalDateTime.of(2024, 6,20,11,0,0);
//            LocalDateTime dateTo = LocalDateTime.of(2024, 6,20,11,20,0);
//
//            appointmentService.createAppointment(new AppointmentAddDTO(dateFrom, dateTo, treatment.getId(), salon2.getId(), employee2.getId(), customer.getId()));
        }


    }
}
