package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.review.ReviewType;
import com.arbitr.cargoway.dto.rq.review.ReviewCreateRq;
import com.arbitr.cargoway.dto.rq.review.ReviewUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.review.ReviewRs;
import com.arbitr.cargoway.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews/")
@RequiredArgsConstructor
@Tag(name = "Review", description = "Управление отзывами профиля")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("profile/{profileId}/")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewRs createComment(@PathVariable("profileId") UUID profileId,
                                  @RequestBody @Valid ReviewCreateRq reviewCreateRq) {
        return reviewService.createReview(profileId, reviewCreateRq);
    }

    @PatchMapping("{reviewId}/")
    public ReviewRs updateComment(@PathVariable("reviewId") UUID reviewId,
                                  @RequestBody ReviewUpdateRq reviewUpdateRq) {
        return reviewService.updateReview(reviewId, reviewUpdateRq);
    }

    @GetMapping("{reviewId}/")
    public ReviewRs getReview(@PathVariable("reviewId") UUID reviewId) {
        return reviewService.getReview(reviewId);
    }

    @GetMapping("profile/{profileId}/")
    public PaginationRs<ReviewRs> getProfileReviews(@RequestParam ReviewType reviewType,
                                                    @PathVariable UUID profileId,
                                                    @ModelAttribute PaginationRq paginationRq) {
        return reviewService.getReviews(reviewType, profileId, paginationRq);
    }

    @GetMapping("mine/")
    public PaginationRs<ReviewRs> getMyReviews(@ModelAttribute PaginationRq paginationRq) {
        return reviewService.getMyProfileReviews(paginationRq);
    }

    @DeleteMapping("{reviewId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable("reviewId") UUID reviewId) {
        reviewService.deleteReview(reviewId);
    }

}
