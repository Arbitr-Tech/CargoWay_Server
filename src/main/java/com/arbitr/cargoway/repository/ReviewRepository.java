package com.arbitr.cargoway.repository;

import com.arbitr.cargoway.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Page<Review> findReviewsByCommentator_IdAndProfile_Id(UUID commentatorId, UUID profileId, Pageable pageable);
    Page<Review> findReviewsByProfile_Id(UUID profileId, Pageable pageable);
    List<Review> findReviewsByProfile_Id(UUID profileId);
}
