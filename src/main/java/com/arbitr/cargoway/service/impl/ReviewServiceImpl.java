package com.arbitr.cargoway.service.impl;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.review.ReviewCreateRq;
import com.arbitr.cargoway.dto.rq.review.ReviewType;
import com.arbitr.cargoway.dto.rq.review.ReviewUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.review.ReviewRs;
import com.arbitr.cargoway.entity.Profile;
import com.arbitr.cargoway.entity.Review;
import com.arbitr.cargoway.exception.NotFoundException;
import com.arbitr.cargoway.mapper.ReviewMapper;
import com.arbitr.cargoway.repository.ProfileRepository;
import com.arbitr.cargoway.repository.ReviewRepository;
import com.arbitr.cargoway.service.ProfileService;
import com.arbitr.cargoway.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ProfileService profileService;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProfileRepository profileRepository;

    @Transactional
    @Override
    public ReviewRs createReview(UUID profileId, ReviewCreateRq reviewCreateRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();
        Profile commentedProfile = profileService.getProfileById(profileId);

        Review newReview = reviewMapper.toEntity(reviewCreateRq);

        newReview.setCommentator(currentProfile);
        newReview.setProfile(commentedProfile);

        reviewRepository.save(newReview);

        this.calculateRating(commentedProfile);

        return reviewMapper.toRsDto(newReview);
    }

    @Transactional
    @Override
    public ReviewRs updateReview(UUID reviewId, ReviewUpdateRq reviewUpdateRq) {
        Review existingReview = this.getReviewById(reviewId);
        Profile commentedProfile = existingReview.getProfile();

        if (reviewUpdateRq.getRating() != null) {
            existingReview.setRating(reviewUpdateRq.getRating());
        }

        if (reviewUpdateRq.getComment() != null) {
            existingReview.setComment(reviewUpdateRq.getComment());
        }

        reviewRepository.save(existingReview);

        this.calculateRating(commentedProfile);

        return reviewMapper.toRsDto(existingReview);
    }

    @Override
    public ReviewRs getReview(UUID reviewId) {
        Review existingReview = this.getReviewById(reviewId);
        return reviewMapper.toRsDto(existingReview);
    }

    @Override
    public PaginationRs<ReviewRs> getReviews(ReviewType reviewType, UUID profileId, PaginationRq paginationRq) {
        Page<Review> generalReviews = null;
        Pageable pageable = PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize());

        if (reviewType.equals(ReviewType.MINE)) {
            Profile currentProfile = profileService.getAuthenticatedProfile();
            generalReviews = reviewRepository.findReviewsByCommentator_IdAndProfile_Id(
                    currentProfile.getId(),
                    profileId,
                    pageable
            );
        }

        if (reviewType.equals(ReviewType.ALL)) {
            generalReviews = reviewRepository.findReviewsByProfile_Id(
                    profileId,
                    pageable
            );
        }

        List<ReviewRs> generalReviewsRs = generalReviews.getContent().stream()
                .map(reviewMapper::toRsDto)
                .toList();

        return PaginationRs.of(
                generalReviewsRs,
                generalReviews.getNumber(),
                generalReviews.getSize(),
                generalReviews.getTotalPages()
        );
    }

    @Override
    public PaginationRs<ReviewRs> getMyProfileReviews(PaginationRq paginationRq) {
        Profile currentProfile = profileService.getAuthenticatedProfile();

        Page<Review> myReviews = reviewRepository.findReviewsByProfile_Id(
                currentProfile.getId(),
                PageRequest.of(paginationRq.getPageNumber(), paginationRq.getPageSize())
        );

        List<ReviewRs> myReviewsRs = myReviews.getContent().stream()
                .map(reviewMapper::toRsDto)
                .toList();

        return PaginationRs.of(
                myReviewsRs,
                myReviews.getNumber(),
                myReviews.getSize(),
                myReviews.getTotalPages()
        );
    }

    @Override
    public void deleteReview(UUID reviewId) {
        Review existingReview = this.getReviewById(reviewId);
        reviewRepository.delete(existingReview);
    }

    private Review getReviewById(UUID reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException("Отзыв с id=%s не был найден!".formatted(reviewId))
        );
    }

    private void calculateRating(Profile commentedProfile) {
        List<Review> reviews = reviewRepository.findReviewsByProfile_Id(commentedProfile.getId());

        int ratingSum = reviews.stream().map(Review::getRating).mapToInt(Integer::intValue).sum();
        double newCommentedProfileRating = ratingSum / (double) reviews.size();

        commentedProfile.setUserRating(newCommentedProfileRating);

        profileRepository.save(commentedProfile);
    }
}
