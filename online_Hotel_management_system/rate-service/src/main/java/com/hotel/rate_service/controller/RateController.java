package com.hotel.rate_service.controller;

import com.hotel.rate_service.dto.RateRequestDTO;
import com.hotel.rate_service.dto.RateResponseDTO;
import com.hotel.rate_service.service.RateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rates")
@Tag(name = "Room Rates", description = "Manage hotel room rates")
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new room rate")
    public ResponseEntity<RateResponseDTO> createRate(@Valid @RequestBody RateRequestDTO rateRequest) {
        return ResponseEntity.ok(rateService.saveRate(rateRequest));
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all room rates")
    public ResponseEntity<List<RateResponseDTO>> getAllRates() {
        return ResponseEntity.ok(rateService.getAllRates());
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get rate by ID")
    public ResponseEntity<RateResponseDTO> getRateById(@PathVariable Long id) {
        return rateService.getRateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a room rate")
    public ResponseEntity<RateResponseDTO> updateRate(
            @PathVariable Long id,
            @Valid @RequestBody RateRequestDTO rateRequest) {
        return ResponseEntity.ok(rateService.updateRate(id, rateRequest));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a room rate")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        rateService.deleteRate(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getRateByRoomType")
    @Operation(summary = "Get rate by room type")
    public ResponseEntity<Double> getRateByRoomType(@RequestParam String roomType) {
        Double rate = rateService.getRateForRoomType(roomType);
        return ResponseEntity.ok(rate);
    }
}
