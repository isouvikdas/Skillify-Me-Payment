package com.skillifyme.payment.skillify_Me_Payment.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private String userEmail;
    private BigDecimal amount;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private String cancelUrl;
    private String successUrl;
}
