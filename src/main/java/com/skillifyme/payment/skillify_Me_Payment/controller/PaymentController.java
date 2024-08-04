package com.skillifyme.payment.skillify_Me_Payment.controller;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.skillifyme.payment.skillify_Me_Payment.model.dto.PaymentRequest;
import com.skillifyme.payment.skillify_Me_Payment.model.dto.PaymentResponse;
import com.skillifyme.payment.skillify_Me_Payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = new PaymentResponse();
        try {
            PaymentResponse paymentResponse = paymentService.createPayment(paymentRequest);

            response.setSuccess(true);
            response.setPaymentId(paymentResponse.getPaymentId());
//            response.setPayerId(paymentResponse.getPayerId());
            response.setMessage("Payment created successfully");
            response.setApprovalUrl(paymentResponse.getApprovalUrl());

        } catch (PayPalRESTException e) {
            response.setSuccess(false);
            response.setMessage("Payment creation failed: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/execute")
    public ResponseEntity<PaymentResponse> executePayment(@RequestBody Map<String, String> payload) {
        String paymentId = payload.get("paymentId");
        String payerId = payload.get("payerId");

        PaymentResponse response = new PaymentResponse();
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            response.setSuccess(true);
            response.setPaymentId(payment.getId());
            response.setPayerId(payerId);
            response.setMessage("Payment executed successfully");
        } catch (PayPalRESTException e) {
            response.setSuccess(false);
            response.setMessage("Payment execution failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/payment-success")
    public ResponseEntity<PaymentResponse> paymentSuccess(
            @RequestParam String paymentId,
            @RequestParam String payerId) {

        PaymentResponse response = new PaymentResponse();
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            response.setSuccess(true);
            response.setPaymentId(payment.getId());
            response.setPayerId(payerId);
            response.setMessage("Payment executed successfully");
        } catch (PayPalRESTException e) {
            response.setSuccess(false);
            response.setMessage("Payment execution failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

}
