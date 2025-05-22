package com.hotel.payment_service.controller;

import com.hotel.payment_service.dto.PaymentRequestDTO;
import com.hotel.payment_service.dto.PaymentResponseDTO;
import com.hotel.payment_service.model.Payment;
import com.hotel.payment_service.repository.PaymentRepository;
import com.hotel.payment_service.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentService paymentService, PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO request) {
        try {
            PaymentResponseDTO response = paymentService.createPaymentIntent(request);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess() {
        return ResponseEntity.ok("Payment was successful! Thank you.");
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return ResponseEntity.ok("Payment was cancelled.");
    }

    @GetMapping("/rate-test")
    public ResponseEntity<String> testRateConnection(@RequestParam String roomType) {
        try {
            Double rate = paymentService.getRateForRoomType(roomType);
            return ResponseEntity.ok("Rate for room type '" + roomType + "': " + rate);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error connecting to rate-service: " + e.getMessage());
        }
    }

    @GetMapping("/status/{bookingId}")
    public ResponseEntity<String> getPaymentStatusByBookingId(@PathVariable Long bookingId) {
        String status = paymentService.getPaymentStatusByBookingId(bookingId);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(status);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) throws IOException {
        String payload = request.getReader().lines().collect(Collectors.joining());
        String sigHeader = request.getHeader("Stripe-Signature");
        String endpointSecret = "your_stripe_webhook_secret"; // Replace with your actual secret

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(400).body("Invalid signature");
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
            if (intent != null) {
                String paymentIntentId = intent.getId();
                paymentRepository.findByPaymentIntentId(paymentIntentId)
                        .ifPresent(payment -> {
                            payment.setStatus("SUCCEEDED");
                            paymentRepository.save(payment);
                        });
            }
        }

        return ResponseEntity.ok("Received");
    }
}
