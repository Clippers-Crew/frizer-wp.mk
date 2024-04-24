package mk.frizer.service;

import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Review;
import mk.frizer.model.Salon;
import mk.frizer.model.dto.ReviewAddDTO;
import mk.frizer.model.dto.ReviewUpdateDTO;
import mk.frizer.model.enums.Role;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getReviews();
    Optional<Review> getReviewById(Long id);
    Optional<Review> createReviewForEmployee(ReviewAddDTO reviewAddDTO);
    Optional<Review> createReviewForCustomer(ReviewAddDTO reviewAddDTO);
    Optional<Review> updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO);
    Optional<Review> deleteReviewById(Long id);
}
