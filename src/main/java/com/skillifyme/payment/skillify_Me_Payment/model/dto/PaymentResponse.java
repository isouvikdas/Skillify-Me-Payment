package com.skillifyme.payment.skillify_Me_Payment.model.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private boolean success;
    private String paymentId;
    private String payerId;
    private String message;
    private String approvalUrl;
}