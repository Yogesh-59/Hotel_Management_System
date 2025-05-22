package com.hotel.rate_service.controller;

import com.hotel.rate_service.dto.RateRequestDTO;
import com.hotel.rate_service.dto.RateResponseDTO;
import com.hotel.rate_service.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateControllerTest {

    @InjectMocks
    private RateController rateController;

    @Mock
    private RateService rateService;

    private RateRequestDTO requestDTO;
    private RateResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new RateRequestDTO();
        requestDTO.setRoomType("Deluxe");
        requestDTO.setPrice(150.0);

        responseDTO = new RateResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setRoomType("Deluxe");
        responseDTO.setPrice(150.0);
    }

    @Test
    void testCreateRate() {
        when(rateService.saveRate(any())).thenReturn(responseDTO);

        ResponseEntity<RateResponseDTO> response = rateController.createRate(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deluxe", response.getBody().getRoomType());
        assertEquals(150.0, response.getBody().getPrice());
    }

    @Test
    void testGetAllRates() {
        when(rateService.getAllRates()).thenReturn(List.of(responseDTO));

        ResponseEntity<List<RateResponseDTO>> response = rateController.getAllRates();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Deluxe", response.getBody().get(0).getRoomType());
    }

    @Test
    void testGetRateById_Found() {
        when(rateService.getRateById(1L)).thenReturn(Optional.of(responseDTO));

        ResponseEntity<RateResponseDTO> response = rateController.getRateById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deluxe", response.getBody().getRoomType());
    }

    @Test
    void testGetRateById_NotFound() {
        when(rateService.getRateById(999L)).thenReturn(Optional.empty());

        ResponseEntity<RateResponseDTO> response = rateController.getRateById(999L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateRate() {
        when(rateService.updateRate(eq(1L), any())).thenReturn(responseDTO);

        ResponseEntity<RateResponseDTO> response = rateController.updateRate(1L, requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deluxe", response.getBody().getRoomType());
    }

    @Test
    void testDeleteRate() {
        doNothing().when(rateService).deleteRate(1L);

        ResponseEntity<Void> response = rateController.deleteRate(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(rateService, times(1)).deleteRate(1L);
    }

    @Test
    void testGetRateByRoomType() {
        when(rateService.getRateForRoomType("Deluxe")).thenReturn(150.0);

        ResponseEntity<Double> response = rateController.getRateByRoomType("Deluxe");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(150.0, response.getBody());
    }
}
