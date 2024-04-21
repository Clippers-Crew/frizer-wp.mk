package mk.frizer.service;

import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Review;
import mk.frizer.model.Salon;
import mk.frizer.model.enums.Role;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getReviews();
    Optional<Review> getReviewById(Long id);
    Optional<Review> createReview(Long userFromId, Long userToId, Double rating, String comment);
    Optional<Review> updateReview(Long id, Long userFromId, Long userToId, Double rating, String comment);
    Optional<Review> deleteReviewById(Long id);
}
