package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.model.*;
import mk.frizer.model.dto.ReviewAddDTO;
import mk.frizer.model.dto.ReviewUpdateDTO;
import mk.frizer.model.exceptions.ReviewNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.CustomerRepository;
import mk.frizer.repository.EmployeeRepository;
import mk.frizer.repository.ReviewRepository;
import mk.frizer.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final BaseUserRepository baseUserRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, BaseUserRepository baseUserRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.reviewRepository = reviewRepository;
        this.baseUserRepository = baseUserRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        Review user = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFoundException::new);
        return Optional.of(user);
    }

    @Override
    @Transactional
    public Optional<Review> createReviewForCustomer(ReviewAddDTO reviewAddDTO) {
        Employee employee = employeeRepository.findById(reviewAddDTO.getEmployeeId())
                .orElseThrow(UserNotFoundException::new);
        Customer customer = customerRepository.findById(reviewAddDTO.getCustomerId())
                .orElseThrow(UserNotFoundException::new);

        Review user = new Review(employee.getBaseUser(), customer.getBaseUser(), reviewAddDTO.getRating(), reviewAddDTO.getComment());
        return Optional.of(reviewRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<Review> createReviewForEmployee(ReviewAddDTO reviewAddDTO) {
        Employee employee = employeeRepository.findById(reviewAddDTO.getEmployeeId())
                .orElseThrow(UserNotFoundException::new);
        Customer customer = customerRepository.findById(reviewAddDTO.getCustomerId())
                .orElseThrow(UserNotFoundException::new);

        Review user = new Review(customer.getBaseUser(), employee.getBaseUser(), reviewAddDTO.getRating(), reviewAddDTO.getComment());
        return Optional.of(reviewRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<Review> updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO) {
        Review review = getReviewById(id).get();

        review.setRating(reviewUpdateDTO.getRating());
        review.setComment(reviewUpdateDTO.getComment());
        review.setDate(LocalDateTime.now());

        return Optional.of(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public Optional<Review> deleteReviewById(Long id) {
        Review user = getReviewById(id).get();
        reviewRepository.deleteById(id);
        return Optional.of(user);
    }
    @Override
    public Map<Long,ReviewStats> getStatisticsForEmployee(List<Employee> employees)
    {
        Map<Long,ReviewStats> map = new HashMap<>();
        for(Employee employee : employees)
        {
           Double rating =  reviewRepository.findAll().stream().filter(r->r.getUserTo().equals(employee.getBaseUser()))
                    .mapToDouble(e->e.getRating()).average().orElse(0.0);
           int numberOfReviews = (int) reviewRepository.findAll().stream()
                   .filter(r->r.getUserTo().equals(employee.getBaseUser()))
                   .count();
           ReviewStats reviewStats = new ReviewStats(rating,numberOfReviews);
           map.put(employee.getId(),reviewStats);
        }
        return map;
    }
    @Override
    public List<Review> getReviewsForEmployees(List<Employee> employees) {
        List<BaseUser> users = employees.stream().map(e->e.getBaseUser()).collect(Collectors.toList());;
        return reviewRepository.findAll().stream().filter(r->users.contains(r.getUserTo())).collect(Collectors.toList());
    }
}
