package mk.frizer.service.impl;

import mk.frizer.model.BaseUser;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Review;
import mk.frizer.model.Salon;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.ReviewNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.ReviewRepository;
import mk.frizer.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final BaseUserRepository baseUserRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, BaseUserRepository baseUserRepository) {
        this.reviewRepository = reviewRepository;
        this.baseUserRepository = baseUserRepository;
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
    public Optional<Review> createReview(Long userFromId, Long userToId, Double rating, String comment) {
        BaseUser userFrom = baseUserRepository.findById(userFromId)
                .orElseThrow(UserNotFoundException::new);
        BaseUser userTo = baseUserRepository.findById(userToId)
                .orElseThrow(UserNotFoundException::new);

        Review user = new Review(userFrom, userTo, rating, comment);
        //TODO publish event for review created
        return Optional.of(reviewRepository.save(user));
    }

    @Override
    public Optional<Review> updateReview(Long id, Long userFromId, Long userToId, Double rating, String comment) {
        Review review = getReviewById(id).get();
        BaseUser userFrom = baseUserRepository.findById(userFromId)
                .orElseThrow(UserNotFoundException::new);
        BaseUser userTo = baseUserRepository.findById(userToId)
                .orElseThrow(UserNotFoundException::new);

        review.setUserFrom(userFrom);
        review.setUserTo(userTo);
        review.setRating(rating);
        review.setComment(comment);
        //TODO maybe event??
        return Optional.of(reviewRepository.save(review));
    }

    @Override
    public Optional<Review> deleteReviewById(Long id) {
        //try catch?
        Review user = getReviewById(id).get();
        reviewRepository.deleteById(id);
        //TODO publish for delete
        return Optional.of(user);
    }
}
