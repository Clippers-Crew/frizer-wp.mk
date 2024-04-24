package mk.frizer.service.impl;

import mk.frizer.model.*;
import mk.frizer.model.dto.ReviewAddDTO;
import mk.frizer.model.dto.ReviewUpdateDTO;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.ReviewNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.CustomerRepository;
import mk.frizer.repository.EmployeeRepository;
import mk.frizer.repository.ReviewRepository;
import mk.frizer.service.EmployeeService;
import mk.frizer.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<Review> createReviewForCustomer(ReviewAddDTO reviewAddDTO) {
        Employee employee = employeeRepository.findById(reviewAddDTO.getEmployeeId())
                .orElseThrow(UserNotFoundException::new);
        Customer customer = customerRepository.findById(reviewAddDTO.getCustomerId())
                .orElseThrow(UserNotFoundException::new);

        Review user = new Review(employee.getBaseUser(), customer.getBaseUser(), reviewAddDTO.getRating(), reviewAddDTO.getComment());
        return Optional.of(reviewRepository.save(user));
    }

    @Override
    public Optional<Review> createReviewForEmployee(ReviewAddDTO reviewAddDTO) {
        Employee employee = employeeRepository.findById(reviewAddDTO.getEmployeeId())
                .orElseThrow(UserNotFoundException::new);
        Customer customer = customerRepository.findById(reviewAddDTO.getCustomerId())
                .orElseThrow(UserNotFoundException::new);

        Review user = new Review(customer.getBaseUser(), employee.getBaseUser(), reviewAddDTO.getRating(), reviewAddDTO.getComment());
        return Optional.of(reviewRepository.save(user));
    }

    @Override
    public Optional<Review> updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO) {
        Review review = getReviewById(id).get();

        review.setRating(reviewUpdateDTO.getRating());
        review.setComment(reviewUpdateDTO.getComment());
        review.setDate(LocalDateTime.now());

        return Optional.of(reviewRepository.save(review));
    }

    @Override
    public Optional<Review> deleteReviewById(Long id) {
        Review user = getReviewById(id).get();
        reviewRepository.deleteById(id);
        return Optional.of(user);
    }
}
