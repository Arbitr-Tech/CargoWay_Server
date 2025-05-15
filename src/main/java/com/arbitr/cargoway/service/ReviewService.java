package com.arbitr.cargoway.service;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.review.ReviewType;
import com.arbitr.cargoway.dto.rq.review.ReviewCreateRq;
import com.arbitr.cargoway.dto.rq.review.ReviewUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.review.ReviewRs;

import java.util.UUID;

public interface ReviewService {
    ReviewRs createReview(UUID profileId, ReviewCreateRq reviewCreateRq);
    ReviewRs updateReview(UUID reviewId, ReviewUpdateRq reviewUpdateRq);
    ReviewRs getReview(UUID reviewId);
    PaginationRs<ReviewRs> getReviews(ReviewType reviewType, UUID profileId, PaginationRq paginationRq);
    PaginationRs<ReviewRs> getMyProfileReviews(PaginationRq paginationRq);
    void deleteReview(UUID reviewId);
}
