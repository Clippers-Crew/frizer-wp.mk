package mk.frizer.web.controller;

import mk.frizer.model.Review;
import mk.frizer.model.dto.ReviewAddDTO;
import mk.frizer.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    //    Optional<Review> createReviewForEmployee(ReviewAddDTO reviewAddDTO);
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public String addReview(@RequestParam Long salonId,
                            @RequestParam Long employeeId,
                            @RequestParam Long customerId,
                            @RequestParam String comment,
                            @RequestParam Double rating) {
        reviewService.createReviewForEmployee(new ReviewAddDTO(employeeId, customerId, rating, comment));
        return "redirect:/salons/" + salonId;
    }
}
