package com.arbitr.cargoway.controller;

import com.arbitr.cargoway.dto.rq.PaginationRq;
import com.arbitr.cargoway.dto.rq.trailer.TrailerCreateRq;
import com.arbitr.cargoway.dto.rq.trailer.TrailerUpdateRq;
import com.arbitr.cargoway.dto.rs.PaginationRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerRs;
import com.arbitr.cargoway.dto.rs.trailer.TrailerShortInfoRs;
import com.arbitr.cargoway.service.TrailerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/trailers/")
@RequiredArgsConstructor
@Tag(name = "Управление прицепами", description = "Управление прицепами текущего перевозчика")
public class TrailerController {
    private final TrailerService trailerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrailerRs createTrailer(@RequestBody @Valid TrailerCreateRq trailerCreateRq) {
        return trailerService.createTrailer(trailerCreateRq);
    }

    @GetMapping("{trailerId}/")
    public TrailerRs getTrailer(@PathVariable("trailerId") UUID trailerId) {
        return trailerService.getTrailer(trailerId);
    }

    @GetMapping
    public PaginationRs<TrailerRs> getTrailers(@ModelAttribute PaginationRq paginationRq) {
        return trailerService.getCurrentProfileTrailers(paginationRq);
    }

    @GetMapping("list/")
    public List<TrailerShortInfoRs> getListTrailers() {
        return trailerService.getListCurrentProfileTrailers();
    }

    @PatchMapping("{trailerId}/")
    public TrailerRs updateTrailer(@PathVariable("trailerId") UUID trailerId,
                                   @RequestBody TrailerUpdateRq trailerUpdateRq) {
        return trailerService.updateTrailer(trailerId, trailerUpdateRq);
    }

    @DeleteMapping("{trailerId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrailer(@PathVariable("trailerId") UUID trailerId) {
        trailerService.deleteTrailer(trailerId);
    }
}
