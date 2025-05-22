package com.hotel.payment_service.service;

import com.hotel.payment_service.dto.PaymentRequestDTO;
import com.hotel.payment_service.dto.PaymentResponseDTO;
import com.hotel.payment_service.feign.RateServiceClient;
import com.hotel.payment_service.model.Payment;
import com.hotel.payment_service.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RateServiceClient rateServiceClient;

    @Value("${stripe.api.secretKey}")
    private String stripeSecretKey;

    public PaymentService(PaymentRepository paymentRepository, RateServiceClient rateServiceClient) {
        this.paymentRepository = paymentRepository;
        this.rateServiceClient = rateServiceClient;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey; // Initialize Stripe API key
    }

    public PaymentResponseDTO createPaymentIntent(PaymentRequestDTO request) throws StripeException {
        // Stripe product data
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Booking ID: " + request.getBookingId())
                        .build();

        // Price data (Stripe expects amount in cents)
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(request.getCurrency())
                        .setUnitAmount((long) (request.getAmount() * 100))
                        .setProductData(productData)
                        .build();

        // Line item
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        // Create session
        SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(lineItem)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8086/api/payment/success")
                .setCancelUrl("http://localhost:8086/api/payment/cancel")
                .setCustomerEmail(request.getCustomerEmail())
                .build();

        Session session = Session.create(params);

        // Save payment record
        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setCustomerEmail(request.getCustomerEmail());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setPaymentIntentId(session.getId()); // Use session ID instead of payment intent
        payment.setStatus("PENDING");
        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        // Prepare response
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(savedPayment.getId());
        response.setPaymentIntentId(session.getId()); // Session ID
        response.setClientSecret(session.getUrl());   // Session URL for redirect
        response.setStatus(savedPayment.getStatus());

        return response;
    }

    public void updatePaymentStatus(String paymentIntentId, String status) {
        Payment payment = paymentRepository.findByPaymentIntentId(paymentIntentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        paymentRepository.save(payment);
    }

    public Double getRateForRoomType(String roomType) {
        return rateServiceClient.getRateByRoomType(roomType);
    }
    public String getPaymentStatusByBookingId(Long bookingId) {
        // You should retrieve the payment by booking ID from your database
        // Here's an example:
        Optional<Payment> paymentOpt = paymentRepository.findByBookingId(bookingId);
        return paymentOpt.map(Payment::getStatus).orElse(null);
    }
}
