package com.hotel.rate_service.service;

import com.hotel.rate_service.dto.RateRequestDTO;
import com.hotel.rate_service.dto.RateResponseDTO;
import com.hotel.rate_service.model.Rate;
import com.hotel.rate_service.repository.RateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateServiceTest {

    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    private RateService rateService;

    private RateRequestDTO requestDTO;
    private Rate rate;
    private Rate savedRate;

    @BeforeEach
    void setUp() {
        requestDTO = RateRequestDTO.builder()
                .roomType("Deluxe")
                .price(150.0)
                .season("Summer")
                .description("Spacious deluxe room")
                .build();

        rate = Rate.builder()
                .id(1L)
                .roomType("Deluxe")
                .price(150.0)
                .season("Summer")
                .description("Spacious deluxe room")
                .build();

        savedRate = Rate.builder()
                .id(1L)
                .roomType("Deluxe")
                .price(150.0)
                .season("Summer")
                .description("Spacious deluxe room")
                .build();
    }

    @Test
    void testSaveRate() {
        when(rateRepository.save(any())).thenReturn(savedRate);

        RateResponseDTO response = rateService.saveRate(requestDTO);

        assertNotNull(response);
        assertEquals("Deluxe", response.getRoomType());
        assertEquals(150.0, response.getPrice());
    }

    @Test
    void testGetAllRates() {
        when(rateRepository.findAll()).thenReturn(Arrays.asList(savedRate));

        List<RateResponseDTO> rates = rateService.getAllRates();

        assertEquals(1, rates.size());
        assertEquals("Deluxe", rates.get(0).getRoomType());
    }

    @Test
    void testGetRateById_Found() {
        when(rateRepository.findById(1L)).thenReturn(Optional.of(rate));

        Optional<RateResponseDTO> response = rateService.getRateById(1L);

        assertTrue(response.isPresent());
        assertEquals("Deluxe", response.get().getRoomType());
    }

    @Test
    void testGetRateById_NotFound() {
        when(rateRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<RateResponseDTO> response = rateService.getRateById(999L);

        assertFalse(response.isPresent());
    }

    @Test
    void testUpdateRate() {
        when(rateRepository.save(any())).thenReturn(savedRate);

        RateResponseDTO updated = rateService.updateRate(1L, requestDTO);

        assertNotNull(updated);
        assertEquals(150.0, updated.getPrice());
    }

    @Test
    void testDeleteRate() {
        doNothing().when(rateRepository).deleteById(1L);

        rateService.deleteRate(1L);

        verify(rateRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetRateForRoomType() {
        when(rateRepository.findByRoomType("Deluxe")).thenReturn(Optional.of(rate));

        Double price = rateService.getRateForRoomType("Deluxe");

        assertEquals(150.0, price);
    }

    @Test
    void testGetRateForRoomType_NotFound() {
        when(rateRepository.findByRoomType("Standard")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rateService.getRateForRoomType("Standard");
        });

        assertEquals("Rate not found for room type: Standard", exception.getMessage());
    }
}
