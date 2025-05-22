package com.hotel.rate_service.service;

import com.hotel.rate_service.dto.RateRequestDTO;
import com.hotel.rate_service.dto.RateResponseDTO;
import com.hotel.rate_service.model.Rate;
import com.hotel.rate_service.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RateService {

    private final RateRepository rateRepository;
    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public RateResponseDTO saveRate(RateRequestDTO dto) {
        Rate rate = Rate.builder()
                .roomType(dto.getRoomType())
                .price(dto.getPrice())
                .season(dto.getSeason())
                .description(dto.getDescription())
                .build();

        return mapToDTO(rateRepository.save(rate));
    }

    public List<RateResponseDTO> getAllRates() {
        return rateRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RateResponseDTO> getRateById(Long id) {
        return rateRepository.findById(id)
                .map(this::mapToDTO);
    }

    public RateResponseDTO updateRate(Long id, RateRequestDTO dto) {
        Rate rate = Rate.builder()
                .id(id)
                .roomType(dto.getRoomType())
                .price(dto.getPrice())
                .season(dto.getSeason())
                .description(dto.getDescription())
                .build();

        return mapToDTO(rateRepository.save(rate));
    }

    public void deleteRate(Long id) {
        rateRepository.deleteById(id);
    }

    private RateResponseDTO mapToDTO(Rate rate) {
        return RateResponseDTO.builder()
                .id(rate.getId())
                .roomType(rate.getRoomType())
                .price(rate.getPrice())
                .season(rate.getSeason())
                .description(rate.getDescription())
                .build();
    }
    public Double getRateForRoomType(String roomType) {
        return rateRepository.findByRoomType(roomType)
                .map(Rate::getPrice)
                .orElseThrow(() -> new RuntimeException("Rate not found for room type: " + roomType));
    }
}
